package org.util.instructions

import org.util.D5700


class ConvertASCIIInstruction: Instruction(){

    private var rX: Int = 0
    private var rY: Int = 0

    override fun parse(data: String){
        this.rX = data.substring(0, 1).toInt(16)
        this.rY = data.substring(1,2).toInt(16)
    }
    override fun operation(computer: D5700){
        if (computer.cpu.getGeneralRegister(rX).data > 16){throw RuntimeException("can only convert numbers from 0-16")}
        var temp = "%01x".format(computer.cpu.getGeneralRegister(rX).data).toCharArray().last().code.toByte()
        computer.cpu.getGeneralRegister(rY).data = temp
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data = (computer.cpu.ProgramCounter.data + 2).toShort()
    }
}