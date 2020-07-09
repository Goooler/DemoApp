package io.goooler.demoapp.adapter.rv.tree

import io.goooler.demoapp.adapter.rv.base.BaseRvAdapter
import io.goooler.demoapp.adapter.rv.diff.BaseDiffAdapter
import io.goooler.demoapp.adapter.rv.diff.IModelDiff
import io.goooler.demoapp.adapter.tree.IModelTree

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

internal fun <T : IModelTree<T>> BaseRvAdapter<T>.fixToTree() {
    setIFix(object : BaseRvAdapter.IFix<T> {
        override fun fix(list: List<T>) = ArrayList<T>().also {
            list.forEach { tree ->
                it.addAll(IModelTree.ergodic(tree))
            }
        }
    })
}