package pl.pzienowicz.trialer.validator

import android.content.Context
import java.util.*

class StaticDateValidator(private val staticDate: Date) : ValidatorInterface {

    override fun valid(context: Context): Boolean {
        if(staticDate.before(Date())) {
            return false
        }

        return true
    }
}