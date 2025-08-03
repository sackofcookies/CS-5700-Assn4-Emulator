package org.util.instructions

import org.util.D5700

class WriteInstruction: Instruction(){

    private var rX: Int = 0

    override fun parse(data: String){
        this.rX = data.substring(0, 0).toInt(16)
    }
    override fun operation(computer: D5700){
        if (computer.cpu.memory){
            computer.instructions.set(computer.cpu.address.data, computer.cpu.getGeneralRegister(rX).data)
        }
        else{
            computer.ram.set(computer.cpu.address.data, computer.cpu.getGeneralRegister(rX).data)
        }
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data.inc()
        computer.cpu.ProgramCounter.data.inc()
    }
}