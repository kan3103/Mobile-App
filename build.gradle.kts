// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
}
buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.1")
        classpath("io.realm:realm-gradle-plugin:10.15.1")
    }
}


//task clean(type: Delete) {
//    delete rootProject.buildDir
//}