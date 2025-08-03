package org.util.instructions

import org.util.D5700


class SwitchMemoryInstruction: Instruction(){


    override fun parse(data: String){
    }
    override fun operation(computer: D5700){
        computer.cpu.memory = !computer.cpu.memory
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data.inc()
        computer.cpu.ProgramCounter.data.inc()
    }
}