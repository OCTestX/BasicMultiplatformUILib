package io.github.octestx.basic.multiplatform.ui.ui.utils

import io.github.octestx.library.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import java.io.File

private val resCache: MutableMap<String, File> = mutableMapOf()

@OptIn(ExperimentalResourceApi::class)
suspend fun getAbsoluteFromResPath(resourcePath: String, offset: String = resourcePath.split(".").last()): String {
    val cacheFile = resCache.getOrPut(resourcePath) {
        val bytes = Res.readBytes(resourcePath)
        File.createTempFile(System.nanoTime().toString(), offset).apply {
            writeBytes(bytes)
        }
    }
    return cacheFile.absolutePath
}