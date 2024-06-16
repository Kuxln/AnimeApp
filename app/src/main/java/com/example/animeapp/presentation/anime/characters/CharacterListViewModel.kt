package com.example.animeapp.presentation.anime.characters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.anime.AnimeApiDataSource
import com.example.animeapp.data.anime.CharacterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val api: AnimeApiDataSource
) : ViewModel() {
    private val _livedata = MutableLiveData<CharacterListViewState>()
    val livedata: LiveData<CharacterListViewState> get() = _livedata
    private val state = CharacterListViewState()

    fun setId(id: String) {
        state.id = id
        _livedata.postValue(state)
        getCharactersIdList()
    }

    private fun getCharactersIdList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.id?.let {
                    val response = api.getCharactersList(it)
                    state.charactersIdList = response
                    getCharactersList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getCharactersList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.charactersIdList?.data?.let {
                    for (data: CharacterItem in it) {
                        val response = api.getCharacter(data.id)
                        response?.let {item ->
                            state.charactersList.add(item)
                        }
                    }
                    _livedata.postValue(state)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}