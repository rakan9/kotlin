/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.FirSessionComponent
import org.jetbrains.kotlin.name.ClassId


abstract class SealedClassInheritorsProvider : FirSessionComponent {
    abstract fun getSealedClassInheritors(firClass: FirRegularClass): List<ClassId>
    abstract fun setSealedInheritors(firClass: FirRegularClass, inheritors: List<ClassId>)
}

class SealedClassInheritorsProviderImpl : SealedClassInheritorsProvider() {
    private val inheritorsMap = hashMapOf<ClassId, List<ClassId>>()
    override fun getSealedClassInheritors(firClass: FirRegularClass): List<ClassId> {
        require(firClass.isSealed)
        return inheritorsMap[firClass.classId] ?: emptyList()
    }

    override fun setSealedInheritors(firClass: FirRegularClass, inheritors: List<ClassId>) {
        inheritorsMap[firClass.classId] = inheritors
    }
}

private val FirSession.sealedClassInheritorsProvider: SealedClassInheritorsProvider by FirSession.sessionComponentAccessor()

var FirRegularClass.sealedInheritors: List<ClassId>
    get() = session.sealedClassInheritorsProvider.getSealedClassInheritors(this)
    set(value) {
        session.sealedClassInheritorsProvider.setSealedInheritors(this, value)
    }
