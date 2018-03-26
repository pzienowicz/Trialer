package pl.pzienowicz.trialer

import android.content.Context
import pl.pzienowicz.trialer.listener.TrialEndedListener
import pl.pzienowicz.trialer.validator.ValidatorInterface
import java.util.*


class Trialer(private val context: Context) {

    var trialEndedListener: TrialEndedListener? = null

    private val validators = ArrayList<ValidatorInterface>()

    fun addValidator(validatorInterface: ValidatorInterface) {
        validators.add(validatorInterface)
    }

    fun valid() {
        validators.forEach { validator ->
            if(!validator.valid(context)) {
                trialEndedListener?.onTrialEnded(validator)
            }
        }
    }
}