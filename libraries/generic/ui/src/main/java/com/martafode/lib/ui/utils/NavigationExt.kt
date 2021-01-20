package com.martafode.lib.ui.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

fun Fragment.findNavControllerInActivity(@IdRes viewId: Int): NavController =
    Navigation.findNavController(requireActivity(), viewId)

// It returns same result as `findNavControllerInActivity` but it seems to be better to look at smaller set.
fun Fragment.findNavControllerInFragment(@IdRes viewId: Int): NavController {
    val navHostFragment = childFragmentManager.findFragmentById(viewId) as NavHostFragment
    return navHostFragment.navController
}
