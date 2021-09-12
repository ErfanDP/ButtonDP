package me.erfandp.buttondp.data.repositories

import android.content.Context
import android.util.Log
import androidx.room.Room
import me.erfandp.buttondp.data.consts.LogTags
import me.erfandp.buttondp.data.model.User
import me.erfandp.buttondp.data.room.dao.UserDataBase
import java.util.*
import kotlin.collections.ArrayList

object UserRepository {
	private lateinit var userDataBase:UserDataBase
	fun initializeRepository(context: Context){
		userDataBase = Room.databaseBuilder(context.applicationContext,
											UserDataBase::class.java,
											"userDataBase").build()
	}
	
	suspend fun getUserById(uuid: UUID?): User? {
		return userDataBase.userDao().findById(uuid.toString())
	}
	
	suspend fun getUserByUsername(userName:String):User?{
		return userDataBase.userDao().findByUserName(userName)
	}
	
	suspend fun checkLoginInfo(userName: String, password: String): User? {
		return userDataBase.userDao().loginUser(userName,password)
	}
	
	suspend fun signUpUser(fullName:String,username:String,password:String):Boolean{
		return if(getUserByUsername(username) == null){
			val user =User(fullName,username,password)
			userDataBase.userDao().insertUser(user)
			Log.d(LogTags.USER_REP,"user signed up $user")
			true
		}else{
			Log.e(LogTags.USER_REP,"user signed up failed username already exist")
			false
		}
	}
	
	suspend fun isFirstTime():Boolean{
		return userDataBase.userDao().countOfUsers() == 0
	}
	
}