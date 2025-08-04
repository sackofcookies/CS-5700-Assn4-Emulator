package org.util.instructions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class InstructionFactoryTest {

    @Test
    fun `factory returns correct instruction instances`() {
        val instructions = listOf(
            StoreInstruction::class,
            AddInstruction::class,
            SubInstruction::class,
            ReadInstruction::class,
            WriteInstruction::class,
            JumpInstruction::class,
            ReadKeyboardInstruction::class,
            SwitchMemoryInstruction::class,
            SkipEqualInstruction::class,
            SkipNotEqualInstruction::class,
            SetAInstruction::class,
            SetTInstruction::class,
            ReadTInstruction::class,
            ConvertBase10Instruction::class,
            ConvertASCIIInstruction::class,
            DrawInstruction::class
        )

        for (i in instructions.indices) {
            val instruction = InstructionFactory(i)
            assertEquals(instructions[i], instruction::class, "Instruction for opcode $i is incorrect")
        }
    }

    @Test
    fun `factory throws exception for invalid opcode`() {
        val invalidOpcodes = listOf(-1, 16, 999)

        for (opcode in invalidOpcodes) {
            val exception = assertThrows<IllegalArgumentException> {
                InstructionFactory(opcode)
            }
            assertTrue(exception.message!!.contains("$opcode is not a valid instruction number"))
        }
    }
}
