package com.rsschool.android2021

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.rsschool.android2021.databinding.StopwatchItemBinding

class StopwatchAdapter(
    private val listener: StopwatchListener
) : ListAdapter<Stopwatch, StopwatchViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopwatchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = StopwatchItemBinding.inflate(layoutInflater, parent, false)
        return StopwatchViewHolder(binding, listener, binding.root.context.resources)
    }

    override fun onBindViewHolder(holder: StopwatchViewHolder, position: Int) {

        if (holder.timer != null) {
            holder.timer!!.cancel()
        }

        val stopwatch = getItem(position)

        holder.timer = object : CountDownTimer(stopwatch.currentMs, UNIT_TEN_MS) {
            override fun onTick(millisUntilFinished: Long) {
                stopwatch.currentMs = millisUntilFinished
                holder.setTime(stopwatch)
            }

            override fun onFinish() {
                holder.stop(stopwatch)
            }
        }

        holder.bind(stopwatch)
        holder.setProgress(stopwatch)
    }

    private companion object {

        private val itemComparator = object : DiffUtil.ItemCallback<Stopwatch>() {

            override fun areItemsTheSame(oldItem: Stopwatch, newItem: Stopwatch): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Stopwatch, newItem: Stopwatch): Boolean {
                return oldItem.currentMs == newItem.currentMs &&
                        oldItem.isStarted == newItem.isStarted
            }

            override fun getChangePayload(oldItem: Stopwatch, newItem: Stopwatch) = Any()
        }
    }
}