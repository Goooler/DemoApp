package io.goooler.demoapp.util

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.base.BaseDialogFragment

object DialogManager {

    private val dialogQueue = ArrayList<DialogElement?>()
    private const val MAX_SIZE = 3


    fun showDialog(element: DialogElement?) {
        addElement(element)
        if (dialogQueue.size == MAX_SIZE) justShow(getElement())
    }

    private fun justShow(element: DialogElement?) {
        element?.run {
            (dialog as? BaseDialogFragment)?.dismissListener = listener
            dialog?.show(fragmentManager, tag)
        }
    }

    private fun addElement(element: DialogElement?) {
        dialogQueue.add(element)
        dialogQueue.sortByDescending { it?.priority ?: -1 }
    }

    private fun removeElement() {
        dialogQueue.run {
            if (isNotEmpty()) removeAt(0)
        }
    }

    private fun getElement(): DialogElement? {
        return if (dialogQueue.isNotEmpty()) dialogQueue.first() else null
    }

    private val listener = object : BaseDialogFragment.OnDismissListener {
        override fun onDismiss() {
            removeElement()
            getElement()?.run {
                dialog?.let {
                    justShow(this)
                }
            }
        }
    }

    class DialogElement(
            var dialog: DialogFragment?,
            var fragmentManager: FragmentManager,
            var tag: String? = null,
            var priority: Int = 1
    )
}