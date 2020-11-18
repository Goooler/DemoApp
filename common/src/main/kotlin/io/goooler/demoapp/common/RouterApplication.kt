package io.goooler.demoapp.common

import com.alibaba.android.arouter.launcher.ARouter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import io.goooler.demoapp.base.core.BaseApplication
import io.goooler.demoapp.common.util.CrashHandler
import io.goooler.demoapp.common.util.debugRun

open class RouterApplication : BaseApplication() {

    override fun initRight() {
        super.initRight()
        CrashHandler.init()
        initArouter()
        initSmartRefresh()
    }

    private fun initArouter() {
        debugRun {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun initSmartRefresh() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, refreshLayout ->
            refreshLayout.setPrimaryColorsId(
                android.R.color.transparent, android.R.color.darker_gray
            )
            ClassicsHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, refreshLayout ->
            refreshLayout.setPrimaryColorsId(
                android.R.color.transparent, android.R.color.darker_gray
            )
            ClassicsFooter(context)
        }
    }
}