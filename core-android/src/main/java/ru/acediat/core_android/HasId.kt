package ru.acediat.core_android

interface HasId {
    val id : Int
    override fun equals(other : Any?) : Boolean
}