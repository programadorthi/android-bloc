package br.com.programadorthi.timer.bloc

sealed class TimerEvent {
    object Pause : TimerEvent()
    object Reset : TimerEvent()
    object Resume : TimerEvent()
    data class Start(val duration: Long) : TimerEvent()
    data class Tick(val duration: Long) : TimerEvent()
}
