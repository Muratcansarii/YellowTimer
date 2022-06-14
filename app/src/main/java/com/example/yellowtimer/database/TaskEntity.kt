package com.example.yellowtimer.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "taskTable")
class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var name: String


    @Ignore
    constructor(name: String) {
        this.name = name
    }

    constructor(
        id: Int,
        name: String,
    ) {
        this.id = id
        this.name = name
    }
}

