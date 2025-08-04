package org.util.screen

interface ScreenObserver{
    abstract fun update(screen: List<List<Char>>)
}