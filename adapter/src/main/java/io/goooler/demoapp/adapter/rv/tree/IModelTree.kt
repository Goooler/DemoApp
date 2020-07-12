package io.goooler.demoapp.adapter.rv.tree

import io.goooler.demoapp.adapter.rv.base.IModelType

/**
 * Created on 2019/08/22.
 *
 * 树。
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface IModelTree<M : IModelTree<M>> : IModelType {
    /**
     * 获取子树列表。
     */
    fun getChildren(): List<M>? = null

    /**
     * 是否把父节点加到子节点的前一个。
     */
    fun isAddParent(): Boolean = false

    companion object {
        /**
         * 从一颗树中获取所有节点。
         */
        fun <M : IModelTree<M>> ergodic(tree: M): List<M> {
            return ArrayList<M>().also {
                findNode(tree, it)
            }
        }

        /**
         * 递归遍历。
         */
        private fun <M : IModelTree<M>> findNode(tree: M, list: MutableList<M>) {
            val childTreeList = tree.getChildren()
            if (childTreeList == null) {
                list.add(tree)
                return
            }
            if (tree.isAddParent()) {
                list.add(tree)
            }
            childTreeList.forEach {
                findNode(it, list)
            }
        }
    }
}