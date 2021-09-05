package com.antonov.tinkoff.fintex.ui.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonov.tinkoff.fintex.data.model.categories.CategoriesGifResponse
import com.antonov.tinkoff.fintex.data.repository.categories.CategoriesGIfRepository
import com.antonov.tinkoff.fintex.ui.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CategoriesGifViewModel(
    private val categoriesGIfRepository: CategoriesGIfRepository,
    private val categoriesName: String,
    private val page: Int,
) : ViewModel() {

    private val _categoriesGifLiveData = MutableLiveData<ViewState<CategoriesGifResponse>>()
    val categoriesGifLiveData: LiveData<ViewState<CategoriesGifResponse>>
        get() = _categoriesGifLiveData

    init {
        getCategoriesGif(categoriesName, page)
    }

    fun getCategoriesGif(categoriesName: String, page: Int){
        if (_categoriesGifLiveData.value is ViewState.Loading) return
        viewModelScope.launch(Dispatchers.IO) {
            _categoriesGifLiveData.postValue(ViewState.Loading())
            try {
                _categoriesGifLiveData.postValue(ViewState.Success(data = categoriesGIfRepository.getCategoriesGif(categoriesName, page)))
            } catch (e: Exception){
                _categoriesGifLiveData.postValue(ViewState.Error(exception = e))
            }
        }
    }
}