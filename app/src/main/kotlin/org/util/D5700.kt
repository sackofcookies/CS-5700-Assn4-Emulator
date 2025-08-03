package org.util

import org.util.cpu.CPU
import org.util.memory.Memory
import org.util.memory.Ram
import org.util.screen.Screen

class D5700(public val instructions: Memory){
    public val cpu = CPU()
    public val ram = Ram()
    public val screen = Screen()

    
}