package io.goooler.demoapp.main.ui

import com.alibaba.android.arouter.facade.annotation.Route
import io.goooler.demoapp.common.base.BaseThemeActivity
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.main.databinding.MainWidgetActivityBinding

@Route(path = RouterPath.WIDGET)
class WidgetActivity : BaseThemeActivity<MainWidgetActivityBinding>()
