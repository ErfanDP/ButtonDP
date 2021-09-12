package me.erfandp.buttondp.data.room.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import me.erfandp.buttondp.data.model.UUIDConverter
import me.erfandp.buttondp.data.model.User

@Database(entities = [User::class], version = 1)
@TypeConverters(UUIDConverter::class)
abstract class UserDataBase : RoomDatabase() {
	abstract fun userDao(): UserDao
}