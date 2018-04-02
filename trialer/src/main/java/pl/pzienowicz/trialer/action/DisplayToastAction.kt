package pl.pzienowicz.trialer.action

import android.content.Context
import android.widget.Toast

open class DisplayToastAction(private val context: Context, private val message: String) : ActionInterface {

    override fun run() {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}