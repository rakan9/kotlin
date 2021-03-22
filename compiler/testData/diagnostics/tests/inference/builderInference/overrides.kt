// !DIAGNOSTICS: -UNUSED_PARAMETER -UNUSED_VALUE -UNUSED_EXPRESSION -ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE -EXPERIMENTAL_IS_NOT_ENABLED -UNUSED_VARIABLE -CAST_NEVER_SUCCEEDS
// WITH_RUNTIME

import kotlin.experimental.ExperimentalTypeInference

class GenericController<T> {
    fun consume(t: T) {}
    fun id(t: T): (T) -> Unit = TODO()
}

@OptIn(ExperimentalTypeInference::class)
fun <S> generate1(@BuilderInference g: suspend GenericController<S>.() -> Unit): Unit = TODO()

fun main() {
    <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER!>generate1<!> {
        consume("");

        abstract class A {
            open fun foo(): (String) -> Unit = id("")
        }

        object : A() {
            override fun foo() = super.foo()
        }
    }
}
