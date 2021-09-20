package com.github.lectricas.cognitivecomplexity.actions

import com.github.lectricas.cognitivecomplexity.EditorUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.idea.core.util.getLineCount
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.getChildOfType
import org.jetbrains.kotlin.psi.psiUtil.startOffset


class ShowCurrentMethodNameAction : AnAction() {
    override fun update(e: AnActionEvent) {
    }

    override fun actionPerformed(event: AnActionEvent) {
        val editor = FileEditorManager.getInstance(event.project!!).selectedTextEditor
        val currentFile = FileDocumentManager.getInstance().getFile(editor!!.document)
        val offset = editor.caretModel.offset
        var currentPsiFile = PsiManager.getInstance(event.project!!).findFile(currentFile!!)
        var currentPsiElement = currentPsiFile?.findElementAt(offset)

        val currentPsiFuntion: KtNamedFunction? =
            PsiTreeUtil.findFirstParent(currentPsiElement) { it is KtNamedFunction } as? KtNamedFunction

        val message: String
        val buttonTitle: String
        if (currentPsiFuntion != null) {
            val numberOfLines = (currentPsiFuntion.getChildOfType<KtBlockExpression>())?.getLineCount()
            message = "Current method is ${currentPsiFuntion.name}, line count: $numberOfLines"
            buttonTitle = "InsertAbove"
        } else {
            message = "Caret not in method right now"
            buttonTitle = "Ok"
        }

        val zeroIfPressed = Messages.showDialog(
            event.project,
            message,
            "Dialog",
            listOf(buttonTitle).toTypedArray(),
            0,
            Messages.getInformationIcon()
        )
        if (zeroIfPressed == 0 && currentPsiFuntion != null) {
            // не знаю как поставить изначальный офесет, может придумаю к вечеру. Пока что какие-то костыли тут.
            EditorUtil.insertText(editor, "// $message\n", currentPsiFuntion.startOffset, false)
        }
    }
}
