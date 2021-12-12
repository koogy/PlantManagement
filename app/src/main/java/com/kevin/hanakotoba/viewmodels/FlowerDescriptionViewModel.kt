package com.kevin.hanakotoba.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.data.FlowerRepository
import com.kevin.hanakotoba.data.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlowerDescriptionViewModel @Inject internal constructor(
    private val gardenRepository: GardenRepository,
    private val flowerRepository: FlowerRepository
) : ViewModel() {

        fun addFlowerInGarden(flowerId: Int) {
            viewModelScope.launch {
                gardenRepository.insertFlowerInGarden(flowerId)
            }
        }

        fun deleteFlowerInGarden(flowerId: Int) {
            viewModelScope.launch {
                gardenRepository.deleteFlowerInGarden(flowerId)
            }
        }
        fun updateFlowerInGarden(flower: Flower) {
            viewModelScope.launch {
                flowerRepository.updateFlower(flower)
            }
        }

        fun isPlanted(flowerId: Int): Flow<Boolean> {
            return gardenRepository.isPlanted(flowerId)
        }
}