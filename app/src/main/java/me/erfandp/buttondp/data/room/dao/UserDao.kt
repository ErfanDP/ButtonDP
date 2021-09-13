package me.erfandp.buttondp.data.room.dao

import androidx.room.*
import me.erfandp.buttondp.data.model.User

@Dao
interface UserDao {
	
	
	@Query("SELECT * FROM userTable WHERE id LIKE :id LIMIT 1")
	suspend fun findById(id: String?): User?
	
	@Query("SELECT * FROM userTable WHERE username LIKE :username LIMIT 1")
	suspend fun findByUserName(username:String?):User?
	
	@Query("SELECT * FROM userTable WHERE username LIKE :username AND password LIKE :password LIMIT 1")
	suspend fun loginUser(username: String?,password:String?):User?
	
	@Query("SELECT COUNT(username) FROM userTable ")
	suspend fun countOfUsers():Int
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertUser(user: User)
	
	@Query("DELETE FROM userTable")
	suspend fun deleteAllUsers()
	
	@Delete
	suspend fun delete(user: User)
	
	
}