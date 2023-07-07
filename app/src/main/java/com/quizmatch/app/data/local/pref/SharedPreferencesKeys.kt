package com.quizmatch.app.data.local.pref

/**
 * Shared preferences keys
 *
 * @constructor Create empty Shared preferences keys
 */
interface SharedPreferencesKeys {
    companion object {
        const val PRIVATE_MODE = 0
        const val SHAREPRE_NAME = "QuizMatchSP"
        const val SCORE = "score"
        const val USER_PROFILE = "userProfile"
    }
}