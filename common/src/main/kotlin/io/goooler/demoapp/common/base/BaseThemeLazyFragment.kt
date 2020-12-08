package io.goooler.demoapp.common.base

import io.goooler.demoapp.base.core.ILazyFragment

abstract class BaseThemeLazyFragment : BaseThemeFragment(), ITheme, ILazyFragment {

    override fun onResume() {
        super.onResume()
        onFragmentResume()
    }
}
