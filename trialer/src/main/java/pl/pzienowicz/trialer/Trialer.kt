package pl.pzienowicz.trialer

import android.content.Context
import pl.pzienowicz.trialer.action.ActionInterface
import pl.pzienowicz.trialer.listener.TrialEndedListener
import pl.pzienowicz.trialer.validator.ValidatorInterface
import java.util.*


class Trialer(private val context: Context) {

    var trialEndedListener: TrialEndedListener? = null

    private val validators = ArrayList<ValidatorInterface>()
    private val actions = ArrayList<ActionInterface>()

    fun addValidator(validatorInterface: ValidatorInterface) {
        validators.add(validatorInterface)
    }

    fun addAction(actionInterface: ActionInterface) {
        actions.add(actionInterface)
    }

    fun valid() {
        validators.forEach { validator ->
            if(!validator.valid(context)) {
                trialEndedListener?.onTrialEnded(validator)

                actions.forEach { action ->
                    action.run()
                }
            }
        }
    }
}