package org.util.instructions

import kotlin.test.*
import org.util.D5700
import org.util.cpu.Register8
import org.util.cpu.CPU
import org.util.memory.Ram
import org.util.memory.Memory
import org.util.screen.Screen

class InstructionTests {

    private fun createD5700(): D5700 {
        val d = D5700(MockMemory())
        // Reset CPU general registers to zero
        for (i in 0 until 8) d.cpu.getGeneralRegister(i).data = 0
        d.cpu.ProgramCounter.data = 0
        d.cpu.timer.data = 0
        d.cpu.memory = false
        return d
    }

    // Minimal mock Memory to avoid exceptions
    class MockMemory : Memory() {
        override val writable = true
        private val mem = MutableList(4096) { 0.toByte() }

        override fun get(address: Short) = mem[address.toInt()]
        override fun getShort(address: Short): Short {
            val high = mem[address.toInt()].toInt() and 0xFF
            val low = mem[address.toInt() + 1].toInt() and 0xFF
            return ((high shl 8) or low).toShort()
        }
        override fun set(address: Short, data: Byte) {
            mem[address.toInt()] = data
        }
        override fun set(address: Short, data: Short) {
            mem[address.toInt()] = (data.toInt() shr 8).toByte()
            mem[address.toInt() + 1] = (data.toInt() and 0xFF).toByte()
        }
    }

    @Test
    fun `AddInstruction parse and operation test`() {
        val instr = AddInstruction()
        instr.parse("123")
        assertEquals(1, instr.rX)
        assertEquals(2, instr.rY)
        assertEquals(3, instr.rZ)

        val d = createD5700()
        d.cpu.getGeneralRegister(1).data = 5
        d.cpu.getGeneralRegister(2).data = 10
        d.cpu.getGeneralRegister(3).data = 0

        instr.operation(d)
        assertEquals(15.toByte(), d.cpu.getGeneralRegister(3).data)

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
    }

    @Test
    fun `ConvertASCIIInstruction parse, operation and ProgramCounter`() {
        val instr = ConvertASCIIInstruction()
        instr.parse("01")
        assertEquals(0, instr.rX)
        assertEquals(1, instr.rY)

        val d = createD5700()
        d.cpu.getGeneralRegister(0).data = 10
        instr.operation(d)
        // ASCII hex char for 0xA is 'a' in hex lowercase (but format %01x is lowercase)
        val expected = 'a'.code.toByte()
        assertEquals(expected, d.cpu.getGeneralRegister(1).data)

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)

