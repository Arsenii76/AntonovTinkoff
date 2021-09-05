package com.antonov.tinkoff.fintex.ui.features.random.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonov.tinkoff.fintex.data.model.random.GifResponse
import com.antonov.tinkoff.fintex.data.repository.random.RandomGifRepository
import com.antonov.tinkoff.fintex.ui.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.collections.ArrayList

class RandomGifViewModel(private val randomGifRepository: RandomGifRepository) : ViewModel() {

    private val _gifLiveData = MutableLiveData<ViewState<GifResponse>>()
    val gifLiveData : LiveData<ViewState<GifResponse>>
        get() = _gifLiveData

    private val _backAllowedLiveData = MutableLiveData<Boolean>()
    val backAllowedLiveData : LiveData<Boolean>
        get() = _backAllowedLiveData

    private val gifs = ArrayList<Gif>()
    private var currentUrlPosition = 0

    init {
        getRandomGif()
    }

    fun nextGif() {
        if (currentUrlPosition + 1 < gifs.size) {
            _gifLiveData.value = ViewState.Success(
                GifResponse(
                    description = gifs[currentUrlPosition + 1].description,
                    url = gifs[currentUrlPosition + 1].url
                )
            )
            currentUrlPosition++
            _backAllowedLiveData.value = currentUrlPosition!=0
        } else getRandomGif()

    }

    fun previousGif() {
        if (currentUrlPosition != 0) {
            _gifLiveData.value = ViewState.Success(
                GifResponse(
                    description = gifs[currentUrlPosition - 1].description,
                    url = gifs[currentUrlPosition - 1].url
                )
            )
            currentUrlPosition--
            _backAllowedLiveData.value = currentUrlPosition!=0
        }
    }

    fun getRandomGif() {
        if (_gifLiveData.value is ViewState.Loading) return
        viewModelScope.launch(Dispatchers.IO) {
            _gifLiveData.postValue(ViewState.Loading())
            try {
                val data = randomGifRepository.getRandomGif()
                _gifLiveData.postValue(ViewState.Success(data = data))

                gifs.add(
                    Gif(
                        url = data.url,
                        description = data.description
                    )
                )
                currentUrlPosition = gifs.size - 1
                _backAllowedLiveData.postValue(currentUrlPosition!=0)

            } catch (e: Exception) {
                _gifLiveData.postValue(ViewState.Error(exception = e))
                _backAllowedLiveData.postValue(currentUrlPosition!=0)
            }
        }
    }

}