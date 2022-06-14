package com.example.yellowtimer.database

import androidx.room.*

@Dao
interface AppDao {

    @Query("SELECT * FROM taskTable ORDER BY ID")
    fun loadAllTasks(): List<TaskEntity?>

    @Query("SELECT * FROM userTable Where :email = email AND :pass = pass")
    fun loadUser(email:String,pass:String): List<UserEntity?>

    @Insert
    fun insertTask(taskEntity: TaskEntity?)

    @Insert
    fun insertUser(userEntity: UserEntity?)

    @Update
    fun updateTask(taskEntity: TaskEntity?)

    @Delete
    fun delete(taskEntity: TaskEntity?)

}