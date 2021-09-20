package com.github.lectricas.cognitivecomplexity.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ui.Messages

class ShowProjectNameAction : AnAction() {
    override fun update(e: AnActionEvent) {
    }

    override fun actionPerformed(event: AnActionEvent) {
        val projectName = event.project?.name
        val currentDoc = FileEditorManager.getInstance(event.project!!).selectedTextEditor?.document
        val currentFile = FileDocumentManager.getInstance().getFile(currentDoc!!)
        Messages.showMessageDialog(
            event.project,
            "Project name is: $projectName, opened file is ${currentFile?.name}",
            "Hello World",
            Messages.getInformationIcon()
        )
    }
}
