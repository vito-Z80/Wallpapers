package com.serdjuk.wallpapers.provider

import androidx.compose.runtime.mutableStateOf
import com.serdjuk.wallpapers.Greets
import com.serdjuk.wallpapers.model.Navigator
import java.util.Stack

object Screen {
    enum class State {
        FIRST_SCREEN, SECOND_SCREEN
    }

    private val state = mutableStateOf(State.FIRST_SCREEN)
    private val stack by lazy { Stack<Navigator>() }
    private var screenCounter = 0

    var firstScreen: Navigator? = Greets().also { stack.push(it) }
    var secondScreen: Navigator? = null

    fun backScreen() {
        if (stack.size > 2) {
            val from = stack.pop()
            if (screenCounter % 2 != 0) {
                firstScreen = stack.peek()
                secondScreen = from
            } else {
                secondScreen = stack.peek()
                firstScreen = from
            }
            screenCounter++
            change()
        }
    }

    fun nextScreen(screen: Navigator) {
        if (screenCounter % 2 == 0) {
            if (secondScreen != null) {
                secondScreen = screen
            } else {
                firstScreen = stack.peek()
                secondScreen = screen
            }
        } else {
            secondScreen = stack.peek()
            firstScreen = screen
        }
        screenCounter++
        stack.push(screen)
        change()
    }

    private fun change() {
        state.value.getOpposite()
    }

    fun getState() = state

    private fun State.getOpposite() {
        state.value = when (this) {
            State.FIRST_SCREEN -> State.SECOND_SCREEN
            State.SECOND_SCREEN -> State.FIRST_SCREEN
        }
    }
}
