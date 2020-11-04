package com.vieja.appet.ui.care

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CareViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is care Fragment"
    }

    val text: LiveData<String> = _text
}