// !DIAGNOSTICS: -UNUSED_PARAMETER -UNUSED_ANONYMOUS_PARAMETER -UNUSED_VALUE -ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE -EXPERIMENTAL_IS_NOT_ENABLED -UNUSED_VARIABLE -CAST_NEVER_SUCCEEDS
// WITH_RUNTIME


import kotlin.experimental.ExperimentalTypeInference

class GenericController<T> {
    fun consume(t: T) {}
    fun transform(t: T): (T) -> Unit = TODO()
    fun transform2(t: T): T.() -> Unit = TODO()
}

@OptIn(ExperimentalTypeInference::class)
fun <S> generate(@BuilderInference g: suspend GenericController<S>.() -> Unit): Unit = TODO()

fun main() {
    <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER!>generate<!> {
        consume("");

        var x = transform("")

        <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>x<!> = {
            <!UNRESOLVED_REFERENCE!>it<!> // it is of stub type
        }
    }
    <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER!>generate<!> {
        consume("");

        var x = transform2("")

        <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>x<!> = {
            this // this is of stub type
        }
    }

    <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER!>generate<!> {
        consume("")

        var x = transform("")

        <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>x<!> = fun(<!CANNOT_INFER_PARAMETER_TYPE!>x<!>) { }
    }

    <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER!>generate<!> {
        consume("")

        var x = transform2("")

        <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>x<!> = fun String.() { }
    }
}
