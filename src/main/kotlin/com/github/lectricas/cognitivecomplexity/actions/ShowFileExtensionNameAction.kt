package com.github.lectricas.cognitivecomplexity.actions

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager

class ShowFileExtensionNameAction : AnAction() {
    override fun update(e: AnActionEvent) {
    }

    override fun actionPerformed(event: AnActionEvent) {
        val currentDoc = FileEditorManager.getInstance(event.project!!).selectedTextEditor!!.document
        val currentFile = FileDocumentManager.getInstance().getFile(currentDoc)
        currentFile?.extension?.let {
            NotificationGroupManager.getInstance().getNotificationGroup("Custom Notification Group")
                .createNotification(it, NotificationType.INFORMATION)
                .notify(event.project)
        }
    }
}
