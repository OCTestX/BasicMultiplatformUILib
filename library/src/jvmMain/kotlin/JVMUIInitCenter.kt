package io.github.kotlin.fibonacci

object JVMUIInitCenter {
    private var initialized = false
    fun init() {
        CommonUIInitCenter.init {

        }
    }
}