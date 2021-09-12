package me.erfandp.buttondp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.erfandp.buttondp.data.repositories.UserRepository

class SignUpViewModel: ViewModel() {
	val signupResponseLiveData = MutableLiveData<Boolean>()
	
	fun signUpUser(fullName:String,username:String,password:String){
		viewModelScope.launch(Dispatchers.IO) {
			signupResponseLiveData.postValue(UserRepository.signUpUser(fullName, username, password))
		}
	}

}