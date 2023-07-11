package com.quizmatch.app.utils

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.quizmatch.app.data.model.firebase.User
import com.quizmatch.app.ui.dashboard.DashboardActivity


//Method to check validation email
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
//Method to show toast on fragment
fun Fragment.showToast(resId: Int? = null, message: String? = null) {
    activity?.let {
        Toast.makeText(
            it, if (resId != null) {
                it.getString(resId)
            } else {
                message!!
            }, Toast.LENGTH_SHORT
        ).show()
    }
}

//Method to show toast on activity
fun Activity.showToast(resId: Int? = null, message: String? = null) {

    Toast.makeText(
        this, if (resId != null) {
            this.getString(resId)
        } else {
            message!!
        }, Toast.LENGTH_SHORT
    ).show()
}

/**
 * Execute navigation
 *
 * Execute fragment navigation based on [fragmentNavigationBuilder] options.
 * [fragmentNavigationBuilder] contains several options like fragment name,
 * Container name, add or replace fragment.
 *
 * @param fragmentNavigationBuilder
 */
fun FragmentActivity.executeNavigation(fragmentNavigationBuilder: FragmentNavigationBuilder) {
    val currentFragment: Fragment = fragmentNavigationBuilder.fragment
    val fts = supportFragmentManager.beginTransaction()
    currentFragment.arguments = fragmentNavigationBuilder.bundle
    if (fragmentNavigationBuilder.isAddFragment)
        fts.add(
            fragmentNavigationBuilder.container!!,
            currentFragment,
            currentFragment.javaClass.simpleName
        )
    else
        fts.replace(
            fragmentNavigationBuilder.container!!,
            currentFragment,
            currentFragment.javaClass.simpleName
        )

    if (fragmentNavigationBuilder.isBackStack)
        fts.addToBackStack(currentFragment.javaClass.simpleName)
    fts.commit()
}


data class FragmentNavigationBuilder(
    var fragment: Fragment,
    var container: Int? = null,
    var isAddFragment: Boolean = false,
    var isBackStack: Boolean = false,
    var bundle: Bundle? = null
) {
    fun container(container: Int) = apply { this.container = container }
    fun isAddFragment(isAddFragment: Boolean) = apply { this.isAddFragment = isAddFragment }
    fun isBackStack(isBackStack: Boolean) = apply { this.isBackStack = isBackStack }
    fun bundle(bundle: Bundle?) = apply { this.bundle = bundle }
    fun build() = FragmentNavigationBuilder(fragment, container, isAddFragment, isBackStack, bundle)
}
//fun Activity.navigationDashboardScreen(bundle: Bundle = bundleOf()) {
//    (this as DashboardActivity).executeNavigation(
//        FragmentNavigationBuilder(DashboardFragment())
//            .container(homeContainer())
//            .isAddFragment(true)
//            .isBackStack(true)
//            .bundle(bundle)
//            .build()
//    )
//
//}
class CustomException(message: String? = "Something went wrong!!!") : Exception(message)


interface OnItemClickListener {
    fun onItemClick(position: Int,itemDetail: User)
}