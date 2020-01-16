package io.goooler.demoapp.util

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.goooler.demoapp.base.BaseDialogFragment
import java.util.concurrent.ArrayBlockingQueue

class DialogManager(private val maxSize: Int = 3) : DefaultLifecycleObserver {

    private val dialogQueue = ArrayBlockingQueue<DialogElement?>(maxSize)

    fun showDialog(element: DialogElement?) {
        addElement(element)
        if (dialogQueue.size == maxSize) justShow(getElement())
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        clearAll()
    }

    private fun justShow(element: DialogElement?) {
        element?.run {
            (dialog as? BaseDialogFragment)?.dismissListener = listener
            dialog?.show(fragmentManager, tag)
        }
    }

    private fun addElement(element: DialogElement?) {
        dialogQueue.add(element)
        dialogQueue.sortedByDescending { it?.priority ?: -1 }
    }

    private fun removeElement() {
        dialogQueue.run {
            if (isNotEmpty()) remove()
        }
    }

    private fun clearAll() {
        dialogQueue.clear()
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