package com.kevin.hanakotoba.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [FlowerDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
class PlantRepository @Inject constructor(private val flowerDao: FlowerDao) {

    fun getPlants() = flowerDao.getFlowers()

    fun getPlant(plantId: String) = flowerDao.getFlower(plantId)

}
