import kotlin.experimental.ExperimentalTypeInference

class GenericController<T> {
    fun consume(t: T) {}
}

@OptIn(ExperimentalTypeInference::class)
fun <S> generate(@BuilderInference g: suspend GenericController<S>.((S) -> Unit) -> Unit): Unit = TODO()

fun main() {
    generate {
        consume("");

        var x = it

        x = {
            it // it is of stub type
        }
    }
}
