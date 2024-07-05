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
    val SourceTargetCompatibility = JavaVersion.VERSION_17
}
@Deprecated("using TOML")
object Libs {

    private const val accompanistVersion = "0.32.0"


    const val AccompanistPagerIndicators = "com.google.accompanist:accompanist-pager-indicators:$accompanistVersion"
    const val AccompanistPermissions = "com.google.accompanist:accompanist-permissions:$accompanistVersion"


    // Others
    const val ProcessPhoenix = "com.jakewharton:process-phoenix:2.1.2"
    const val BlankJUtilCodex = "com.blankj:utilcodex:1.31.1"

}