package com.martafode.feature.main.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class DataPreferencesHelper @Inject constructor(
    private val dataPreferences: SharedPreferences
): PreferencesHelper {
    /**
     * This block of code is just delegating all of the functionality to the
     * shared preferences since we can't use the kotlin delegation due to shared preferences not
     * having a interface that we could extend
     */

    override fun getString(key: String, defaultValue: String?): String? =
        if (dataPreferences.contains(key) || defaultValue != null) {
            dataPreferences.getString(key, defaultValue)
        } else {
            null
        }

    override fun getInt(key: String, defaultValue: Int): Int? =
        if (dataPreferences.contains(key) || defaultValue != PreferencesHelper.DEFAULT_INT_VALUE) {
            dataPreferences.getInt(key, defaultValue)
        } else {
            null
        }

    override fun getLong(key: String, defaultValue: Long): Long? =
        if (dataPreferences.contains(key) || defaultValue != PreferencesHelper.DEFAULT_LONG_VALUE) {
            dataPreferences.getLong(key, defaultValue)
        } else {
            null
        }

    override fun getBoolean(key: String, defaultValue: Boolean) = dataPreferences.getBoolean(key, defaultValue)

    /**
     * write(key, value) does not guarantee that the operation is successful.
     * It will fail silently because it uses apply() which is async
     */
    override fun write(key: String, value: Any) {
        val editor = dataPreferences.edit()

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
        return dataPreferences.all
    }

    override fun remove(key: String): Boolean {
        val editor = dataPreferences.edit()
        editor.remove(key)
        return editor.commit()
    }
}
