package pl.pzienowicz.trialer.action

import android.app.Activity

open class FinishActivityAction(private val activity: Activity) : ActionInterface {

    override fun run() {
        activity.finish()
    }
}