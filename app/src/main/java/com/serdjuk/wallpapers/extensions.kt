package com.serdjuk.wallpapers

import java.util.Locale

fun String.firstUpper() = replaceFirstChar { c ->
    if (c.isLowerCase()) c.titlecase(
        Locale.getDefault()
    ) else c.toString()
}
