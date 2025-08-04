package org.util.instructions

import org.util.D5700

class ReadTInstruction: Instruction(){

    private var rX: Int = 0

    override fun parse(data: String){
        this.rX = data.substring(0, 1).toInt(16)
    }
    override fun operation(computer: D5700){
        computer.cpu.getGeneralRegister(rX).data = computer.cpu.timer.data
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data = (computer.cpu.ProgramCounter.data + 2).toShort()
    }
}