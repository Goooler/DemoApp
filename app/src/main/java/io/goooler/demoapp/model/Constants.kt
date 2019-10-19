package io.goooler.demoapp.model

/**
 * 常量集合
 */
interface Constants {
    companion object {
        const val DB_NAME = "Demo.db"
        const val NULL_STRING = ""
        const val BASE_ACTIVITY = "BaseActivity"
        const val FINISH_ALL_ACTIVITY = "finished all activities"
        const val COORDINATES = "coordinates"
        const val PARAMS = "params"
        const val DATA = "data"
        const val KEY_AND_VALUE = "keyAndValue"
        const val LABEL = "label"
        const val X = "x"
        const val Y = "y"
        const val MIPMAP = "mipmap"
        const val GOTO_FRAGMENT_ID = "goto_fragment_id"
    }

    object SpKey {
        const val SP_RUN_INFO = "run_config"
        const val SP_FIRST_RUN = "firstRun"
    }
}