package org.util.memory

import kotlin.test.*

class MemoryTest {

    @Test
    fun `Ram allows byte writes and reads`() {
        val ram = Ram(16)
        ram.set(2, 42.toByte())
        assertEquals(42.toByte(), ram.get(2))
    }

    @Test
    fun `Rom throws exception on byte write`() {
        val rom = Rom(16)
        assertFailsWith<RuntimeException>("Attempted to Write to Read only memory") {
            rom.set(2, 42.toByte())
        }
    }

    @Test
    fun `Ram allows short writes and reads with big endian interpretation`() {
        val ram = Ram(16)
        val shortVal: Short = 0x1234
        val high = (shortVal.toInt() shr 8).toByte()
        val low = (shortVal.toInt() and 0xFF).toByte()

        ram.set(4, high)
        ram.set(5, low)

        val result = ram.getShort(4)
        assertEquals(shortVal, result)
    }

    @Test
    fun `Rom throws exception on short write`() {
        val rom = Rom(16)
        assertFailsWith<RuntimeException>("Attempted to Write to Read only memory") {
            rom.set(4, 0x1234)
        }
    }

    @Test
    fun `getShort correctly combines two bytes in big endian`() {
        val ram = Ram(16)
        ram.set(0, (0x0A).toByte())
        ram.set(1, (0x1B).toByte())
        val result = ram.getShort(0)
        // 0x0A1B = (10 << 8) + 27 = 2587
        assertEquals(0x0A1B.toShort(), result)
    }

    @Test
    fun `Ram short set writes correct bytes at address and address+1`() {
        val ram = Ram(16)
        val value: Short = 0x1234

        // Simulate set(2, 0x1234)
        ram.set(2, value)

        // Check memory at address 2 and 3 for high and low byte
        val high = ram.get(2)
        val low = ram.get(3)
        val recombined = ((high.toInt() shl 8) or (low.toInt() and 0xFF)).toShort()

        assertEquals(value, recombined)
    }
}
