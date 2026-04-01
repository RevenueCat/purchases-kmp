package com.revenuecat.purchases.kmp

/**
 * A LogHandler which simply prints to the console. Useful as a fake in tests.
 */
object PrintLnLogHandler : LogHandler {

    override fun v(tag: String, msg: String) {
        println("V: $tag $msg")
    }

    override fun d(tag: String, msg: String) {
        println("D: $tag $msg")
    }

    override fun i(tag: String, msg: String) {
        println("I: $tag $msg")
    }

    override fun w(tag: String, msg: String) {
        println("W: $tag $msg")
    }

    override fun e(tag: String, msg: String, throwable: Throwable?) {
        println("E: $tag $msg")
        throwable?.printStackTrace()
    }
}
