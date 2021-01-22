package com.martafode.feature.main.data.local

import android.content.SharedPreferences
import com.martafode.feature.main.data.local.PreferencesHelper.Companion.DEFAULT_INT_VALUE
import com.martafode.feature.main.data.local.PreferencesHelper.Companion.DEFAULT_LONG_VALUE
import javax.inject.Inject

class AppPreferencesHelper @Inject constructor(
    private val appPreferences: SharedPreferences,
): PreferencesHelper {
    companion object {
        const val LAST_DATA_FETCH_TIMESTAMP = "lastDataFetchTimestamp"
    }

    /**
     * This block of code is just delegating all of the functionality to the
     * shared preferences since we can't use the kotlin delegation due to shared preferences not
     * having a interface that we could extend
     */

    override fun getString(key: String, defaultValue: String?): String? =
        if (appPreferences.contains(key) || defaultValue != null) {
            appPreferences.getString(key, defaultValue)
        } else {
            null
        }

    override fun getInt(key: String, defaultValue: Int): Int? =
        if (appPreferences.contains(key) || defaultValue != DEFAULT_INT_VALUE) {
            appPreferences.getInt(key, defaultValue)
        } else {
            null
        }

    override fun getLong(key: String, defaultValue: Long): Long? =
        if (appPreferences.contains(key) || defaultValue != DEFAULT_LONG_VALUE) {
            appPreferences.getLong(key, defaultValue)
        } else {
            null
        }

    override fun getBoolean(key: String, defaultValue: Boolean) = appPreferences.getBoolean(key, defaultValue)

    /**
     * write(key, value) does not guarantee that the operation is successful.
     * It will fail silently because it uses apply() which is async
     */
    override fun write(key: String, value: Any) {
        val editor = appPreferences.edit()

        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Boolean -> editor.putBoolean(key, value)
            else -> throw ClassCastException("This type is not yet supported")
        }

        editor.apply()
    }

    override fun retrieveAllData(): Map<String, *> {
        return appPreferences.all
    }

    override fun remove(key: String): Boolean {
        val editor = appPreferences.edit()
        editor.remove(key)
        return editor.commit()
    }
}
