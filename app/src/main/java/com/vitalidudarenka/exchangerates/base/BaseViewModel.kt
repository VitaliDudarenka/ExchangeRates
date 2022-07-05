package com.vitalidudarenka.exchangerates.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    private var viewModelJob = Job()

    private val parentJob = SupervisorJob()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    val scope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}