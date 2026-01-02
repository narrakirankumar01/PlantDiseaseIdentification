package plantdiseaseidentifier.app.s3468594narrakiran


import android.content.Context

object LanguagePrefs {

    private const val PREF_NAME = "language_pref"
    private const val KEY_LANG = "lang"

    fun saveLanguage(context: Context, langCode: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANG, langCode).apply()
    }

    fun getLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANG, "en") ?: "en"
    }
}
