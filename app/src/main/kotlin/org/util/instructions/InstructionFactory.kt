package org.util.instructions

public fun InstructionFactory(instruction: Int): Instruction = when (instruction) {
    0 -> StoreInstruction(),
    1 -> AddInstruction(),
    2 -> SubInstruction(),
    3 -> ReadInstruction(),
    4 -> WriteInstruction(),
    5 -> JumpInstruction(),
    6 -> ReadKeyboardInstruction(),
    7 -> SwitchMemoryInstruction(),
    8 -> SkipEqualInstruction(),
    9 -> SkipNotEqualInstruction(),
    10 -> SetAInstruction(),
    11 -> SetTInstruction(),
    12 -> ReadTInstruction(),
    13 -> ConvertBase10Instruction(),
    14 -> ConvertASCIIInstruction(),
    15 -> DrawInstruction(),
    else -> throw IllegalArgumentException("" + instruction + " is not a valid instruction number")
}