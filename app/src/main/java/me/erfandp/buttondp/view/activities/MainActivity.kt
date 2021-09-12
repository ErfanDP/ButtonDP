package me.erfandp.buttondp.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import me.erfandp.buttondp.R
import me.erfandp.buttondp.data.repositories.UserRepository
import me.erfandp.buttondp.view.fragments.LoginFragmentDirections
import me.erfandp.buttondp.viewModels.MainViewModel
import me.erfandp.buttondp.viewModels.MainViewModel.NavigationDestinations
import java.util.*

class MainActivity: AppCompatActivity() {
	
	private val viewModel: MainViewModel by viewModels()
	private lateinit var navController: NavController
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		UserRepository.initializeRepository(applicationContext)
		initNavController()
		initObservers()
	}
	
	private fun initNavController() {
		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
		navController = navHostFragment.navController
	}
	
	private fun initObservers() {
		viewModel.navigateLiveData.observe(this) {
			when (it.to) {
				is NavigationDestinations.Home -> {
					navigateHome(it.to.userId)
				}
				is NavigationDestinations.Signup -> navigateSignUp()
				
				is NavigationDestinations.Login -> navigateLogin()
			}
		}
	}
	
	private fun navigateHome(id: UUID) {
		val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(id.toString())
		navController.navigate(action)
	}
	
	private fun navigateLogin() {
		navController.popBackStack()
	}
	
	private fun navigateSignUp() {
		val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
		navController.navigate(action)
	}
	
}