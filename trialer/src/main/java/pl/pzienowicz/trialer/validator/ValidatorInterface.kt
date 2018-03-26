package pl.pzienowicz.trialer.validator

import android.content.Context

interface ValidatorInterface {
    fun valid(context: Context): Boolean
}