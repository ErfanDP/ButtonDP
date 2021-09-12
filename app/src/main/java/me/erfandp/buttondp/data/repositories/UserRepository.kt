package me.erfandp.buttondp.data.repositories

import android.util.Log
import me.erfandp.buttondp.data.consts.LogTags
import me.erfandp.buttondp.data.model.User
import java.util.*
import kotlin.collections.ArrayList

object UserRepository {
	private val userList: MutableList<User> = Collections.synchronizedList(ArrayList<User>())
	
	fun getUserById(uuid: UUID?): User? {
		return userList.find {
			it.id == uuid
		}
	}
	
	private fun getUserByUsername(userName:String):User?{
		return userList.find {
			it.userName == userName
		}
	}
	
	fun checkLoginInfo(userName: String, password: String): User? {
		return userList.find {
			it.userName == userName && it.password == password
		}
	}
	
	fun signUpUser(fullName:String,username:String,password:String):Boolean{
		return if(getUserByUsername(username) == null){
			val user =User(fullName,username,password)
			userList.add(user)
			Log.d(LogTags.USER_REP,"user signed up $user")
			true
		}else{
			Log.e(LogTags.USER_REP,"user signed up failed username already exist")
			false
		}
	}
	
	fun isFirstTime():Boolean{
		return userList.size == 0
	}
	
}