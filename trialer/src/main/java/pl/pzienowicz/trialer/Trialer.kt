package pl.pzienowicz.trialer

import android.content.Context
import pl.pzienowicz.trialer.listener.TrialEndedListener
import java.util.*
import java.util.zip.ZipFile


class Trialer(private val context: Context) {

    var trialEndedListener: TrialEndedListener? = null
    var endDate: Date? = null
    var daysAfterBuild: Int? = null
    var daysAfterInstall: Int? = null

    private var buildDate: Date
    private var installDate: Date

    init {
        installDate = getInstallDate()
        buildDate = getBuildDate()
    }

    private fun getInstallDate(): Date {
        return Date(context.packageManager.getPackageInfo(context.packageName, 0).firstInstallTime)
    }

    private fun getBuildDate(): Date {
        val applicationInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
        val zipFile = ZipFile(applicationInfo.sourceDir)
        val zipEntry = zipFile.getEntry("classes.dex")
        val time = zipEntry.time
        zipFile.close()

        return Date(time)
    }

    fun valid() {
        if(endDate != null && endDate!!.before(Date())) {
            trialEndedListener?.onTrialEnded()
            return
        }

        if(daysAfterBuild != null) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, daysAfterBuild!!)

            if(buildDate.before(calendar.time)) {
                trialEndedListener?.onTrialEnded()
                return
            }
        }

        if(daysAfterInstall != null) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, daysAfterInstall!!)

            if(installDate.before(calendar.time)) {
                trialEndedListener?.onTrialEnded()
                return
            }
        }
    }
}