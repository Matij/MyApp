package com.martafode.feature.main.data.local

interface PreferencesHelper {
    companion object {
        const val DEFAULT_INT_VALUE = Int.MAX_VALUE
        const val DEFAULT_LONG_VALUE = Long.MAX_VALUE
    }

    /**
     * Writes a [value] to be stored in the shared preferences
     * under the given [key].
     */
    fun write(key: String, value: Any)

    /**
     * Retrieve a [String] for a given [key]. If the value associated
     * to the key is not a [String] then it will throw a [ClassCastException].
     * If there is no value associated to the [key] but the [defaultValue] is provided
     * then the [defaultValue] will be returned.
     * When [defaultValue] is not provided _null_ is returned.
     */
    fun getString(key: String, defaultValue: String? = null): String?

    /**
     * Retrieve an [Int] for a given [key]. If the value associated
     * to the key is not a [Int] then it will throw a [ClassCastException].
     * If there is no value associated to the [key] the [defaultValue] is returned.
     */
    fun getInt(key: String, defaultValue: Int = DEFAULT_INT_VALUE): Int?

    /**
     * Retrieve an [Long] for a given [key]. If the value associated
     * to the key is not a [Long] then it will throw a [ClassCastException].
     * If there is no value associated to the [key] the [defaultValue] is returned.
     */
    fun getLong(key: String, defaultValue: Long = DEFAULT_LONG_VALUE): Long?

    /**
     * Retrieve an [Boolean] for a given [key]. If the value associated
     * to the key is not a [Boolean] then it will throw a [ClassCastException].
     * If there is no value associated to the [key] the [defaultValue] is returned.
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    /**
     * Retrieve all data stored into SharedPreferences linked to this PreferencesHelper instance
     */
    fun retrieveAllData(): Map<String, *>

    /**
     * Removes the [key] and respective value from the user shared preferences
     * and returns _true_ when the operation is successful or _false_ otherwise.
     */
    fun remove(key: String): Boolean
}