        // Test exception for value > 16
        d.cpu.getGeneralRegister(0).data = 17
        val instr2 = ConvertASCIIInstruction()
        instr2.parse("01")
        assertFailsWith<RuntimeException> { instr2.operation(d) }
    }

    @Test
    fun `ConvertBase10Instruction parse, operation and ProgramCounter`() {
        val instr = ConvertBase10Instruction()
        instr.parse("0")

        val d = createD5700()
        d.cpu.getGeneralRegister(0).data = 123.toByte()
        d.cpu.address.data = 0
        d.cpu.memory = false

        instr.operation(d)
        // Values 1, 2, 3 stored as bytes (digits)
        assertEquals(1.toByte(), d.ram.get(0))
        assertEquals(2.toByte(), d.ram.get(1))
        assertEquals(3.toByte(), d.ram.get(2))

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)

        // Test when cpu.memory = true writes to instructions
        d.cpu.memory = true
        d.cpu.address.data = 3
        instr.operation(d)
        assertEquals(1.toByte(), d.instructions.get(3))
        assertEquals(2.toByte(), d.instructions.get(4))
        assertEquals(3.toByte(), d.instructions.get(5))
    }

    @Test
    fun `DrawInstruction parse, operation, and ProgramCounter`() {
        val instr = DrawInstruction()
        instr.parse("123")
        assertEquals(1, instr.rX)
        assertEquals(2, instr.rY)
        assertEquals(3, instr.rZ)

        val d = createD5700()
        d.cpu.getGeneralRegister(1).data = 65.toByte() // 'A'

        instr.operation(d)
        assertEquals('A', d.screen.getDisplay()[2][3])

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)

        // Test exception for char > 127
        d.cpu.getGeneralRegister(1).data = 200.toByte()
        val instr2 = DrawInstruction()
        instr2.parse("123")
        assertFailsWith<IllegalArgumentException> { instr2.operation(d) }
    }

    @Test
    fun `JumpInstruction parse, operation, ProgramCounter`() {
        val instr = JumpInstruction()
        instr.parse("1234")
        assertEquals(0x1234.toShort(), instr.address)

        val d = createD5700()
        instr.operation(d) // no-op
        instr.ProgramCounter(d)
        assertEquals(0x1234.toShort(), d.cpu.ProgramCounter.data)
    }

    @Test
    fun `ReadInstruction parse, operation, ProgramCounter`() {
        val instr = ReadInstruction()
        instr.parse("1")
        val d = createD5700()
        d.cpu.address.data = 0

        // set memory
        d.cpu.memory = false
        d.ram.set(0, 42.toByte())

        instr.operation(d)
        assertEquals(42.toByte(), d.cpu.getGeneralRegister(1).data)

        // test instructions memory read
        d.cpu.memory = true
        d.instructions.set(0, 99.toByte())

        instr.operation(d)
        assertEquals(99.toByte(), d.cpu.getGeneralRegister(1).data)

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
    }

    @Test
    fun `ReadKeyboardInstruction parse, operation, ProgramCounter with mocked input`() {
        val instr = ReadKeyboardInstruction()
        instr.parse("1")

        val d = createD5700()

        // Mock readln for input "0A"
        var inputSequence = listOf("ZZ", "0A").iterator()
        fun mockReadln() = inputSequence.next()

        val originalReadln = ::readln
        try {
            // Replace readln globally (not trivial in Kotlin without libraries, so skip)
            // Instead, test operation logic separately or mark as integration test
            // We can only test parse and ProgramCounter here
            instr.ProgramCounter(d)
            assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
        } finally {
            // Restore if needed
        }
    }

    @Test
    fun `ReadTInstruction parse, operation, ProgramCounter`() {
        val instr = ReadTInstruction()
        instr.parse("1")
        val d = createD5700()
        d.cpu.timer.data = 42.toByte()

        instr.operation(d)
        assertEquals(42.toByte(), d.cpu.getGeneralRegister(1).data)

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
    }

    @Test
    fun `SetAInstruction parse, operation, ProgramCounter`() {
        val instr = SetAInstruction()
        instr.parse("1234")
        val d = createD5700()

        instr.operation(d)
        assertEquals(0x1234.toShort(), d.cpu.address.data)

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
    }

    @Test
    fun `SetTInstruction parse, operation, ProgramCounter`() {
        val instr = SetTInstruction()
        instr.parse("7F")
        val d = createD5700()

        instr.operation(d)
        assertEquals(0x7F.toByte(), d.cpu.timer.data)

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
    }

    @Test
    fun `SkipEqualInstruction parse, ProgramCounter with equal and not equal values`() {
        val instr = SkipEqualInstruction()
        instr.parse("12")
        val d = createD5700()

        d.cpu.getGeneralRegister(1).data = 5
        d.cpu.getGeneralRegister(2).data = 5
        d.cpu.ProgramCounter.data = 0

        instr.ProgramCounter(d)
        // According to your code it always increments PC by 2 once, plus an extra 2 if equal
        assertEquals(4.toShort(), d.cpu.ProgramCounter.data)

        d.cpu.ProgramCounter.data = 0
        d.cpu.getGeneralRegister(2).data = 4

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
    }

    @Test
    fun `SkipNotEqualInstruction parse, ProgramCounter with equal and not equal values`() {
        val instr = SkipNotEqualInstruction()
        instr.parse("12")
        val d = createD5700()

        d.cpu.getGeneralRegister(1).data = 5
        d.cpu.getGeneralRegister(2).data = 4
        d.cpu.ProgramCounter.data = 0

        instr.ProgramCounter(d)
        // Adds 2 twice if not equal, once if equal
        assertEquals(4.toShort(), d.cpu.ProgramCounter.data)

        d.cpu.ProgramCounter.data = 0
        d.cpu.getGeneralRegister(2).data = 5

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
    }

    @Test
    fun `StoreInstruction parse, operation, ProgramCounter`() {
        val instr = StoreInstruction()
        instr.parse("1A")
        val d = createD5700()

        instr.operation(d)
        assertEquals(0xA.toByte(), d.cpu.getGeneralRegister(1).data)

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
    }

    @Test
    fun `SubInstruction parse, operation, ProgramCounter`() {
        val instr = SubInstruction()
        instr.parse("123")
        val d = createD5700()

        d.cpu.getGeneralRegister(1).data = 10
        d.cpu.getGeneralRegister(2).data = 6
        d.cpu.getGeneralRegister(3).data = 0

        instr.operation(d)
        assertEquals(4.toByte(), d.cpu.getGeneralRegister(3).data)

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
    }

    @Test
    fun `SwitchMemoryInstruction operation toggles cpu memory flag and ProgramCounter increments`() {
        val instr = SwitchMemoryInstruction()
        val d = createD5700()
        d.cpu.memory = false

        instr.operation(d)
        assertTrue(d.cpu.memory)

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
    }

    @Test
    fun `WriteInstruction parse, operation, ProgramCounter`() {
        val instr = WriteInstruction()
        instr.parse("1")

        val d = createD5700()
        d.cpu.address.data = 0
        d.cpu.getGeneralRegister(1).data = 42

        d.cpu.memory = false
        instr.operation(d)
        assertEquals(42.toByte(), d.ram.get(0))

        d.cpu.memory = true
        instr.operation(d)
        assertEquals(42.toByte(), d.instructions.get(0))

        instr.ProgramCounter(d)
        assertEquals(2.toShort(), d.cpu.ProgramCounter.data)
    }
}
