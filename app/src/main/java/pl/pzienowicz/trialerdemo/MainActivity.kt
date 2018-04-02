package pl.pzienowicz.trialerdemo

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import pl.pzienowicz.trialer.Trialer
import pl.pzienowicz.trialer.action.DisplayToastAction
import pl.pzienowicz.trialer.listener.TrialEndedListener
import pl.pzienowicz.trialer.validator.InstallDateValidator
import pl.pzienowicz.trialer.validator.StaticDateValidator
import pl.pzienowicz.trialer.validator.ValidatorInterface
import java.util.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val someFutureDate = Calendar.getInstance()
        someFutureDate.add(Calendar.DATE, 30)

        val trialer = Trialer(this)
        trialer.trialEndedListener = object : TrialEndedListener {
            override fun onTrialEnded(validatorClass: ValidatorInterface) {
                Toast.makeText(applicationContext, "Trial ended by: " + validatorClass::class.java.simpleName  , Toast.LENGTH_LONG).show()
            }

        }
        trialer.addValidator(InstallDateValidator(10))
        trialer.addValidator(StaticDateValidator(someFutureDate.time))
        trialer.addAction(DisplayToastAction(this, "Toast by DisplayToastAction"))
        trialer.valid()
    }
}
