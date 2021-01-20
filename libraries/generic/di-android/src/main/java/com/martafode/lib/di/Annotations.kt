@file:Suppress("unused")

package com.martafode.lib.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Scope
import kotlin.reflect.KClass

// TODO: Consider moving ActivityScoped & FragmentScoped.
// Do we really need those in every layer? Maybe not. If so, move it.

/**
 * Annotation for [Activity] scoped objects.
 */
@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScoped

/**
 * Annotation for [Fragment] scoped objects.
 */
@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScoped

/**
 * Annotation for injecting <out [ViewModel]> objects.
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
