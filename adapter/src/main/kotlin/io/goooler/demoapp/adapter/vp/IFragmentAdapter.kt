package io.goooler.demoapp.adapter.vp

import androidx.fragment.app.Fragment

interface IFragmentAdapter {

  fun setData(fragments: List<Fragment>? = null, titles: List<String>? = null)

  fun getItem(position: Int): Fragment
}
