package me.erfandp.buttondp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.erfandp.buttondp.data.repositories.UserRepository
import java.util.*

class MainViewModel : ViewModel(){

	val navigateLiveData = MutableLiveData<NavigationAction>()
	
	fun navigateToHome(from:NavigationDestinations, userId:UUID){
		navigateLiveData.postValue(NavigationAction(from,NavigationDestinations.Home(userId)))
	}
	
	fun navigateToLogin(from:NavigationDestinations){
		navigateLiveData.postValue(NavigationAction(from,NavigationDestinations.Login()))
	}
	
	fun navigateToSignUp(from:NavigationDestinations){
		navigateLiveData.postValue(NavigationAction(from,NavigationDestinations.Signup()))
	}
	
	data class NavigationAction(val from:NavigationDestinations, val to:NavigationDestinations)

	sealed class NavigationDestinations{
		class Signup():NavigationDestinations()
		
		class Login():NavigationDestinations()
		
		data class Home(val userId: UUID=UUID.randomUUID() ):NavigationDestinations()
	}
	
	
	
}