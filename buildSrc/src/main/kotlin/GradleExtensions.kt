import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.implementation(dependency: Any): Dependency? =
    add("implementation", dependency)

fun DependencyHandler.testImplementation(dependencies: List<String>) {
    dependencies.forEach { dependency ->
        add("testImplementation", dependency)
    }
}
