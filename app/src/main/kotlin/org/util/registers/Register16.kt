package org.util.registers

class Register16(public var data:Short){
    public fun add(register: Register16): Register16 = Register16((this.data + register.data).toShort())
}