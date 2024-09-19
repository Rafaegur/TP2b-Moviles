package com.example.ciudades.data

import androidx.room.*

@Dao
interface CapitalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapital(capital: Capital)

    @Update
    suspend fun updateCapital(capital: Capital)

    @Delete
    suspend fun deleteCapital(capital: Capital)

    @Query("SELECT * FROM capital_table WHERE name = :name")
    suspend fun getCapitalByName(name: String): Capital?

    @Query("DELETE FROM capital_table WHERE country = :country")
    suspend fun deleteCapitalsByCountry(country: String)

    @Query("SELECT * FROM capital_table")
    suspend fun getAllCapitals(): List<Capital>

    @Query("DELETE FROM capital_table")
    suspend fun deleteAllCapitals()
}
