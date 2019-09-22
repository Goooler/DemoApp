package io.goooler.demoapp.model

/**
 * 常量集合
 */
interface Constants {
    companion object {
        val DB_NAME = "Demo.db"
        val NULL_STRING = ""
        val NULL_OBJECT: String? = null
        val SP_RUNINFO = "run_config"
        val SP_FIRST_RUN = "firstRun"
        val BASE_ACTIVITY = "BaseActivity"
        val FINISH_ALL_ACTIVITY = "finished all activities"
        val COORDINATES = "coordinates"
        val PARAMS = "params"
        val DATA = "data"
        val KEY_AND_VALUE = "keyAndValue"
        val LABLE = "lable"
        val X = "x"
        val Y = "y"
        val MIPMAP = "mipmap"
        val GOTO_FRAGMENT_ID = "goto_fragment_id"
    }
}