package org.util.screen

interface Subject<T>{
    abstract fun notifyObservers()
    abstract fun registerObserver(observer: T)
    abstract fun unregiserObserver(observer: T)
}