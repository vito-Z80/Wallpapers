package com.serdjuk.wallpapers.provider.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.serdjuk.wallpapers.provider.Core
import com.serdjuk.wallpapers.PREF_NAME
import com.serdjuk.wallpapers.PREF_SETTINGS_KEY
import com.serdjuk.wallpapers.model.SettingsModel

class Preference(private val gson: Gson) {

    private var _settings: SettingsModel? = null
    fun getSettings(context: Context) = _settings ?: readSettings(context = context)

    fun readSettings(context: Context): SettingsModel {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(PREF_SETTINGS_KEY, "")
        println(json)
        val settings =
            json?.let { gson.fromJson(it, SettingsModel::class.java) }
                ?: SettingsModel().also { update(PREF_SETTINGS_KEY, prefs, gson.toJson(it)) }
        _settings = settings
        return settings
    }


    fun writeSettings(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = gson.toJson(Core.preference.getSettings(context = context))
        update(PREF_SETTINGS_KEY, prefs, json)
    }

    private fun update(prefKey: String, prefs: SharedPreferences, json: String?) {
        val editor = prefs.edit()
        editor.putString(prefKey, json)
        editor.apply()
    }
}