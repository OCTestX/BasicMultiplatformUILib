package io.github.octestx.basic.multiplatform.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

fun highlightText(text: String, highlightColor: Color, vararg highlights: String): AnnotatedString =
    highlightText(text, highlightColor, highlights.toList())

fun highlightText(text: String, highlightColor: Color, highlights: List<String>): AnnotatedString {
    return buildAnnotatedString {
        if (highlights.isEmpty()) {
            append(text)
            return@buildAnnotatedString
        }

        // 预处理：收集所有匹配区间（不区分大小写）
        val ranges = mutableListOf<IntRange>()
        val lowerText = text.lowercase()

        highlights.forEach { keyword ->
            val lowerKeyword = keyword.lowercase()
            var startIndex = 0

            while (startIndex < text.length) {
                val index = lowerText.indexOf(lowerKeyword, startIndex)
                if (index == -1) break

                ranges.add(index until (index + keyword.length))
                startIndex = index + 1
            }
        }

        // 合并重叠/相邻的区间
        val mergedRanges = ranges.sortedBy { it.first }.fold(mutableListOf<IntRange>()) { acc, range ->
            if (acc.isEmpty()) {
                acc.add(range)
            } else {
                val last = acc.last()
                if (range.first <= last.last) {
                    acc[acc.lastIndex] = last.first..maxOf(last.last, range.last)
                } else {
                    acc.add(range)
                }
            }
            acc
        }

        // 构建带高亮的文本
        var lastPos = 0
        mergedRanges.forEach { range ->
            // 添加非高亮部分
            if (range.first > lastPos) {
                append(text.substring(lastPos, range.first))
            }

            // 添加高亮部分
            withStyle(SpanStyle(background = highlightColor)) {
                append(text.substring(range))
            }

            lastPos = range.last
        }

        // 添加剩余部分
        if (lastPos < text.length) {
            append(text.substring(lastPos, text.length))
        }
    }
}