package com.eotw95.wantnote.screen.wantList

import androidx.lifecycle.ViewModel
import com.eotw95.wantnote.WantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WantListViewModel @Inject constructor(private val repository: WantRepository): ViewModel() {
    val items = repository.getAll()
}