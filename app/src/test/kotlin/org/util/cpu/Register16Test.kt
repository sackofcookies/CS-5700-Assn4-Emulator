package org.util.cpu

import kotlin.test.Test
import kotlin.test.assertEquals

class Register16Test {

    @Test
    fun `add should correctly sum positive values`() {
        val reg1 = Register16(1000)
        val reg2 = Register16(2000)
        val result = reg1.add(reg2)
        assertEquals(3000.toShort(), result.data)
    }

    @Test
    fun `add should handle overflow correctly`() {
        val reg1 = Register16(Short.MAX_VALUE)
        val reg2 = Register16(1)
        val result = reg1.add(reg2)
        // Short.MAX_VALUE + 1 = -32768 due to overflow
        assertEquals(Short.MIN_VALUE, result.data)
    }

    @Test
    fun `add should work with negative values`() {
        val reg1 = Register16((-1000).toShort())
        val reg2 = Register16(500)
        val result = reg1.add(reg2)
        assertEquals((-500).toShort(), result.data)
    }

    @Test
    fun `add should not mutate original registers`() {
        val reg1 = Register16(300)
        val reg2 = Register16(400)
        reg1.add(reg2)
        assertEquals(300.toShort(), reg1.data)
        assertEquals(400.toShort(), reg2.data)
    }
}
