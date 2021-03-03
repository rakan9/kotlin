/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.frontend.api.components

import org.jetbrains.kotlin.idea.frontend.api.symbols.KtSymbol
import org.jetbrains.kotlin.idea.frontend.api.types.KtType

data class KtTypeRendererOptions(
    val shortQualifiedNames: Boolean = false,
    val renderFunctionType: Boolean = true,
) {
    companion object {
        val DEFAULT = KtTypeRendererOptions()
        val SHORT_NAMES = DEFAULT.copy(shortQualifiedNames = true)
    }
}

data class KtDeclarationRendererOptions(
    val modifiers: Set<RendererModifier> = RendererModifier.ALL,
    val typeRendererOptions: KtTypeRendererOptions = KtTypeRendererOptions.DEFAULT,
    val parameterNamesInFunctionalTypes: Boolean = true,
    val unitReturnType: Boolean = false,
    val normalizedVisibilities: Boolean = false,
    val renderContainingDeclarations: Boolean = false,
    val approximateTypes: Boolean = false,
) {
    companion object {
        val DEFAULT = KtDeclarationRendererOptions()
    }
}

enum class RendererModifier(val includeByDefault: Boolean) {
    VISIBILITY(true),
    MODALITY(true),
    OVERRIDE(true),
    ANNOTATIONS(false),
    INNER(true),
    DATA(true),
    INLINE(true),
    EXPECT(true),
    ACTUAL(true),
    CONST(true),
    LATEINIT(true),
    FUN(true),
    VALUE(true)
    ;

    companion object {
        @JvmField
        val ALL_EXCEPT_ANNOTATIONS = values().filter { it.includeByDefault }.toSet()

        @JvmField
        val ALL = values().toSet()
    }
}

abstract class KtSymbolDeclarationRendererProvider : KtAnalysisSessionComponent() {
    abstract fun render(symbol: KtSymbol, options: KtDeclarationRendererOptions): String
    abstract fun render(type: KtType, options: KtTypeRendererOptions): String
}

interface KtSymbolDeclarationRendererMixIn : KtAnalysisSessionMixIn {
    fun KtSymbol.render(options: KtDeclarationRendererOptions = KtDeclarationRendererOptions.DEFAULT): String =
        analysisSession.symbolDeclarationRendererProvider.render(this, options)

    fun KtType.render(options: KtTypeRendererOptions = KtTypeRendererOptions.DEFAULT): String =
        analysisSession.symbolDeclarationRendererProvider.render(this, options)
}