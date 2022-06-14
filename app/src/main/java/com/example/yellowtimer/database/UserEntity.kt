package com.example.yellowtimer.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "userTable")
class UserEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var email: String
    var pass: String


    @Ignore
    constructor(email: String,pass:String) {
        this.email = email
        this.pass = pass
    }

    constructor(
        id: Int,
        email: String,
        pass: String
    ) {
        this.id = id
        this.email = email
        this.pass = pass
    }
}