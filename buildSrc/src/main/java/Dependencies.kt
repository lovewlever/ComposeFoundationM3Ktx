import org.gradle.api.JavaVersion
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ProductFlavors {
    fun versionNameSuffixTime(): String = SimpleDateFormat("MMddHHmm", Locale.CANADA).format(Date())

    fun packageTime(): Long = System.currentTimeMillis()
}

@Deprecated("using TOML")
object BuildVersion {
    const val CompileSdk = 34
    const val MinSdk = 24
    const val TargetSdk = 34
    const val VersionCode = 1
    const val VersionName = "1.0.0"

    val SourceTargetCompatibility = JavaVersion.VERSION_17
    const val JvmTarget = "17"
    const val KotlinCompilerExtensionVersion = "1.5.1"
    const val KotlinCompilerVersion = "1.9.0"
}
@Deprecated("using TOML")
object Libs {

    private const val accompanistVersion = "0.32.0"

    // Google accompanist
    @Deprecated("如果您使用 SystemUIController 在 Activity 中进行边到边切换并更改系统栏颜色和系统栏图标颜色，请使用 androidx.activity 1.8.0-alpha03 及更高版本中提供的新 Activity.enableEdgeToEdge 方法")
    const val AccompanistSystemUIController = "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion"
    @Deprecated("在 Compose Material 1.3.0 中，伴奏者 SwipeRefresh 已被 PullRefresh 取代。实现是类似的，但它不是可组合函数，而是可以应用于可组合函数的修饰符")
    const val AccompanistSwipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$accompanistVersion"
    const val AccompanistPager = "com.google.accompanist:accompanist-pager:$accompanistVersion"
    const val AccompanistPagerIndicators = "com.google.accompanist:accompanist-pager-indicators:$accompanistVersion"
    const val AccompanistPermissions = "com.google.accompanist:accompanist-permissions:$accompanistVersion"
    const val AccompanistPlaceholder = "com.google.accompanist:accompanist-placeholder:$accompanistVersion"
    const val AccompanistWebView = "com.google.accompanist:accompanist-webview:$accompanistVersion"


    // Others
    const val ProcessPhoenix = "com.jakewharton:process-phoenix:2.1.2"
    const val BlankJUtilCodex = "com.blankj:utilcodex:1.31.1"

}