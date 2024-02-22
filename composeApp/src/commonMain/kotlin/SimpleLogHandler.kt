import io.shortway.kobankat.LogHandler

class SimpleLogHandler(
    private val log: (message: String) -> Unit
) : LogHandler {

    override fun v(tag: String, msg: String) = log(tag, msg)

    override fun d(tag: String, msg: String) = log(tag, msg)

    override fun i(tag: String, msg: String) = log(tag, msg)

    override fun w(tag: String, msg: String) = log(tag, msg)

    override fun e(tag: String, msg: String, throwable: Throwable?) = log(tag, msg)

    private fun log(tag: String, msg: String) =
        log("[$tag] $msg")

}