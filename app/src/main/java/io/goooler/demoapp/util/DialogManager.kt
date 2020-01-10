package io.goooler.demoapp.util

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import io.goooler.demoapp.base.BaseDialogFragment
import java.util.concurrent.ArrayBlockingQueue

object DialogManager {

    private val dialogQueue = ArrayBlockingQueue<DialogElement>(10)

    fun showDialog(dialog: BaseDialogFragment, fragmentManager: FragmentManager, tag: String?) {
        if (dialogQueue.isEmpty()) {
            justShow(dialog, fragmentManager, tag)
        }
        dialogQueue.add(DialogElement(dialog, fragmentManager, tag))
    }

    private fun getElement(): DialogElement? {
        return if (dialogQueue.isNotEmpty()) dialogQueue.first() else null
    }

    private val dialogListener = object : BaseDialogFragment.OnDismissListener {
        override fun onDismiss() {
            dialogQueue.remove()
            getElement()?.run {
                justShow(dialog, fragmentManager, tag)
            }
        }
    }

    private fun justShow(dialog: DialogFragment, fragmentManager: FragmentManager, tag: String?) {
        (dialog as? BaseDialogFragment)?.dismissListener = dialogListener
        dialog.show(fragmentManager, tag)
    }

    private class DialogElement(
            var dialog: DialogFragment,
            var fragmentManager: FragmentManager,
            var tag: String?
    )
}