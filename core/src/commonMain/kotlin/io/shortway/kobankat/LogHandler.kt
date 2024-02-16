package io.shortway.kobankat

/**
 * Interface that allows handling logs manually.
 *
 * @see PurchasesFactory.logHandler
 */
public expect interface LogHandler {
    public fun v(tag: String, msg: String)
    public fun d(tag: String, msg: String)
    public fun i(tag: String, msg: String)
    public fun w(tag: String, msg: String)
    public fun e(tag: String, msg: String, throwable: Throwable?)
}