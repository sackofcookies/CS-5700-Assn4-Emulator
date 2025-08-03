package org.util.instructions

import org.util.D5700

class ReadInstruction: Instruction(){

    private var rX: Int = 0

    override fun parse(data: String){
        this.rX = data.substring(0, 0).toInt(16)
    }
    override fun operation(computer: D5700){
        var temp: Byte
        if (computer.cpu.memory){
            temp = computer.instructions.get(computer.cpu.address.data)
        }
        else{
            temp = computer.ram.get(computer.cpu.address.data)
        }
        computer.cpu.getGeneralRegister(rX).data = temp
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data.inc()
        computer.cpu.ProgramCounter.data.inc()
    }
}