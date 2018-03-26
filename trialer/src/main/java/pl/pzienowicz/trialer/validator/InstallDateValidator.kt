package pl.pzienowicz.trialer.validator

import android.content.Context
import java.util.*

class InstallDateValidator(private val daysAfterInstall: Int) : ValidatorInterface {

    override fun valid(context: Context): Boolean {
        val nowCalendar = Calendar.getInstance()
        val now = nowCalendar.time

        val installDateCalendar = Calendar.getInstance()
        installDateCalendar.time = getInstallDate(context)
        installDateCalendar.add(Calendar.DATE, daysAfterInstall)

        if(installDateCalendar.time.before(now)) {
            return false
        }

        return true
    }

    private fun getInstallDate(context: Context): Date {
        return Date(context.packageManager.getPackageInfo(context.packageName, 0).firstInstallTime)
    }
}