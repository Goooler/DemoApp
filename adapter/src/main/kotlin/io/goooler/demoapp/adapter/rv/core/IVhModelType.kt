package io.goooler.demoapp.adapter.rv.core

import androidx.annotation.LayoutRes

/**
 * Created on 2020/10/21.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface IVhModelType {

    /**
     * Get the viewType. You can treat layout ID as viewType.
     * @[LayoutRes]
     */
    val viewType: Int
}