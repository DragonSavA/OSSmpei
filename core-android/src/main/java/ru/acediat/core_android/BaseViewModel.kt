package ru.acediat.core_android

import android.os.Bundle
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    open fun saveData(outState: Bundle){}

    open fun restoreData(savedInstanceState: Bundle){}
}