package io.goooler.demoapp.login.ui

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import io.goooler.demoapp.base.core.BaseActivity
import io.goooler.demoapp.common.router.RouterManager
import io.goooler.demoapp.common.router.RouterPath
import io.goooler.demoapp.login.R

@Route(path = RouterPath.LOGIN)
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 首次安装立即打开，后续切后台没有杀死 app 的情况下会触发反复进入 launcherActivity 的 bug
        if ((intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish()
            return
        }
        if (intent.action == RouterManager.RE_LOGIN) {
            setContentView(R.layout.login_activity)
        } else {
            RouterManager.goMain()
            finish()
        }
    }
}