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
import kotlinx.coroutines.async
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
        state.animeId = id
        _livedata.postValue(state)
        getCharactersIdList()
    }

    private fun getCharactersIdList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.animeId?.let {animeId ->
                    state.charactersIds = api.getCharactersList(animeId)?.data?.map { it.id }
//                    getCharactersList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

//    private fun getCharactersList() {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val task = async {
//
//                }
//                state.charactersIdList?.data?.let {
//
//                    for (data: CharacterItem in it) {
//                        val response = api.getCharacter(data.id)
//                        response?.let { item ->
//                            state.charactersList.add(item)
//                        }
//                    }
//                    _livedata.postValue(state)
//                }
//
//                task.
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

}