package com.rsschool.android2021

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.CountDownTimer
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.rsschool.android2021.databinding.StopwatchItemBinding
import kotlinx.coroutines.*

class StopwatchViewHolder(
    private val binding: StopwatchItemBinding,
    private val listener: StopwatchListener,
    private val resources: Resources
) : RecyclerView.ViewHolder(binding.root) {

    var timer: CountDownTimer? = null
    private var current = 0L
    private var job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    fun bind(stopwatch: Stopwatch) {
        binding.cardView.setCardBackgroundColor(Color.WHITE)
        binding.stopwatchTimer.text = stopwatch.currentMs.displayTime()

        if (stopwatch.isFinish) {
            binding.cardView.setCardBackgroundColor(Color.RED)
        }

        if (stopwatch.isStarted) {
            startTimer(stopwatch)
        } else {
            stopTimer(stopwatch)
        }

        initButtonsListeners(stopwatch)
    }

    fun stop(stopwatch: Stopwatch) {
        binding.cardView.setCardBackgroundColor(Color.RED)
        stopwatch.isStarted = false
        stopwatch.isFinish = true
        stopwatch.currentMs = stopwatch.time
        binding.stopwatchTimer.text = stopwatch.currentMs.displayTime()
        stopTimer(stopwatch)
    }

    fun setProgress(stopwatch: Stopwatch) {
        binding.customView.setPeriod(PERIOD)
        binding.customView.setCurrent(PERIOD - stopwatch.currentMs)
    }

    fun setTime(stopwatch: Stopwatch) {
        binding.stopwatchTimer.text = stopwatch.currentMs.displayTime()
    }

    private fun initButtonsListeners(stopwatch: Stopwatch) {
        binding.startPauseButton.setOnClickListener {
            if (stopwatch.isStarted) {
                listener.stop(stopwatch.id, stopwatch.currentMs)
            } else {
                listener.start(stopwatch.id)
            }
        }

        binding.deleteButton.setOnClickListener { listener.delete(stopwatch.id) }
    }

    private fun startTimer(stopwatch: Stopwatch) {
        val drawable = resources.getDrawable(R.drawable.ic_baseline_pause_24)
        binding.startPauseButton.setImageDrawable(drawable)

        if (stopwatch.isFinish) stopwatch.isFinish = false

        timer?.cancel()
        timer?.start()

        current = stopwatch.currentMs
        binding.customView.setPeriod(PERIOD)

        uiScope.launch {
            while (current >= 0) {
                current -= INTERVAL
                binding.customView.setCurrent(PERIOD - current)
                delay(INTERVAL)
            }
        }

        binding.blinkingIndicator.isInvisible = false
        (binding.blinkingIndicator.background as? AnimationDrawable)?.start()
    }

    private fun stopTimer(stopwatch: Stopwatch) {
        val drawable = resources.getDrawable(R.drawable.ic_baseline_play_arrow_24)
        binding.startPauseButton.setImageDrawable(drawable)

        if (job.isActive) {
            uiScope.coroutineContext.cancelChildren()
        }

        timer?.cancel()

        binding.blinkingIndicator.isInvisible = true
        (binding.blinkingIndicator.background as? AnimationDrawable)?.stop()
    }
}