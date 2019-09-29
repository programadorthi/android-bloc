package br.com.programadorthi.timer.bloc

sealed class TimerState(val duration: Long) {
    object Finished : TimerState(0)
    class Paused(duration: Long) : TimerState(duration)
    class Ready(duration: Long) : TimerState(duration)
    class Running(duration: Long) : TimerState(duration)
}
