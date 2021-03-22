// !DIAGNOSTICS: -UNUSED_PARAMETER -UNUSED_EXPRESSION -EXPERIMENTAL_IS_NOT_ENABLED -UNUSED_VARIABLE -CAST_NEVER_SUCCEEDS
// WITH_RUNTIME

@file:OptIn(ExperimentalTypeInference::class)

import kotlin.experimental.ExperimentalTypeInference

class GenericController<T> {
    suspend fun foo1(t: T) {}
    fun foo2(t: T) {}

    suspend fun foo3(t: T) = t
    fun foo4(): T = TODO()
}

suspend fun <T> GenericController<T>.foo5(t: T) {}
fun <T> GenericController<T>.foo6(t: T) {}

suspend fun <T> GenericController<T>.foo7(t: T) = t
fun <T> GenericController<T>.foo8(): T = TODO()

fun <S> generate(@BuilderInference g: suspend GenericController<S>.() -> Unit): List<S> = TODO()

fun main() {
    val test1 = generate {
        foo1(3)
        ::foo1
    }
    val test2 = generate {
        foo1(3)
        ::foo2
    }
    val test3 = generate {
        foo1(3)
        ::foo3
    }
    val test4 = <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER!>generate<!> {
        foo1(3)
        ::foo4
    }
    val test5 = generate {
        foo1(3)
        ::foo5
    }
    val test6 = generate {
        foo1(3)
        ::foo6
    }
    val test7 = generate {
        foo1(3)
        ::foo7
    }
    val test8 = generate {
        foo1(3)
        ::foo8
    }

    val test11 = generate {
        foo1(3)
        val x = ::foo1
    }
    val test21 = generate {
        foo1(3)
        val x = ::foo2
    }
    val test31 = generate {
        foo1(3)
        val x = ::foo3
    }
    val test41 = <!NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER!>generate<!> {
        foo1(3)
        val x = ::foo4
    }
    val test51 = generate {
        foo1(3)
        val x = ::foo5
    }
    val test61 = generate {
        foo1(3)
        val x = ::foo6
    }
    val test71 = generate {
        foo1(3)
        val x = ::foo7
    }
    val test81 = generate {
        foo1(3)
        val x = ::foo8
    }
}
