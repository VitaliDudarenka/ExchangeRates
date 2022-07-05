package com.vitalidudarenka.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vitalidudarenka.data.db.entities.RateDB
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {

    @Insert
    suspend fun insert(symbolDB: RateDB)

    @Delete
    suspend fun delete(symbolDB: RateDB)

    @Query("SELECT * FROM rate ORDER BY code")
    fun getAll(): Flow<List<RateDB>>

}