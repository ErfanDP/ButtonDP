package me.erfandp.buttondp.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import me.erfandp.buttondp.R
import me.erfandp.buttondp.data.repositories.UserRepository
import me.erfandp.buttondp.databinding.HomeFragmentBinding
import me.erfandp.buttondp.viewModels.HomeViewModel
import java.util.*
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.erfandp.buttondp.utils.extentions.toast
import me.erfandp.buttondp.viewModels.MainViewModel


class HomeFragment: Fragment() {
	
	companion object {
		const val ARGUMENT_USER_ID = "userId"
	}
	
	private val viewModel: HomeViewModel by viewModels()
	private val activityViewModel :MainViewModel by activityViewModels()
	private lateinit var binding: HomeFragmentBinding
	
	private val logout = {
		context?.toast(getString(R.string.toast_logout))
		activityViewModel.navigateToLogin(MainViewModel.NavigationDestinations.HomeSrc())
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			viewModel.activeUser = UserRepository.getUserById(
				UUID.fromString(it.getString(ARGUMENT_USER_ID)))
		}
		
	}
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = DataBindingUtil.inflate(inflater,R.layout.home_fragment,container,false)
		initView()
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		addObservers()
		addLifecycleObserver()
	}
	
	private fun addObservers() {
		viewModel.logoutCountDownLiveData.observe(viewLifecycleOwner) {
			if (it) {
				logout.invoke()
			}
		}
	}
	
	private fun addLifecycleObserver() {
		lifecycle.addObserver(object: LifecycleObserver {
			@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
			fun onStopped() {
				viewModel.startBackgroundCountDown()
				viewModel.stopForegroundCountDown()
			}
			
			@OnLifecycleEvent(Lifecycle.Event.ON_START)
			fun onStarted() {
				viewModel.stopBackgroundCountDown()
				viewModel.startForegroundCountDown()
			}
			
			@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
			fun onDestroyed() {
				viewModel.stopBackgroundCountDown()
				viewModel.stopForegroundCountDown()
			}
			
		})
	}
	
	private fun initView() {
		binding.homeText.text =
			resources.getString(R.string.home_welcome_text, viewModel.getUserFullName())
	}
	
	
}