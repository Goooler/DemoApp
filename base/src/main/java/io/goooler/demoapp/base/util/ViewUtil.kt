package io.goooler.demoapp.base.util

import android.view.View
import androidx.collection.ArrayMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object ViewUtil {

    fun findSupportFragment(target: View, activity: FragmentActivity): Fragment? {
        val tempViewToSupportFragment = ArrayMap<View, Fragment>()
        findAllSupportFragmentsWithViews(
                activity.supportFragmentManager.fragments, tempViewToSupportFragment
        )
        var result: Fragment? = null
        val activityRoot = activity.findViewById<View>(android.R.id.content)
        var current = target
        while (current != activityRoot) {
            result = tempViewToSupportFragment[current]
            if (result != null) {
                break
            }
            current = if (current.parent is View) {
                current.parent as View
            } else {
                break
            }
        }
        tempViewToSupportFragment.clear()
        return result
    }

    private fun findAllSupportFragmentsWithViews(
            topLevelFragments: Collection<Fragment>, result: MutableMap<View, Fragment>
    ) {
        topLevelFragments.forEach {
            it.view?.let { v ->
                result[v] = it
                findAllSupportFragmentsWithViews(it.childFragmentManager.fragments, result)
            }
        }
    }
}