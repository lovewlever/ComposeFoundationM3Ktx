package com.gq.playertest.m3color

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

@Composable
fun M3LightDarkColorPreviewScreen() {
    val context = LocalContext.current

    val lightColors =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) dynamicLightColorScheme(context) else lightColorScheme()
    val darkColors =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) dynamicDarkColorScheme(context) else darkColorScheme()

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Row {
                Text("DynamicLightColor", modifier = Modifier.weight(1f))
                Text("DynamicDarkColor", modifier = Modifier.weight(1f))
            }
            val lightColor = extractColorPairs3(lightColors)
            val darkColor = extractColorPairs3(darkColors)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(lightColor.size) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        val lc = lightColor[index]
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(color = lightColors.background)
                                .padding(vertical = 4.dp, horizontal = 4.dp)
                                .background(lc.second, shape = RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = lc.first,
                                modifier = Modifier.padding(start = 12.dp),
                                color = invertColor(lc.second),
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 12.sp
                            )
                        }

                        val dc = darkColor[index]
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(color = darkColors.background)
                                .padding(vertical = 4.dp, horizontal = 4.dp)
                                .background(dc.second, shape = RoundedCornerShape(6.dp))
                                ,
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = dc.first,
                                modifier = Modifier.padding(start = 12.dp),
                                color = invertColor(dc.second),
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }

}

private fun invertColor(color: Color): Color {
    return Color(
        red = 1f - color.red,
        green = 1f - color.green,
        blue = 1f - color.blue,
        alpha = 0.4F
    )
}

private fun extractColorPairs(scheme: ColorScheme): List<Pair<String, Color>> {
    return listOf(
        "primary" to scheme.primary,
        "onPrimary" to scheme.onPrimary,
        "primaryContainer" to scheme.primaryContainer,
        "onPrimaryContainer" to scheme.onPrimaryContainer,

        "secondary" to scheme.secondary,
        "onSecondary" to scheme.onSecondary,
        "secondaryContainer" to scheme.secondaryContainer,
        "onSecondaryContainer" to scheme.onSecondaryContainer,

        "tertiary" to scheme.tertiary,
        "onTertiary" to scheme.onTertiary,
        "tertiaryContainer" to scheme.tertiaryContainer,
        "onTertiaryContainer" to scheme.onTertiaryContainer,

        "surface" to scheme.surface,
        "surfaceTint" to scheme.surfaceTint,
        "surfaceBright" to scheme.surfaceBright,
        "surfaceDim" to scheme.surfaceDim,
        "surfaceContainer" to scheme.surfaceContainer,
        "surfaceContainerLow" to scheme.surfaceContainerLow,
        "surfaceContainerLowest" to scheme.surfaceContainerLowest,
        "surfaceContainerHigh" to scheme.surfaceContainerHigh,
        "surfaceContainerHighest" to scheme.surfaceContainerHighest,
        "onSurface" to scheme.onSurface,
        "surfaceVariant" to scheme.surfaceVariant,
        "onSurfaceVariant" to scheme.onSurfaceVariant,

        "background" to scheme.background,
        "onBackground" to scheme.onBackground,

        "error" to scheme.error,
        "onError" to scheme.onError,
        "errorContainer" to scheme.errorContainer,
        "onErrorContainer" to scheme.onErrorContainer,

        "outline" to scheme.outline,
        "outlineVariant" to scheme.outlineVariant,
        "inverseSurface" to scheme.inverseSurface,
        "inverseOnSurface" to scheme.inverseOnSurface,
        "inversePrimary" to scheme.inversePrimary
    )

}

private fun extractColorPairs2(scheme: ColorScheme): List<Pair<String, Color>> {
    val pairList = mutableListOf<Pair<String, Color>>()
    val members = scheme::class.declaredMemberProperties.filterIsInstance<KProperty1<ColorScheme, *>>()
    members.forEach { field ->
        field.isAccessible = true
        val name = field.name
        val raw = field.get(scheme)
        if (raw is Color) {
            pairList.add(name to raw)
        }
    }
    return pairList
}

private fun extractColorPairs3(scheme: ColorScheme): List<Pair<String, Color>> {
    val result = mutableListOf<Pair<String, Color>>()
    // 提取括号内内容
    val content = scheme.toString().substringAfter("ColorScheme(").substringBeforeLast(")")
    // 按字段分割
    val regex = Regex("(?<=\\))(?=[a-zA-Z])")
    val parts = regex.split(content)

    for (part in parts) {
        val key = part.substringBefore('=').trim()
        val colorStr = part.substringAfter('=').trim()
        val color = parseColorString(colorStr)
        result.add(key to color)
    }
    return result
}

private fun parseColorString(colorStr: String): Color {
    val values = Regex("""Color\(([\d.,\s]+),\s*sRGB""")
        .find(colorStr)
        ?.groupValues
        ?.get(1)
        ?.split(",")
        ?.map { it.trim().toFloat() }

    return if (values != null && values.size == 4) {
        Color(values[0], values[1], values[2], values[3])
    } else {
        Color.Transparent
    }
}


