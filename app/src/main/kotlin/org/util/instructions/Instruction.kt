package org.util.instructions

import org.util.D5700

abstract class Instruction{
    public fun apply(data: String, computer: D5700){
        parse(data)
        operation(computer)
        ProgramCounter()
    }

    abstract protected fun parse(data: String)
    abstract protected fun operation(computer: D5700)
    abstract protected fun ProgramCounter()
}