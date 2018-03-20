package pl.pzienowicz.trialerdemo

import android.app.Activity
import android.os.Bundle
import pl.pzienowicz.trialer.Trialer

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val trialer = Trialer(this)
        trialer.valid()
    }
}
