package me.erfandp.buttondp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import me.erfandp.buttondp.R
import me.erfandp.buttondp.databinding.LoginFragmentBinding
import me.erfandp.buttondp.utils.extentions.toast
import me.erfandp.buttondp.viewModels.LoginViewModel
import me.erfandp.buttondp.viewModels.MainViewModel

class LoginFragment: Fragment() {
	
	private val viewModel: LoginViewModel by viewModels()
	private val activityViewModel: MainViewModel by activityViewModels()
	private lateinit var binding: LoginFragmentBinding
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
		initView()
		return binding.root
	}
	
	private fun initView() {
		
		binding.customizableGenericButtonLogin.onClickListener = {
			onLoginButtonCLicked()
		}
		
		binding.customizableGenericButtonGotoSignup.onClickListener = {
			onRegisterButtonClicked()
		}
		
	}
	
	
	private fun onRegisterButtonClicked() {
		activityViewModel.navigateToSignUp(MainViewModel.NavigationDestinations.Login())
	}
	
	
	private fun onLoginButtonCLicked() {
		val username = binding.textFieldLoginUsername.editText?.text.toString()
		val password = binding.textFieldLoginPassword.editText?.text.toString()
		val user = viewModel.checkLoginInfo(username, password)
		if (user != null) {
			context?.toast("welcome ${user.fullName}", Toast.LENGTH_SHORT)
			activityViewModel.navigateToHome(
				MainViewModel.NavigationDestinations.Login(),
				user.id)
		} else {
			context?.toast(getString(R.string.login_failed), Toast.LENGTH_SHORT)
		}
	}
}