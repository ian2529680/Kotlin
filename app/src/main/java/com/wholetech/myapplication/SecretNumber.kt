package com.wholetech.myapplication

import java.util.*

class SecretNumber {//物件導向設計
    var secert = Random().nextInt(10) + 1
    var count = 0

    fun validate(number : Int) : Int {
        count++
        return  number - secert
    }

    fun reset() {
        secert = Random().nextInt(10) + 1
        count = 0
    }
}

fun main() {
    val secretNumber = SecretNumber()
    println(secretNumber.secert)
    println("${secretNumber.validate(2)}, count: ${secretNumber.count}")
}

