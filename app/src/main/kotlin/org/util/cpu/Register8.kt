package org.util.cpu

class Register8(public var data:Byte){
    public fun add(register: Register8): Register8 = Register8((this.data + register.data).toByte())
}