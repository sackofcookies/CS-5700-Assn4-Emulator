package org.util.instructions

import org.util.D5700

class DrawInstruction: Instruction(){

    public var rX: Int = 0
    public var rY: Int = 0
    public var rZ: Int = 0

    override fun parse(data: String){
        this.rX = data.substring(0, 1).toInt(16)
        this.rY = data.substring(1,2).toInt(16)
        this.rZ = data.substring(2,3).toInt(16)
    }
    override fun operation(computer: D5700){
        val temp = computer.cpu.getGeneralRegister(rX).data.toInt()
        if (temp > 127){
            throw IllegalArgumentException("Cannot display characters above 127")
        }
        computer.screen.setPos(rY, rZ, temp.toChar())
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data = (computer.cpu.ProgramCounter.data + 2).toShort()
    }
}