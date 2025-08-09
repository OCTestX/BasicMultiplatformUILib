import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
//    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
}

group = "io.github.octestx"
version = "0.1.5.2"

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
//    iosX64()
//    iosArm64()
//    iosSimulatorArm64()
//    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                api(libs.basic.multiplatform.lib)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(compose.animation)
                // Koin Compose 支持（如需）
                api(libs.koin.compose)
                //https://github.com/DevSrSouza/compose-icons
                //https://tabler.io/icons
                api(libs.tabler.icons)
                api(libs.hotpreview)
                //https://github.com/cashapp/molecule/tree/trunk
                implementation(libs.molecule.runtime)
                //https://github.com/Tlaster/PreCompose
                api(libs.precompose)
                api(libs.precompose.koin)
                api(libs.precompose.molecule)

                implementation("io.github.vinceglb:filekit-coil:0.10.0-beta01")
                implementation("io.github.vinceglb:filekit-core:0.10.0-beta01")
                implementation("io.github.vinceglb:filekit-dialogs:0.10.0-beta01")
                implementation("io.github.vinceglb:filekit-dialogs-compose:0.10.0-beta01")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = "io.github.octestx"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "basic-multiplatform-ui-lib", version.toString())

    pom {
        name = "BasicMultiplatformUILib"
        description = "提供UI扩展"
        inceptionYear = "2025"
        url = "https://github.com/OCTestX/BasicMultiplatformUILib"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "OCTestX"
                name = "OCTestX"
                url = "https://github.com/OCTestX"
            }
        }
        scm {
            url = "https://github.com/OCTestX/BasicMultiplatformUILib"
            connection = "scm:git:git://github.com/OCTestX/BasicMultiplatformUILib.git"
            developerConnection = "scm:git:ssh://github.com/OCTestX/BasicMultiplatformUILib.git"
        }
    }
}
