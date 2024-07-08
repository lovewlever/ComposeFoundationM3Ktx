import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ProductFlavors {
    fun versionNameSuffixTime(): String = SimpleDateFormat("MMddHHmm", Locale.US).format(Date())

    fun packageTime(): Long = System.currentTimeMillis()
}