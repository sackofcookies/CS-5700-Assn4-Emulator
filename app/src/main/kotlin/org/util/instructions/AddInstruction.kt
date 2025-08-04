package org.util.instructions

import org.util.D5700

class AddInstruction: Instruction(){

    public var rX: Int = 0
    public var rY: Int = 0
    public var rZ: Int = 0

    override fun parse(data: String){
        this.rX = data.substring(0, 1).toInt(16)
        this.rY = data.substring(1,2).toInt(16)
        this.rZ = data.substring(2).toInt(16)
    }
    override fun operation(computer: D5700){
        computer.cpu.getGeneralRegister(rZ).data = (computer.cpu.getGeneralRegister(rX).add(computer.cpu.getGeneralRegister(rY)).data)
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data = (computer.cpu.ProgramCounter.data + 2).toShort()
    }
}