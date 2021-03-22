// !DIAGNOSTICS: -UNUSED_EXPRESSION -UNUSED_PARAMETER -EXPERIMENTAL_API_USAGE_ERROR
// WITH_RUNTIME

import kotlin.experimental.ExperimentalTypeInference

class GenericController<T> {
    fun consume(t: T) {}
}

@OptIn(ExperimentalTypeInference::class)
fun <S> generate(@BuilderInference g: suspend GenericController<S>.(((S) -> Unit) -> Unit) -> Unit): Unit = TODO()

fun main() {
    generate {
        consume("");
        it {
            {
                it // it is of stub type
            }
        }
    }
}
