package com.vitalidudarenka.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rate")
data class RateDB(@PrimaryKey val code: String) {
}