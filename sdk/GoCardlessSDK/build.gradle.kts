plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
}

android {
    namespace = "com.gocardless.gocardlesssdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

afterEvaluate {
    configure<PublishingExtension> {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.gocardless"
                artifactId = "gocardlesssdk"
                version = "1.0.0"

                afterEvaluate {
                    from(components["release"])
                }

                pom {
                    name.set("GoCardless Android Client")
                    packaging = "aar"
                    description.set("Client library for accessing the GoCardless API")
                    url.set("http://developer.gocardless.com/")

                    scm {
                        url.set("scm:git@github.com:gocardless/gocardless-pro-android-sdk.git")
                        connection.set("scm:git@github.com:gocardless/gocardless-pro-android-sdk.git")
                        developerConnection.set("scm:git@github.com:gocardless/gocardless-pro-android-sdk.git")
                    }

                    licenses {
                        license {
                            name.set("MIT")
                            url.set("http://www.opensource.org/licenses/mit-license.php")
                            distribution.set("repo")
                        }
                    }

                    developers {
                        developer {
                            name.set("GoCardless Ltd")
                            email.set("client-libraries@gocardless.com")
                        }
                    }
                }
            }
        }

        repositories {
            maven {
                name = "OSSRH"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

                credentials {
                    username = System.getenv("MAVEN_USERNAME")
                    password = System.getenv("MAVEN_PASSWORD")
                }
            }
        }

        signing {
            sign(publishing.publications)
        }
    }
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // Unit Tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

    // UI Tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}