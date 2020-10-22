package io.goooler.demoapp.common.util

import com.blankj.utilcode.util.SPUtils
import io.goooler.demoapp.base.util.fromJson

object AppUserInfoManager {

    private const val SP_USER_INFO_KEY = "spUserInfoKey"
    private val spUtil = SPUtils.getInstance("spUserInfoName")

    var userId: Long = 0
        private set
    var token: String? = null
        private set
    var userName: String? = null
        private set
    var nickName: String? = null
        private set

    init {
        spUtil.getString(SP_USER_INFO_KEY).fromJson<UserInfoBean>()?.let {
            userId = it.userId
            token = it.token
            userName = it.userName
            nickName = it.nickName
        }
    }

    fun saveUserInfo(bean: UserInfoBean) {
        bean.let {
            userId = it.userId
            token = it.token
            userName = it.userName
            nickName = it.nickName
            spUtil.put(SP_USER_INFO_KEY, it.toString())
        }
    }

    fun resetUserInfo() {
        userId = 0
        token = null
        userName = null
        nickName = null
        spUtil.put(SP_USER_INFO_KEY, "")
    }

    class UserInfoBean(
        val userId: Long,
        val token: String,
        val userName: String,
        val nickName: String
    )
}