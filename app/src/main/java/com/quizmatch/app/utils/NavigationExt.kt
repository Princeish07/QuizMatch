package com.quizmatch.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import com.quizmatch.app.R
import com.quizmatch.app.ui.dashboard.DashboardActivity
import com.quizmatch.app.ui.landing.LandingActivity
import com.quizmatch.app.ui.question.QuestionListFragment

/*
* todo Created By Prince at 11/04/23.
*/

/**
 * Method to routing to Dashboard Activity
 */
fun Context.routeToDashboardActivityScreen(bundle: Bundle = bundleOf()) {
    val intent = Intent(this, DashboardActivity::class.java).apply {
        putExtras(bundle)
    }
    startActivity(intent)
}

/**
 * Method to routing to Landing Activity
 */
fun Context.routeToLandingActivityScreen(bundle: Bundle = bundleOf()) {
    val intent = Intent(this, LandingActivity::class.java).apply {
        putExtras(bundle)
    }
    startActivity(intent)
}

/*----------------------------------------------------------------------------------------------*/


//Method to navigation Question List in fragment
fun Activity.navigateToQuestionListScreen(bundle: Bundle = bundleOf()) {
    (this as DashboardActivity).executeNavigation(
        FragmentNavigationBuilder(QuestionListFragment())
            .container(landingContainer())
            .isAddFragment(false)
            .isBackStack(false)
            .bundle(bundle)
            .build()
    )

}


/*----------------------------------------------------------------------------------------------*/
fun landingContainer(): Int = R.id.fcv_dashboard

