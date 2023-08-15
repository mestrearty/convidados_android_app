package com.example.convidados.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PresentViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Fragmento de Galeria zé"
    }
    val text: LiveData<String> = _text
}