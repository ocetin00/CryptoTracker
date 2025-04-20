package com.oguzhan.shared


class Greeting {
    private val platform = platform()

    fun greet(): String {
        return "Hello, ${platform}!"

    }
}