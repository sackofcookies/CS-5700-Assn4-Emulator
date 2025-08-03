package org.util.instructions

import org.util.D5700

class DrawInstruction: Instruction(){

    private var rX: Int = 0
    private var rY: Int = 0
    private var rZ: Int = 0

    override fun parse(data: String){
        this.rX = data.substring(0, 0).toInt(16)
        this.rY = data.substring(1,1).toInt(16)
        this.rZ = data.substring(2,2).toInt(16)
    }
    override fun operation(computer: D5700){
        computer.screen.setPos(computer.cpu.getGeneralRegister(rX).data.toInt(), computer.cpu.getGeneralRegister(rY).data.toInt(), computer.cpu.getGeneralRegister(rZ).data.toInt().toChar())
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data.inc()
        computer.cpu.ProgramCounter.data.inc()
    }
}