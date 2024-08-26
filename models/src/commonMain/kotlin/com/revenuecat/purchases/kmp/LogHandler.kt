package com.revenuecat.purchases.kmp

/**
 * Interface that allows handling logs manually.
 *
 * @see Purchases.logHandler
 */
public expect interface LogHandler {
    /**
     * Log a message at the [LogLevel.VERBOSE] level.
     */
    public fun v(tag: String, msg: String)

    /**
     * Log a message at the [LogLevel.DEBUG] level.
     */
    public fun d(tag: String, msg: String)

    /**
     * Log a message at the [LogLevel.INFO] level.
     */
    public fun i(tag: String, msg: String)

    /**
     * Log a message at the [LogLevel.WARN] level.
     */
    public fun w(tag: String, msg: String)

    /**
     * Log a message at the [LogLevel.ERROR] level.
     */
    public fun e(tag: String, msg: String, throwable: Throwable?)
}
