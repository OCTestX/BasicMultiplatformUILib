package io.github.octestx.basic.multiplatform.ui.ui.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import java.util.concurrent.atomic.AtomicBoolean

class SyncerSingle : SyncerSingleClient {
    private val available = AtomicBoolean(true)
    private val mutex = Mutex(true)
    suspend fun sync() {
        if (available.get()) {
            mutex.unlock()
            available.set(false)
        } else {
            throw Exception("SyncerSingle is dead")
        }
    }

    override suspend fun waitingSync() {
        mutex.lock()
        mutex.unlock()
    }
}

interface SyncerSingleClient {
    suspend fun waitingSync()
}

// Test
private fun main() {
    val syncer = SyncerSingle()
    val io = CoroutineScope(Dispatchers.IO)
    val mainScope = CoroutineScope(Dispatchers.Default)
    io.launch {
        println("IOStart")
        delay(1500)
        syncer.sync()
        println("IOEnd")
    }
    mainScope.launch {
        println("MainStart")
        syncer.waitingSync()
        println("MainEnd")
    }
    val f = readln()
}