package com.gq.basicm3.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gq.basicm3.common.GsonCommon
import java.util.regex.Pattern

/**
 * 匹配是否是Log
 */
inline fun String.matchIsLog(): Boolean {
    val index = this.lastIndexOf(".")
    if (index == -1) return false
    val ext = this.substring(index).replace(".", "")
    return Pattern.compile("(log)").matcher(ext).matches()
}

/**
 * 匹配手机号
 */
inline fun String.matchPhoneNumber(): Boolean =
    Pattern.compile("^(?:(?:\\+|00)86)?1[3-9]\\d{9}\$").matcher(this).matches()

/**
 * 匹配邮箱
 */
inline fun String.matchEmail(): Boolean =
    Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$").matcher(this).matches()


/**
 * 匹配url
 */
inline fun String.matchUrl(): Boolean =
    Pattern.compile("^(((ht|f)tps?):\\/\\/)?[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?\$").matcher(this).matches()

/**
 * 匹配是否是图片
 */
inline fun String.matchIsImage(): Boolean {
    val index = this.lastIndexOf(".")
    if (index == -1) return false
    val ext = this.substring(index).replace(".", "")
    return Pattern.compile("(gif|png|jpg|jpeg|webp|svg|psd|bmp|tif|jfif)").matcher(ext).matches()
}

/**
 * 匹配是否是音频文件
 */
inline fun String.matchIsAudio(): Boolean {
    val index = this.lastIndexOf(".")
    if (index == -1) return false
    val ext = this.substring(index).replace(".", "").lowercase()
    return Pattern.compile("(mp3|m4a|wav|amr|awb|wma|ogg|flac|pcm)").matcher(ext).matches()
}

/**
 * 隐藏手机号中间四位为
 */
inline fun String.phoneHideMiddleFour(separator: String = "****"): String {
    return if (this.matchPhoneNumber()) {
        this.replace(Regex("(\\d{3})(\\d{4})(\\d{4})"), "\$1${separator}\$3")
    } else {
        return this
    }
}
inline fun String.matchPassword(between: Pair<Int, Int> = 6 to 20) =
    Regex("^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{${between.first},${between.second}}\$").matches(this)


/**
 * 匹配Html里面的内容并返回内容
 */
inline fun String.matchBetweenHtmlLabelContent() =
    Regex("<[a-zA-Z]+.*?>([\\s\\S]*?)</[a-zA-Z]*?>").findAll(this, 0).map { mResult ->
        mResult.value.replace(regexHtmlLabel, "")
    }.toList()


/**
 * String 转Json实体
 */
inline fun <reified T> String.toEntityDataByGson(gson: Gson = GsonCommon.gson): T? {
    return try {
        gson.fromJson(this, object : TypeToken<T>(){}.type)
    } catch (e: Exception) {
        null
    }
}