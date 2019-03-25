package pl.pzienowicz.trialer.validator

import android.content.Context
import android.content.SharedPreferences

class RunTimesValidator (private val runTimes: Int) : ValidatorInterface {

    override fun valid(context: Context): Boolean {

        var executedTimes = getSharedPreferences(context).getInt(PREFS_NAME, 0)
        executedTimes++

        if(executedTimes > runTimes) {
            return false
        }

        getSharedPreferences(context).edit().putInt(PREFS_NAME, executedTimes).apply()

        return true
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_FILENAME, 0)
    }

    companion object {
        const val PREFS_FILENAME = "pl.pzienowicz.trialer.prefs"
        const val PREFS_NAME = "runTimes"
    }
}