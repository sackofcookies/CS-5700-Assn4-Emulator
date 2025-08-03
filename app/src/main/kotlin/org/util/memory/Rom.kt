package org.util.memory

class Rom(size: Int = 4096): Memory(size){
    override val writable = false
}