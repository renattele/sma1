import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.crashlytics)
}

fun loadProperties(vararg files: File): Properties {
    require(files.isNotEmpty()) { "No properties files provided" }
    val properties = Properties()
    files.forEach { file ->
        if (file.exists()) {
            properties.load(file.inputStream().buffered())
        }
    }
    return properties
}

val localProperties =
    loadProperties(
        rootProject.file("sample.local.properties"),
        rootProject.file("local.properties"),
    )

android {
    namespace = "com.team6.smartbudget.sma1"
    compileSdk =
        libs.versions.sdk.compile
            .get()
            .toInt()

    defaultConfig {
        applicationId = "com.team6.smartbudget.sma1"
        minSdk =
            libs.versions.sdk.min
                .get()
                .toInt()
        targetSdk =
            libs.versions.sdk.target
                .get()
                .toInt()
        versionCode =
            libs.versions.version.code
                .get()
                .toInt()
        versionName =
            libs.versions.version.name
                .get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = localProperties["signing.key.alias"] as String
            keyPassword = localProperties["signing.key.password"] as String
            storeFile = rootProject.file(localProperties["signing.store.file"] as String)
            storePassword = localProperties["signing.store.password"] as String
        }
    }

    buildTypes {
        defaultConfig {
            buildConfigField("int", "DATABASE_VERSION", versionCode.toString())
            buildConfigField(
                "String",
                "API_BASE_URL",
                "\"${localProperties["api.url"]}\"",
            )
            buildConfigField(
                "String",
                "API_HOSTNAME",
                "\"${localProperties["api.hostname"]}\"",
            )
            buildConfigField(
                "String",
                "API_CERTIFICATE",
                "\"${localProperties["api.certificate"]}\"",
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility =
            JavaVersion.valueOf(
                libs.versions.compatibility.source
                    .get(),
            )
        targetCompatibility =
            JavaVersion.valueOf(
                libs.versions.compatibility.target
                    .get(),
            )
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":features:onboarding"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    implementation(libs.coil.okhttp)

    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization.kotlin)
    implementation(libs.kotlin.serialization.json)

    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.datastore)

    ksp(libs.dagger.compiler)
    implementation(libs.dagger)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    detektPlugins(libs.detekt.formatting)

    implementation(libs.kotlinx.collections.immutable)
    implementation(project(":features:onboarding"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)
}


room {
    schemaDirectory("$rootDir/schemas")
}

detekt {
    buildUponDefaultConfig = true
    allRules = true
    autoCorrect = true
    config.setFrom("$rootDir/config/detekt.yml")
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required = true
        xml.required = true
        sarif.required = true
        md.required = true
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = libs.versions.java.get()
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = libs.versions.java.get()
}

ktlint {
    reporters {
        reporter(ReporterType.HTML)
    }
}

tasks.withType<KotlinCompilationTask<*>> {
    compilerOptions.optIn.add("kotlin.uuid.ExperimentalUuidApi")
}