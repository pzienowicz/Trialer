package pl.pzienowicz.trialer.listener

import pl.pzienowicz.trialer.validator.ValidatorInterface

interface TrialEndedListener {
    fun onTrialEnded(validatorClass: ValidatorInterface)
}