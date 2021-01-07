package io.goooler.demoapp.map.ui.fragment

import com.amap.api.maps.LocationSource
import com.amap.api.maps.MapView
import com.amap.api.maps.model.MyLocationStyle
import io.goooler.demoapp.common.base.BaseThemeFragment
import io.goooler.demoapp.map.R
import io.goooler.demoapp.map.databinding.MapAmapFragmentBinding

class AmapFragment private constructor() :
  BaseThemeFragment<MapAmapFragmentBinding>(R.layout.map_amap_fragment) {

  lateinit var mapView: MapView

  override fun initOnce() {
    mapView = binding.mapView
    binding.mapView.onCreate(null)
    mapView.map.run {
      setLocationSource(listener)
      myLocationStyle = MyLocationStyle().interval(2000)
      isMyLocationEnabled = true
    }
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onDestroyView() {
    mapView.onDestroy()
    super.onDestroyView()
  }

  private val listener = object : LocationSource {
    override fun activate(locationChangedListener: LocationSource.OnLocationChangedListener) {
    }

    override fun deactivate() {
    }
  }

  companion object {
    fun newInstance(): AmapFragment = AmapFragment()
  }
}