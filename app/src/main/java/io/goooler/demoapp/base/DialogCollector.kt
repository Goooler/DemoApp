package io.goooler.demoapp.base

import java.util.concurrent.ArrayBlockingQueue

object DialogCollector {
    val dialogQueue = ArrayBlockingQueue<BaseDialogFragment>(10)

    fun isShowing(): Boolean {
        return dialogQueue.isNotEmpty()
    }

    fun addDialog(dialog: BaseDialogFragment) {
        dialogQueue.add(dialog)
    }

    fun getDialog(): BaseDialogFragment? {
        return if (dialogQueue.isNotEmpty()) dialogQueue.first() else null
    }

    fun removeDialog() {
        dialogQueue.run {
            if (isNotEmpty()) remove()
        }
    }

    fun removeAll() {
        dialogQueue.clear()
    }
}