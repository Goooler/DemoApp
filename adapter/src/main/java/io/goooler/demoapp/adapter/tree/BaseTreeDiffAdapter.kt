package io.goooler.demoapp.adapter.tree

import io.goooler.demoapp.adapter.diff.BaseDiffAdapter
import io.goooler.demoapp.adapter.diff.IModelDiff

/**
 * Created on 2019/11/18.
 *
 * @author feling
 * @version 1.1.0
 * @since 1.1.0
 */
abstract class BaseTreeDiffAdapter<T> :
    BaseDiffAdapter<T>() where  T : IModelTree<T>, T : IModelDiff<T> {
    init {
        fixToTree()
    }
}