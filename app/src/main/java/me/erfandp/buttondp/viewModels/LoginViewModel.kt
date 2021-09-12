package me.erfandp.buttondp.viewModels

import androidx.lifecycle.ViewModel
import me.erfandp.buttondp.data.model.User
import me.erfandp.buttondp.data.repositories.UserRepository

class LoginViewModel: ViewModel() {
	
	fun checkLoginInfo(username:String,password:String): User?=
		UserRepository.checkLoginInfo(username,password)
	
	fun isFirstTime():Boolean{
		return UserRepository.isFirstTime()
	}
	
}