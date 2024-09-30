package com.example.ciudades.data

import androidx.room.*

@Dao
interface CapitalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(capital: Capital)

    @Update
    suspend fun updateCity(capital: Capital)

    @Delete
    suspend fun deleteCity(capital: Capital)

    @Query("SELECT * FROM city_table WHERE capitalName = :name")
    suspend fun getCityByName(name: String): Capital?

    @Query("DELETE FROM city_table WHERE country = :country")
    suspend fun deleteCitiesByCountry(country: String)

    @Query("SELECT * FROM city_table")
    suspend fun getAllCities(): List<Capital>

    @Query("DELETE FROM city_table")
    suspend fun clearAllCities()
}
