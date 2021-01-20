// ktlint-disable filename
@file:Suppress("unused")

package com.martafode.lib.di

import javax.inject.Scope

/**
 * Annotation for [Application] scoped objects.
 *
 * In Dagger, an unscoped component cannot depend on a scoped component. As
 * [AppComponent] is a scoped component (`@Singleton`, we create a custom scope
 * to be used by all activity components. Additionally, a component with
 * a specific scope cannot have a sub component with the same scope.
 */
@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScoped
