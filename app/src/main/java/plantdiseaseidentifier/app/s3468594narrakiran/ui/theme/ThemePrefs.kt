package plantdiseaseidentifier.app.s3468594narrakiran.ui.theme

import android.content.Context


object ThemePrefs {

    private const val PREF = "theme_prefs"
    private const val KEY_DARK = "dark_theme"

    fun isDark(context: Context): Boolean {
        return context
            .getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .getBoolean(KEY_DARK, false)
    }

    fun setDark(context: Context, dark: Boolean) {
        context
            .getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_DARK, dark)
            .apply()
    }
}
