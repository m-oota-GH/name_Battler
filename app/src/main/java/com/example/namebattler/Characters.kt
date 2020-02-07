package com.example.namebattler

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "CHARACTERS")
data  class Characters constructor(
    @PrimaryKey(autoGenerate = true)
    val NAME : String,

    @ColumnInfo(name = "JOB")
    val JOB: Int,

    @ColumnInfo(name = "HP")
    val HP: Int,

    @ColumnInfo(name = "MP")
    val MP: Int,

    @ColumnInfo(name = "STR")
    val STR: Int,

    @ColumnInfo(name = "DEF")
    val DEF: Int,

    @ColumnInfo(name = "AGI")
    val AGI: Int,

    @ColumnInfo(name = "LUCK")
    val LUCK: Int,

    @ColumnInfo(name = "CREATE_AT")
    val CREATE_AT: Date?
)