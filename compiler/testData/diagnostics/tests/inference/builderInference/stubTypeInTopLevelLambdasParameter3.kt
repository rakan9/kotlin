// !DIAGNOSTICS: -UNUSED_PARAMETER -UNUSED_VALUE -UNUSED_EXPRESSION -ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE -EXPERIMENTAL_IS_NOT_ENABLED -UNUSED_VARIABLE -CAST_NEVER_SUCCEEDS
// WITH_RUNTIME

import kotlin.experimental.ExperimentalTypeInference

class GenericController<T> {
    fun consume(t: T) {}
}

@OptIn(ExperimentalTypeInference::class)
fun <S> generate1(@BuilderInference g: suspend GenericController<S>.() -> ((S) -> Unit) -> Unit): Unit = TODO()

fun main() {
    generate1 {
        consume("");
        <!TYPE_MISMATCH!>{
            <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE, DEBUG_INFO_UNRESOLVED_WITH_TARGET, UNRESOLVED_REFERENCE!>it<!>
        }<!>
    }
}
