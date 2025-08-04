package org.util.screen

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ScreenTest {

    private class TestObserver : ScreenObserver {
        var notifiedData: List<List<Char>>? = null

        override fun update(screen: List<List<Char>>) {
            // Deep copy for safety
            notifiedData = screen.map { it.toList() }
        }
    }

    @Test
    fun `setPos correctly updates display element`() {
        val screen = Screen(4)
        screen.setPos(1, 2, 'X')

        val observer = TestObserver()
        screen.registerObserver(observer)
        screen.setPos(2, 3, 'Y')

        val display = observer.notifiedData!!
        assertEquals('X', display[1][2])
        assertEquals('Y', display[2][3])
    }

    @Test
    fun `registerObserver stores observer and notifies it`() {
        val screen = Screen(3)
        val observer = TestObserver()
        screen.registerObserver(observer)

        screen.setPos(0, 0, 'A')

        val result = observer.notifiedData!!
        assertEquals('A', result[0][0])
    }

    @Test
    fun `unregisterObserver prevents further updates`() {
        val screen = Screen(2)
        val observer = TestObserver()

        screen.registerObserver(observer)
        screen.setPos(0, 0, 'B')
        screen.unregiserObserver(observer)
        screen.setPos(1, 1, 'Z') // Should not notify observer again

        val data = observer.notifiedData!!
        // Last update was before unregister
        assertEquals('B', data[0][0])
        assertEquals('0', data[1][1])
    }
}
