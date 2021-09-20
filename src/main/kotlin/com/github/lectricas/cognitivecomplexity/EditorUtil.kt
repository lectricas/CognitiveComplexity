package com.github.lectricas.cognitivecomplexity

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.command.UndoConfirmationPolicy
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project

object EditorUtil {

    fun runWriteAction(writeAction: Runnable, project: Project?, document: Document) {
        val application = ApplicationManager.getApplication()
        if (application.isDispatchThread) {
            application.runWriteAction {
                CommandProcessor.getInstance()
                    .executeCommand(project, writeAction, null, null, UndoConfirmationPolicy.DEFAULT, document)
            }
        } else {
            application.invokeLater {
                application.runWriteAction {
                    CommandProcessor.getInstance()
                        .executeCommand(project, writeAction, null, null, UndoConfirmationPolicy.DEFAULT, document)
                }
            }
        }
    }

    fun insertText(editor: Editor, text: String, offset: Int, moveCaretToEnd: Boolean) {
        runWriteAction(Runnable {
            editor.document.insertString(offset, text)
            if (moveCaretToEnd) {
                offsetCaret(editor, text.length)
            }
        }, editor.project, editor.document)
    }

    fun offsetCaret(editor: Editor, offset: Int) {
        editor.caretModel.moveToOffset(editor.caretModel.offset + offset)
    }
}
