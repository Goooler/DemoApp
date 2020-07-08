package io.goooler.demoapp.adapter.tree

import io.goooler.demoapp.adapter.base.BaseAdapter

/**
 * Created on 2019/11/18.
 *
 * @author feling
 * @version 1.1.0
 * @since 1.1.0
 */
fun <T : IModelTree<T>> BaseAdapter<T>.fixToTree() {
    setIFix(object : BaseAdapter.IFix<T> {
        override fun fix(list: List<T>) = ArrayList<T>().also {
            list.forEach { tree ->
                it.addAll(IModelTree.ergodic(tree))
            }
        }
    })
}

