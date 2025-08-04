package org.util.instructions

import org.util.D5700

abstract class Instruction{
    public fun apply(data: String, computer: D5700){
        parse(data)
        operation(computer)
        ProgramCounter(computer)
    }

    abstract public fun parse(data: String)
    abstract public fun operation(computer: D5700)
    abstract public fun ProgramCounter(computer: D5700)
}