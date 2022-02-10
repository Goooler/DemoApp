package io.goooler.demoapp.web

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class BlankFragment : Fragment(R.layout.web_blank_fragment) {
  lateinit var tvContent: TextView

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    tvContent = view.findViewById(R.id.tv_content)
  }
}
