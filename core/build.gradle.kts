import com.badlogic.gdx.tools.texturepacker.TexturePacker

@Suppress(
    // known false positive: https://youtrack.jetbrains.com/issue/KTIJ-19369
    "DSL_SCOPE_VIOLATION"
)
plugins {
    `java-library`
    idea
    alias(libs.plugins.spotless)
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

val assetsDir = rootProject.files("assets")

sourceSets.main.configure {
    resources.srcDir(assetsDir)
}

idea.module { excludeDirs.add(file("src/gwtCompat")) }

spotless {
    isEnforceCheck = false
    java {
        palantirJavaFormat()
    }
}

val packTextures = tasks.register("packTextures") {
    val inputDir = "$rootDir/assets/images/included"
    val outputDir = "$rootDir/assets/images/packed"
    inputs.dir(inputDir)
    outputs.dir(outputDir)
    doLast {
        delete(outputDir)
        TexturePacker.process(TexturePacker.Settings().apply {
            combineSubdirectories = true
        }, inputDir, outputDir, "images.pack")
    }
}

tasks.compileJava.configure {
    dependsOn(packTextures)
    options.encoding = "UTF-8"
}

dependencies {
    implementation(libs.harlequin.core)
    implementation(libs.harlequin.ashley)
    implementation(libs.perceptual)
    implementation(libs.gdx.core)
    implementation(libs.gdx.ai)
    implementation(libs.gdx.controllers.core)
    implementation(libs.ashley)
    implementation(libs.jdkgdxds)
    implementation(libs.simpleGraphs)
    implementation(libs.squidLib.core)
    implementation(libs.textratypist)
    implementation(libs.dagger.core)
    api(libs.dagger.gwt)
    annotationProcessor(libs.dagger.compiler)
}
