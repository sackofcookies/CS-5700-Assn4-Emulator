package org.util.instructions

import org.util.D5700


class SkipEqualInstruction: Instruction(){

    private var rX: Int = 0
    private var rY: Int = 0

    override fun parse(data: String){
        this.rX = data.substring(0, 0).toInt(16)
        this.rY = data.substring(1,1).toInt(16)
    }
    override fun operation(computer: D5700){
    }
    override fun ProgramCounter(computer: D5700){
        if (computer.cpu.getGeneralRegister(rX).data == computer.cpu.getGeneralRegister(rY).data){
            computer.cpu.ProgramCounter.data.inc()
            computer.cpu.ProgramCounter.data.inc()
        }
        computer.cpu.ProgramCounter.data.inc()
        computer.cpu.ProgramCounter.data.inc()
    }
}