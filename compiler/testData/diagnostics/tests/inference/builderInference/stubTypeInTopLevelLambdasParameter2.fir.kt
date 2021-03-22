// !DIAGNOSTICS: -UNUSED_PARAMETER -UNUSED_EXPRESSION -EXPERIMENTAL_IS_NOT_ENABLED -UNUSED_VARIABLE -CAST_NEVER_SUCCEEDS
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
    generate {
        consume("");

        var x = transform("")

        x = {
            it // it is of stub type
        }
    }
    generate {
        consume("");

        var x = transform2("")

        x = {
            this // this is of stub type
        }
    }
}
