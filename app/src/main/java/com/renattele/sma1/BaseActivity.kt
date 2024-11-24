package com.renattele.sma1

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.renattele.sma1.utils.NavigationAction

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val mainContainerId: Int

    fun navigate(
        destination: Fragment,
        destinationTag: String? = null,
        action: NavigationAction = NavigationAction.REPLACE,
        isAddToBackStack: Boolean = true,
        backStackTag: String? = null,
        isUseAnimation: Boolean = true,
    ) {
        val transaction = supportFragmentManager.beginTransaction()

        if (isUseAnimation) {
            transaction.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.slide_out_right,
            )
        }

        when (action) {
            NavigationAction.ADD -> transaction.add(mainContainerId, destination, destinationTag)

            NavigationAction.REPLACE -> transaction.replace(
                mainContainerId,
                destination,
                destinationTag
            )

            NavigationAction.REMOVE -> transaction.remove(destination)
        }

        if (isAddToBackStack) {
            transaction.addToBackStack(backStackTag)
        }

        transaction.setReorderingAllowed(true).commit()
    }
}

fun Activity.baseActivity(): BaseActivity = this as BaseActivity