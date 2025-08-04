package org.util.memory

abstract class Memory(size: Int = 4096, private val memory: MutableList<Byte> = MutableList(size) {0}) {
    abstract val writable: Boolean

    public open fun get(address: Short): Byte = this.memory[address.toInt()]
    public open fun getShort(address: Short): Short = ((this.memory[address.toInt()].toInt().shl(8) + this.memory[address.toInt() + 1].toInt()).toShort())

    public open fun set(address: Short, data: Byte){
        if (writable){
            memory.set(address.toInt(), data)
        }
        else {
            throw RuntimeException("Attempted to Write to Read only memory")
        }
    }
    public open fun set(address: Short, data: Short){
        if (writable){
            val msb = (data.toInt() shr 8).toByte()
            val lsb = (data.toInt() and 0xFF).toByte()
            memory[address.toInt()] = msb
            memory[address.toInt() + 1] = lsb

        }
        else {
            throw RuntimeException("Attempted to Write to Read only memory")
        }
    }
}