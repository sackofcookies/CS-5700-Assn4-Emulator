package org.util.cpu

class CPU(generalRegisters: Int = 8){
    private val general: List<Register8> = List<Register8>(generalRegisters) {Register8(0)}
    public val ProgramCounter: Register16 = Register16(0)
    public val timer: Register8 = Register8(0)
    public val address: Register16 = Register16(0)
    public var memory: Boolean = false

    public fun getGeneralRegister(address: Int): Register8 = general.get(address)
    public fun getTimer(): Runnable = Runnable {
        if (timer.data != 0.toByte()){
            timer.data.dec()
        }
        
    }
}