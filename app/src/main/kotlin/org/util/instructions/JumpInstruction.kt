package org.util.instructions

import org.util.D5700

class JumpInstruction: Instruction(){

    private var address: Short = 0

    override fun parse(data: String){
        this.address = data.toInt(16).toShort()
    }
    override fun operation(computer: D5700){
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data = address
    }
}