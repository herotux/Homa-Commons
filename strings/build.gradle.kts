plugins {
    alias(libs.plugins.library)
    `maven-publish`
}

group = "com.github.herotux.Homa-Commons"
version = findProperty("VERSION")?.toString() ?: System.getenv("VERSION") ?: "8.3.0"

android {
    namespace = "com.goodwy.strings"
    compileSdk = libs.versions.app.build.compileSDKVersion.get().toInt()

    publishing {
        singleVariant("release") {}
    }
}

publishing.publications {
    create<MavenPublication>("release") {
        afterEvaluate {
            from(components["release"])
        }
    }
}
