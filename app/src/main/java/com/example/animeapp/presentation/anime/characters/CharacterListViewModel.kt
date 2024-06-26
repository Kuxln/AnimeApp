package com.example.animeapp.presentation.anime.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.domain.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {
    private val _livedata = MutableLiveData<CharacterListViewState>()
    val livedata: LiveData<CharacterListViewState> get() = _livedata
    private val state = CharacterListViewState()

    fun setId(id: String) {
        state.animeId = id
        _livedata.postValue(state)
        getCharactersIds()
    }

    fun onLoad() {
        getCharactersList()
    }

    private fun getCharactersIds() {
        state.isLoading = true
        _livedata.postValue(state)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.animeId?.let {
                    state.charactersIds = repository.getCharactersIds(it)
                    _livedata.postValue(state)
                    getCharactersList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun getCharactersList() {
        viewModelScope.launch(Dispatchers.IO) {
            if (state.pageList == null) {
                try {
                    state.pageList = state.charactersIds?.let {
                        listOf(CharListPage(repository.getCharacters(it.subList(0, min(10,it.size)))))
                    }
                    isHasMore()
                    state.isLoading = false
                    _livedata.postValue(state)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                loadMoreCharacters()
            }
        }
    }

    private fun loadMoreCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.pageList?.let {
                    val startRange = it.size * 10
                    val endRange = min(state.charactersIds?.size ?: startRange, startRange + 10)
                    state.charactersIds?.let { ids ->
                        state.pageList = listOf(it, listOf(CharListPage(
                            repository.getCharacters(
                                ids.subList(startRange, endRange
                                )
                            ))
                        )).flatten()
                    }
                }
                isHasMore()
                _livedata.postValue(state)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun isHasMore() {
        state.hasMore = state.pageList!!.size.times(10) < state.charactersIds!!.size
    }

}