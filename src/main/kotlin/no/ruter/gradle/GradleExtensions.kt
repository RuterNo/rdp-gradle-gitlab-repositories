package no.ruter.gradle

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.credentials.HttpHeaderCredentials
import org.gradle.authentication.http.HttpHeaderAuthentication

class GradleExtensions private constructor() {
    companion object {
        @JvmStatic
        fun RepositoryHandler.gitlabGroup(
            project: Project,
            groupId: String,
            groupName: String,
            includeGroups: Set<String>? = null,
        ) {
            maven { repo ->
                repo.name = "gitLabGroup${groupName}Maven"
                repo.url = project.uri("https://gitlab.com/api/v4/groups/$groupId/-/packages/maven")
                repo.credentials(HttpHeaderCredentials::class.java) {
                    val jobToken = System.getenv("CI_JOB_TOKEN")
                    it.name = if (jobToken != null) "Job-Token" else "Private-Token"
                    it.value = jobToken ?: project.findProperty("gitLabPrivateToken") as String? ?: error(
                        "Missing required Gradle property (~/.gradle/gradle.properties): gitLabPrivateToken",
                    )
                }
                if (!includeGroups.isNullOrEmpty()) {
                    repo.content {
                        includeGroups.forEach { group ->
                            it.includeGroupByRegex(group)
                        }
                    }
                }
                repo.authentication {
                    it.register("header", HttpHeaderAuthentication::class.java)
                }
            }
        }
    }
}
