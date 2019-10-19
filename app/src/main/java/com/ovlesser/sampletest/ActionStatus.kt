package com.ovlesser.sampletest

sealed class ActionStatus {
    class Start: ActionStatus()
    class Failed(val error: Throwable): ActionStatus()
    class Completed: ActionStatus()
}
