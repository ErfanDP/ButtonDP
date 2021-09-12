package me.erfandp.buttondp.viewModels

import androidx.lifecycle.ViewModel
import me.erfandp.buttondp.data.model.User
import me.erfandp.buttondp.data.repositories.UserRepository

class LoginViewModel: ViewModel() {
	
	suspend fun checkLoginInfo(username:String,password:String): User?=
		UserRepository.checkLoginInfo(username,password)
	
	suspend fun isFirstTime():Boolean{
		return UserRepository.isFirstTime()
	}
	
}