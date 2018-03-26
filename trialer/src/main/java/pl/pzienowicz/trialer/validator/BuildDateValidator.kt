package pl.pzienowicz.trialer.validator

import android.content.Context
import android.util.Log
import java.util.*
import java.util.zip.ZipFile

class BuildDateValidator(private val daysAfterBuild: Int) : ValidatorInterface {

    override fun valid(context: Context): Boolean {
        val buildDate = getBuildDate(context)

        Log.e("Trialer", "buildDate: " + buildDate.toGMTString())

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, daysAfterBuild)

        if(buildDate.before(calendar.time)) {
            return false
        }

        return true
    }

    private fun getBuildDate(context: Context): Date {
        val applicationInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
        val zipFile = ZipFile(applicationInfo.sourceDir)
        val zipEntry = zipFile.getEntry("classes.dex")
        val time = zipEntry.time
        zipFile.close()

        return Date(time)
    }
}