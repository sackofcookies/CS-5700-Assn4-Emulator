package org.util.memory

class Rom(size: Int = 4096, memory: MutableList<Byte> = MutableList(size) {0}): Memory(size, memory){
    override val writable = false
}