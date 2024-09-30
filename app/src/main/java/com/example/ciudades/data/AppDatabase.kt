package com.example.ciudades.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Capital::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun capitalDao(): CapitalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "city_db"
                )
                    .fallbackToDestructiveMigration() // Permite reconstrucción en caso de fallo de migración
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
