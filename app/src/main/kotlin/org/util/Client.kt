package org.util

import java.io.File
import org.util.memory.Rom


public fun main(){
    println("Enter File To Run")
    val input = readln()
    val program = File(input)
    if (!program.exists()){
        throw IllegalArgumentException("File does not exist")
    }

    val instructions = program.readBytes()
    var i: Int = 0
    val temp: MutableList<Byte> = MutableList(4090) {0}
    for (instruction in instructions){
        temp.set(i, instruction)
        i++
    }
    val rom = Rom(memory=temp)
    val computer = D5700(rom)
    computer.screen.registerObserver(ScreenHelper())
    computer.start()
}