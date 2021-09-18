package com.github.lectricas.cognitivecomplexity.services

import com.intellij.openapi.project.Project
import com.github.lectricas.cognitivecomplexity.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
