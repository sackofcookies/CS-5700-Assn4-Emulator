package org.util

import org.util.cpu.CPU
import org.util.memory.Memory
import org.util.memory.Ram
import org.util.screen.Screen
import org.util.instructions.InstructionFactory
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class D5700(public val instructions: Memory){
    public val cpu = CPU()
    public val ram = Ram()
    public val screen = Screen()
    private val executor = Executors.newSingleThreadScheduledExecutor()

    private fun getClock(): Runnable = Runnable {
        var instruction = "%04x".format(instructions.getShort(cpu.ProgramCounter.data))
        InstructionFactory(instruction.substring(0,0).toInt(16)).apply(instruction.substring(1), this)
    }
    public fun start() {
        val cpuFuture = executor.scheduleAtFixedRate(this.getClock(), 0, 2, TimeUnit.MILLISECONDS)
        val timerFuture = executor.scheduleAtFixedRate(this.cpu.getTimer(), 0, 16, TimeUnit.MILLISECONDS)
    }
}