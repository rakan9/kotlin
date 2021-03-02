/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.resolve.calls.model

import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor
import org.jetbrains.kotlin.psi.Call
import org.jetbrains.kotlin.psi.ValueArgument
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
import org.jetbrains.kotlin.types.KotlinType

interface ResolvedCall<D : CallableDescriptor> : ResolvedCallMarker {
    val status: ResolutionStatus

    /** The call that was resolved to this ResolvedCall  */
    val call: Call

    /** A target callable descriptor as it was accessible in the corresponding scope, i.e. with type arguments not substituted  */
    val candidateDescriptor: D

    /** Type arguments are substituted. This descriptor is guaranteed to have NO declared type parameters  */
    val resultingDescriptor: D

    /** If the target was an extension function or property, this is the value for its receiver parameter  */
    val extensionReceiver: ReceiverValue?

    /** If the target was a member of a class, this is the object of that class to call it on  */
    val dispatchReceiver: ReceiverValue?

    /** Determines whether receiver argument or this object is substituted for explicit receiver  */
    val explicitReceiverKind: ExplicitReceiverKind

    /** Values (arguments) for value parameters  */
    val valueArguments: Map<ValueParameterDescriptor, ResolvedValueArgument>

    /** Values (arguments) for value parameters indexed by parameter index  */
    val valueArgumentsByIndex: List<ResolvedValueArgument>?

    /** The result of mapping the value argument to a parameter  */
    fun getArgumentMapping(valueArgument: ValueArgument): ArgumentMapping

    /** What's substituted for type parameters  */
    val typeArguments: Map<TypeParameterDescriptor, KotlinType>

    /** Data flow info for each argument and the result data flow info  */
    val dataFlowInfoForArguments: DataFlowInfoForArguments
    val smartCastDispatchReceiverType: KotlinType?
}

val ResolvedCallMarker.resultingDescriptor: CallableDescriptor
    get() {
        require(this is ResolvedCall<*>)
        return this.resultingDescriptor
    }