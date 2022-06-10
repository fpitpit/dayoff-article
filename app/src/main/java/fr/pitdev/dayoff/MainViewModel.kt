package fr.pitdev.dayoff

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val handled: SavedStateHandle) : ViewModel() {

    fun getHello(name: String) = "Hello $name"

}