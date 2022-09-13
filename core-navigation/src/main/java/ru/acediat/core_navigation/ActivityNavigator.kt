package ru.acediat.core_navigation

const val ACTIVITY_AUTH = 1

interface ActivityNavigator {

    fun navigate(id: Int)

}