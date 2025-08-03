package org.util.memory

abstract class Memory(size: Int = 4096) {
    abstract val writable: Boolean
    private val memory: MutableList<Byte> = MutableList(size) {0}

    public fun get(address: Short): Byte = this.memory[address.toInt()]
    public fun getShort(address: Short): Short = ((this.memory[address.toInt()].toInt().shl(8) + this.memory[address.toInt() + 1].toInt()).toShort())

    public fun set(address: Short, data: Byte){
        if (writable){
            memory.set(address.toInt(), data)
        }
        else {
            throw RuntimeException("Attempted to Write to Read only memory")
        }
    }
    public fun set(address: Short, data: Short){
        if (writable){
            val temp = data.toString()
            temp.padStart(16, '0')
            val msd = temp.substring(0,7)
            val lsd = temp.substring(8)
            memory.set(address.toInt(), msd.toByte())
            memory.set(address.toInt() + 1, lsd.toByte())
        }
        else {
            throw RuntimeException("Attempted to Write to Read only memory")
        }
    }
}