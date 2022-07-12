package ru.acediat.core_android

import android.util.Log

const val OSS_TAG = "OSS_TAG"

object Logger {

    fun i(tag : String, info : String) = Log.i(tag, info)

    fun d(tag: String, info : String) = Log.d(tag, info)

    fun e(tag: String, info : String) = Log.e(tag, info)

    fun e(tag : String, info : String, t : Throwable) = Log.e(tag, info, t)

    fun <T> dArray(tag : String, a : Array<T>){
        Log.d(tag, "--- start array ---")
        for(i in a.indices){
            Log.d(tag,"($i): ${a[i].toString()}")
        }
        Log.d(tag, "--- end array ---")
    }

    fun <T> eArray(tag : String, a : Array<T>){
        Log.e(tag, "--- start array ---")
        for(i in a.indices){
            Log.e(tag,"($i): ${a[i].toString()}")
        }
        Log.e(tag, "--- end array ---")
    }

    fun <T> eArray(tag : String, a : Array<T>, t : Throwable){
        Log.e(tag,"ERROR", t)
        eArray(tag, a)
    }

}