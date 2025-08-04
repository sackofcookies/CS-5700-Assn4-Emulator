package org.util.instructions

import org.util.D5700

private val ALLOWED_CHARACTERS = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8","9","A","B","C","D","E","F")

class ReadKeyboardInstruction: Instruction(){

    private var rX: Int = 0

    override fun parse(data: String){
        this.rX = data.substring(0, 1).toInt(16)
    }
    override fun operation(computer: D5700){
        var input: String
        println("Wating for Keyboard Input")
        do {
            input = readln()
            input = input.padStart(2, '0')
        } while(!((input.substring(0,1) in ALLOWED_CHARACTERS) || (input.substring(1) in ALLOWED_CHARACTERS) ))
        computer.cpu.getGeneralRegister(rX).data = input.substring(0,2).toInt(16).toByte()
    }
    override fun ProgramCounter(computer: D5700){
        computer.cpu.ProgramCounter.data = (computer.cpu.ProgramCounter.data + 2).toShort()
    }
}