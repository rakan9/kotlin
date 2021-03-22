// !DIAGNOSTICS: -UNUSED_PARAMETER -UNUSED_VALUE -UNUSED_EXPRESSION -ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE -EXPERIMENTAL_IS_NOT_ENABLED -UNUSED_VARIABLE -CAST_NEVER_SUCCEEDS
// WITH_RUNTIME

@ExperimentalStdlibApi
fun main() {
    var x: MutableList<Any>? = null
    val z = buildList {
        add("")
        this.run {

        }
    }
}
