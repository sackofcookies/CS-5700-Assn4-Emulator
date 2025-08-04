package org.util.cpu

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CPUTest {

    @Test
    fun `CPU initializes with default general register count`() {
        val cpu = CPU()
        for (i in 0 until 8) {
            assertEquals(0.toByte(), cpu.getGeneralRegister(i).data)
        }
    }

    @Test
    fun `CPU initializes with custom number of general registers`() {
        val cpu = CPU(generalRegisters = 4)
        for (i in 0 until 4) {
            assertEquals(0.toByte(), cpu.getGeneralRegister(i).data)
        }
    }

    @Test
    fun `getGeneralRegister throws for out-of-bounds access`() {
        val cpu = CPU(generalRegisters = 2)
        assertFailsWith<IndexOutOfBoundsException> {
            cpu.getGeneralRegister(2)
        }
    }

    @Test
    fun `ProgramCounter, timer, and address initialize to 0`() {
        val cpu = CPU()
        assertEquals(0.toShort(), cpu.ProgramCounter.data)
        assertEquals(0.toByte(), cpu.timer.data)
        assertEquals(0.toShort(), cpu.address.data)
    }

    @Test
    fun `memory flag is false by default`() {
        val cpu = CPU()
        assertFalse(cpu.memory)
    }

    @Test
    fun `timer Runnable decrements timer if non-zero`() {
        val cpu = CPU()
        cpu.timer.data = 5
        val timerRunnable = cpu.getTimer()
        timerRunnable.run()
        assertEquals(4.toByte(), cpu.timer.data)
    }

    @Test
    fun `timer Runnable does not go below zero if timer is zero`() {
        val cpu = CPU()
        cpu.timer.data = 0
        val timerRunnable = cpu.getTimer()
        timerRunnable.run()
        assertEquals(0.toByte(), cpu.timer.data)
    }

    @Test
    fun `timer Runnable wraps around like Byte if decremented below -128`() {
        val cpu = CPU()
        cpu.timer.data = (-128).toByte()
        val timerRunnable = cpu.getTimer()
        timerRunnable.run()
        // -128 - 1 = 127 (byte overflow)
        assertEquals(127.toByte(), cpu.timer.data)
    }
}
