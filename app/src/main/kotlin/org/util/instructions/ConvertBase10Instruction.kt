package org.util.instructions

import org.util.D5700

class ConvertBase10Instruction: Instruction(){

    private var rX: Int = 0

    override fun parse(data: String){
        this.rX = data.substring(0, 0).toInt(16)
    }
    override fun operation(computer: D5700){
        val temp = computer.cpu.getGeneralRegister(rX).data.toUByte().toString()
        val digit1 = temp.substring(0,0).toByte()
        val digit2 = temp.substring(1,1).toByte()
        val digit3 = temp.substring(2,2).toByte()
        val address = computer.cpu.address.data
        if (computer.cpu.memory){
            computer.instructions.set(address, digit1)
            computer.instructions.set((address + 1).toShort(), digit2)
            computer.instructions.set((address + 2).toShort(), digit3)
        }
        else{
            computer.ram.set(address, digit1)
            computer.ram.set((address + 1).toShort(), digit2)
            computer.ram.set((address + 2).toShort(), digit3)
        }
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data.inc()
        computer.cpu.ProgramCounter.data.inc()
    }
}