package io.goooler.demoapp.adapter.vp

import androidx.annotation.IntRange
import androidx.fragment.app.Fragment

interface IFragmentAdapter {

  fun setData(fragments: List<Fragment>? = null, titles: List<String>? = null)

  fun getItem(@IntRange(from = 0) position: Int): Fragment
}
