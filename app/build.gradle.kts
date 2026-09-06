plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }

android {
    namespace = "com.studioverse.nevermissit"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.studioverse.nevermissit"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    signingConfigs {
        create("release") {
            val storeFilePath = System.getenv("CM_KEYSTORE_PATH")
            val storePasswordValue = System.getenv("CM_KEYSTORE_PASSWORD")
            val keyAliasValue = System.getenv("CM_KEY_ALIAS")
            val keyPasswordValue = System.getenv("CM_KEY_PASSWORD")

            check(!storeFilePath.isNullOrBlank()) { "CM_KEYSTORE_PATH is missing; release signing cannot continue." }
            check(!storePasswordValue.isNullOrBlank()) { "CM_KEYSTORE_PASSWORD is missing; release signing cannot continue." }
            check(!keyAliasValue.isNullOrBlank()) { "CM_KEY_ALIAS is missing; release signing cannot continue." }
            check(!keyPasswordValue.isNullOrBlank()) { "CM_KEY_PASSWORD is missing; release signing cannot continue." }

            storeFile = file(storeFilePath)
            storePassword = storePasswordValue
            keyAlias = keyAliasValue
            keyPassword = keyPasswordValue
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
}
