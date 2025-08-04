package org.util.instructions

import org.util.D5700

class SetTInstruction: Instruction(){

    private var data: Byte = 0

    override fun parse(data: String){
        this.data = data.substring(0,2).toInt(16).toByte()
    }
    override fun operation(computer: D5700){
        computer.cpu.timer.data = this.data
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data = (computer.cpu.ProgramCounter.data + 2).toShort()
    }
}