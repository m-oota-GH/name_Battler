package com.example.namebattler.database.characterDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CHARACTERS")
class Characters (
    @PrimaryKey
    @ColumnInfo(name = "name")
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
    val CREATE_AT: Long
){
    constructor(): this(
        "データがありません",
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0)
}