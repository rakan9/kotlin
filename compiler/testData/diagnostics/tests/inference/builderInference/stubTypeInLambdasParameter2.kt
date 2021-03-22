// !DIAGNOSTICS: -UNUSED_LAMBDA_EXPRESSION -UNUSED_PARAMETER -EXPERIMENTAL_IS_NOT_ENABLED
// WITH_RUNTIME

import kotlin.experimental.ExperimentalTypeInference

class GenericController<T> {
    fun consume(t: T) {}
}

@OptIn(ExperimentalTypeInference::class)
fun <S> generate(@BuilderInference g: suspend GenericController<S>.(((S) -> Unit) -> Unit) -> Unit): Unit = TODO()

fun main() {
    <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER!>generate<!> {
        consume("");
        it {
            {
                <!DEBUG_INFO_ELEMENT_WITH_ERROR_TYPE!>it<!> // it is of stub type
            }
        }
    }
}
