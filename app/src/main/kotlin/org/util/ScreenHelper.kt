package org.util

import org.util.screen.ScreenObserver

class ScreenHelper(): ScreenObserver{
    override fun update(screen: List<List<Char>>){
        screen.forEach(){
            it.forEach(){
                print(it)
            }
            println()
        }
        println()
    }
}