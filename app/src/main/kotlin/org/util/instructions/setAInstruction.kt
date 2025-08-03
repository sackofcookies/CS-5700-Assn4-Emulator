package org.util.instructions

import org.util.D5700

class SetAInstruction: Instruction(){

    private var address: Short = 0

    override fun parse(data: String){
        this.address = data.toInt(16).toShort()
    }
    override fun operation(computer: D5700){
        computer.cpu.address.data = address
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data.inc()
        computer.cpu.ProgramCounter.data.inc()
    }
}