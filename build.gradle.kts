import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.android.library) apply false
}

subprojects {
   tasks.withType<KotlinCompile> {
       compilerOptions {
           freeCompilerArgs.addAll(
               "-P",
               "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + layout.buildDirectory.get().asFile.absolutePath + "/compose_metrics"
           )
           freeCompilerArgs.addAll(
               "-P",
               "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + layout.buildDirectory.get().asFile.absolutePath + "/compose_metrics"
           )
       }
   }
}