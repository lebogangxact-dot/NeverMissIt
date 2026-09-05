plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }

android {
    namespace = "com.nevermissit.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.nevermissit.app"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        create("release") {
            val storeFilePath = System.getenv("NMI_KEYSTORE_FILE")
            val storePasswordValue = System.getenv("NMI_KEYSTORE_PASSWORD")
            val keyAliasValue = System.getenv("NMI_KEY_ALIAS")
            val keyPasswordValue = System.getenv("NMI_KEY_PASSWORD")
            if (!storeFilePath.isNullOrBlank() && !storePasswordValue.isNullOrBlank() &&
                !keyAliasValue.isNullOrBlank() && !keyPasswordValue.isNullOrBlank()) {
                storeFile = file(storeFilePath)
                storePassword = storePasswordValue
                keyAlias = keyAliasValue
                keyPassword = keyPasswordValue
            }
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
