package io.goooler.demoapp.adapter.tree

import io.goooler.demoapp.adapter.list.BaseListAdapter

/**
 * Created on 2019/11/18.
 *
 * @author feling
 * @version 1.1.0
 * @since 1.1.0
 */
abstract class BaseTreeAdapter<T> : BaseListAdapter<T>() where  T : IModelTree<T> {
    init {
        this.fixToTree()
    }
}