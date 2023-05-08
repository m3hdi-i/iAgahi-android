package ir.m3hdi.agahinet.ui.viewmodel

import android.app.Application
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.m3hdi.agahinet.data.model.Ad
import ir.m3hdi.agahinet.data.model.AdFilters
import ir.m3hdi.agahinet.data.repository.AdRepository
import ir.m3hdi.agahinet.util.AppUtils
import ir.m3hdi.agahinet.util.Constants.Companion.PAGE_SIZE
import ir.m3hdi.agahinet.util.Resultx
import ir.m3hdi.agahinet.util.onFailure
import ir.m3hdi.agahinet.util.onSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val adRepository: AdRepository,application: Application) : AndroidViewModel(application)  {

    val adItems= mutableListOf<Ad>()

    private val _nextPage = MutableStateFlow<Resultx<List<Ad>>>(Resultx.success(listOf()))
    val nextPage= _nextPage.asStateFlow()

    private val _filters = MutableStateFlow(AdFilters())
    val filters= _filters.asStateFlow()

    var isLastPage = false

    fun doNewSearch(newFilters: AdFilters){
        _filters.value = newFilters
        fetchNextPage()
    }

    private var c=1
    fun fetchNextPage()
    {
        _nextPage.value= Resultx.loading()


        /*viewModelScope.launch {

            delay(1000)

            val page= mutableListOf<Ad>()
            for (i in 0..9){
                val ad = Ad(
                    adId = c,
                    title = "Default Title $c",
                    description = "Default Description",
                    price = "Not Available",
                    createdAt = "2022-01-01",
                    category = 0,
                    creator = 0,
                    city = 0,
                    mainImageId = null
                )
                page.add(ad)
                c++
            }

            adItems+=page

            if (AppUtils.hasInternetConnection(getApplication<Application>().applicationContext)){
                _nextPage.value=Resultx.success(page)
            }else{
                _nextPage.value=Resultx.failure(Exception("No Network"))
            }

            if (c>=110){
                isLastPage=true
            }

            currentPage++


        }*/

        viewModelScope.launch {
            delay(800)
            _filters.value.offset = adItems.size

            adRepository.searchAds(_filters.value).onSuccess {
                if (it.size < PAGE_SIZE){
                    isLastPage=true
                }
                adItems+=it
                _nextPage.value= Resultx.success(it)
            }.onFailure {
                _nextPage.value= Resultx.failure(it)
            }
        }

    }

}