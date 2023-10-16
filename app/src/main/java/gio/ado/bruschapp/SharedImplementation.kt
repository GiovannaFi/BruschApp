package gio.ado.bruschapp

import android.content.Context
import android.content.SharedPreferences

private const val PROFILE = "PROFILE"


class SharedImplementation(context: Context) {

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(
        "profile chosen",
        Context.MODE_PRIVATE
    )

    init {
        if (sharedPrefs.getString(PROFILE, null) == null) {
            sharedPrefs.edit()?.putString(PROFILE, "")?.apply()
        }
    }

    fun saveProfile(name: String?) {
        sharedPrefs
            .edit()
            ?.putString(PROFILE, name)
            ?.apply()
    }

    fun getProfile(): String? {
        return sharedPrefs.getString(PROFILE, null)
    }
}