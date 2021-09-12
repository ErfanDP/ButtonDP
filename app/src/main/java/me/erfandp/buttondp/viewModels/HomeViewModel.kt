package me.erfandp.buttondp.viewModels

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.erfandp.buttondp.data.consts.LogTags
import me.erfandp.buttondp.data.model.User
import me.erfandp.buttondp.data.repositories.UserRepository
import java.util.*

class HomeViewModel: ViewModel() {
	var activeUser: User? = null
	
	val logoutCountDownLiveData = MutableLiveData<Boolean>()
	
	private val backgroundCountDown = object: CountDownTimer(10000, 1000) {
		override fun onTick(millisUntilFinished: Long) {
			Log.d(LogTags.HOME_COUNTER, "ontick background $millisUntilFinished")
			
		}
		
		override fun onFinish() {
			Log.d(LogTags.HOME_COUNTER, "onfinish background")
			logOut()
		}
		
	}
	private val foregroundCountDown = object: CountDownTimer(30000, 1000) {
		override fun onTick(millisUntilFinished: Long) {
			Log.d(LogTags.HOME_COUNTER, "ontick foreground $millisUntilFinished")
		}
		
		override fun onFinish() {
			Log.d(LogTags.HOME_COUNTER, "onfinish foreground")
			logOut()
		}
	}
	
	private fun logOut() {
		logoutCountDownLiveData.postValue(true)
	}
	
	fun getUserFullName(): String? {
		return activeUser?.fullName
	}
	
	fun startBackgroundCountDown() {
		backgroundCountDown.start()
	}
	
	fun stopBackgroundCountDown() {
		backgroundCountDown.cancel()
	}
	
	
	fun startForegroundCountDown() {
		foregroundCountDown.start()
	}
	
	fun stopForegroundCountDown() {
		foregroundCountDown.cancel()
	}
	
	fun initActiveUser(id:String?){
		viewModelScope.launch(Dispatchers.IO) {
			activeUser = UserRepository.getUserById(UUID.fromString(id))
		}
	}
}