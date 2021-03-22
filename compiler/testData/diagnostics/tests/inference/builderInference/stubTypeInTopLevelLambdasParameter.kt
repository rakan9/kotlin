// !DIAGNOSTICS: -UNUSED_PARAMETER -UNUSED_VALUE -UNUSED_EXPRESSION -ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE -EXPERIMENTAL_IS_NOT_ENABLED -UNUSED_VARIABLE -CAST_NEVER_SUCCEEDS
// WITH_RUNTIME

import kotlin.experimental.ExperimentalTypeInference

class GenericController<T> {
    fun consume(t: T) {}
}

@OptIn(ExperimentalTypeInference::class)
fun <S> generate1(@BuilderInference g: suspend GenericController<S>.(((S) -> Unit) -> Unit) -> Unit): Unit = TODO()
@OptIn(ExperimentalTypeInference::class)
fun <S> generate2(@BuilderInference g: suspend GenericController<S>.((S.() -> Unit) -> Unit) -> Unit): Unit = TODO()

fun main() {
    <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER!>generate1<!> {
        consume("");

        var x = <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>it<!>

        <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>x<!> = {
            <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>it<!> // it is of stub type
        }
    }
    <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER!>generate2<!> {
        consume("");

        var x = <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>it<!>

        <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>x<!> = {
            this // it is of stub type
        }
    }
}
