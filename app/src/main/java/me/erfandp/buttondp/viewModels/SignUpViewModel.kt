package me.erfandp.buttondp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.erfandp.buttondp.data.repositories.UserRepository

class SignUpViewModel: ViewModel() {
	val signupResponseLiveData = MutableLiveData<Boolean>()
	
	fun signUpUser(fullName:String,username:String,password:String){
		signupResponseLiveData.value =  UserRepository.signUpUser(fullName, username, password)
	}

}