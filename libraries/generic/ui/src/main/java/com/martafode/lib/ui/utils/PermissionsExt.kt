package com.martafode.lib.ui.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.coroutinespermission.PermissionManager

/* CHECK PERMISSIONS */

data class PermissionCheckResult(val granted: Set<String>, val needed: Set<String>)

// https://stackoverflow.com/questions/30549561/how-to-check-grants-permissions-at-run-time
fun Activity.checkPermissions(permissions: Collection<String>) = permissions.fold(
    initial = PermissionCheckResult(setOf(), setOf()),
    operation = { (granted, needed), permission ->
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            PermissionCheckResult(granted + permission, needed)
        } else {
            PermissionCheckResult(granted, needed + permission)
        }
    }
)

fun Fragment.checkPermissions(permissions: Collection<String>) = requireActivity().checkPermissions(permissions)

suspend fun PermissionManager.Companion.requestPermissions(
    fragment: Fragment,
    reqId: Int,
    permissions: Iterable<String>
): PermissionResult = requestPermissions(fragment, reqId, *permissions.toList().toTypedArray())

// https://blog.mindorks.com/implementing-easy-permissions-in-android-android-tutorial

/* OPEN APP SETTINGS */

const val REQUEST_PERMISSION_SETTING = 1

val Activity.appSettingsIntent get(): Intent =
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
    }

fun Fragment.openAppSettings(requestId: Int = REQUEST_PERMISSION_SETTING) {
    startActivityForResult(requireActivity().appSettingsIntent, requestId)
}

fun Activity.openAppSettings(requestId: Int = REQUEST_PERMISSION_SETTING) {
    startActivityForResult(appSettingsIntent, requestId)
}
