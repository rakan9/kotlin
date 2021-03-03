/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.frontend.api.fir.renderer

import org.jetbrains.kotlin.builtins.StandardNames
import org.jetbrains.kotlin.fir.declarations.FirValueParameter
import org.jetbrains.kotlin.fir.declarations.toAnnotationClassId
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall
import org.jetbrains.kotlin.fir.expressions.FirConstExpression
import org.jetbrains.kotlin.fir.expressions.argumentMapping
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef

internal fun StringBuilder.renderAnnotations(
    coneTypeIdeRenderer: ConeTypeIdeRenderer,
    annotations: List<FirAnnotationCall>
) {
    for (annotation in annotations) {
        if (!annotation.isParameterName()) {
            append(renderAnnotation(annotation, coneTypeIdeRenderer))
            append(" ")
        }
    }
}

private fun FirAnnotationCall.isParameterName(): Boolean {
    return toAnnotationClassId().asSingleFqName() == StandardNames.FqNames.parameterName
}

private fun renderAnnotation(annotation: FirAnnotationCall, coneTypeIdeRenderer: ConeTypeIdeRenderer): String {
    return buildString {
        append('@')
        val resolvedTypeRef = annotation.typeRef as? FirResolvedTypeRef
        require(resolvedTypeRef != null)
        append(coneTypeIdeRenderer.renderType(resolvedTypeRef.type))

        val arguments = renderAndSortAnnotationArguments(annotation)
        if (arguments.isNotEmpty()) {
            arguments.joinTo(this, ", ", "(", ")")
        }
    }
}

private fun renderAndSortAnnotationArguments(descriptor: FirAnnotationCall): List<String> {
    val argumentList = descriptor.argumentMapping?.entries?.map { (name, value) ->
        "$name = ${renderConstant(value)}"
    }
    return argumentList?.sorted() ?: emptyList()
}

private fun renderConstant(value: FirValueParameter): String {
    return when (value) {
        is FirConstExpression<*> -> value.toString()
        else -> "???"
    }
}