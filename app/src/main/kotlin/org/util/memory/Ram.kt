package org.util.memory

class Ram(size: Int = 4096): Memory(size){
    override val writable = true
}