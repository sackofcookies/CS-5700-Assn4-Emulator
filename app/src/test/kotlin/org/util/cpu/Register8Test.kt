package org.util.cpu

import kotlin.test.Test
import kotlin.test.assertEquals

class Register8Test {

    @Test
    fun `add should correctly sum positive values`() {
        val reg1 = Register8(10)
        val reg2 = Register8(20)
        val result = reg1.add(reg2)
        assertEquals(30.toByte(), result.data)
    }

    @Test
    fun `add should handle overflow correctly`() {
        val reg1 = Register8(120)
        val reg2 = Register8(120)
        val result = reg1.add(reg2)
        // 120 + 120 = 240 -> toByte() = -16 due to overflow
        assertEquals((-16).toByte(), result.data)
    }

    @Test
    fun `add should work with negative values`() {
        val reg1 = Register8((-100).toByte())
        val reg2 = Register8(50)
        val result = reg1.add(reg2)
        assertEquals((-50).toByte(), result.data)
    }

    @Test
    fun `add should not mutate original registers`() {
        val reg1 = Register8(5)
        val reg2 = Register8(10)
        reg1.add(reg2)
        assertEquals(5.toByte(), reg1.data)
        assertEquals(10.toByte(), reg2.data)
    }
}
