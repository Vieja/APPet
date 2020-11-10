package com.vieja.appet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vieja.appet.models.Pet

class MainActivityViewModel: ViewModel() {

    private val _chosen_pet_id = MutableLiveData<Int>(1)


    fun choosePet(id : Int) {
        _chosen_pet_id.value = id
    }

    fun getChosenPet() : LiveData<Int>{
        return _chosen_pet_id
    }

}