package com.vitalidudarenka.exchangerates.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseViewModel:ViewModel() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var isActive = true

    private val parentJob = SupervisorJob()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    val scope = CoroutineScope(coroutineContext)
    val handlerLiveData = MutableLiveData<Throwable>()

    fun <P> doWork(doOnAsyncBlock: suspend CoroutineScope.() -> P) {
        doCoroutineWork(doOnAsyncBlock, viewModelScope, Dispatchers.IO)
    }

    fun <P> doWorkInMainThread(doOnAsyncBlock: suspend CoroutineScope.() -> P) {
        doCoroutineWork(doOnAsyncBlock, viewModelScope, Dispatchers.Main)
    }

    fun <P> doRepeatWork(delay: Long, doOnAsyncBlock: suspend CoroutineScope.() -> P) {
        isActive = true
        viewModelScope.launch() {
            while (this@BaseViewModel.isActive) {
                withContext(Dispatchers.IO) {
                    doOnAsyncBlock.invoke(this)
                }
                if (this@BaseViewModel.isActive) {
                    delay(delay)
                }
            }
        }
    }

    fun stopRepeatWork() {
        this.isActive = false
    }

    fun startRepeatWork() {
        this.isActive = true
    }

    override fun onCleared() {
        super.onCleared()
        isActive = false
        viewModelJob.cancel()
    }

    private inline fun <P> doCoroutineWork(
        crossinline doOnAsyncBlock: suspend CoroutineScope.() -> P,
        coroutineScope: CoroutineScope,
        context: CoroutineContext
    ) {
        coroutineScope.launch {
            withContext(context) {
                doOnAsyncBlock.invoke(this)
            }
        }
    }

}