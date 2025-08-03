package org.util.screen

class Screen(edgeLength: Int = 8): Subject<ScreenObserver>{
    private val display: MutableList<MutableList<Char>> = MutableList<MutableList<Char>>(edgeLength) {MutableList<Char>(edgeLength){'0'}}
    private val observers: MutableList<ScreenObserver> = mutableListOf()

    public fun setPos(x: Int, y: Int, element: Char) {
        this.display.get(x).set(y, element)
        this.notifyObservers()
    }
    override fun notifyObservers() = observers.forEach() {it.update(display)}
    override fun registerObserver(observer: ScreenObserver) {observers.add(observer)}
    override fun unregiserObserver(observer: ScreenObserver) {observers.remove(observer)}
}