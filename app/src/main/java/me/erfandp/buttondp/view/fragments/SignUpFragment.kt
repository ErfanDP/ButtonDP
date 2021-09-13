package me.erfandp.buttondp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import me.erfandp.buttondp.R
import me.erfandp.buttondp.databinding.SignUpFragmentBinding
import me.erfandp.buttondp.utils.extentions.toast
import me.erfandp.buttondp.viewModels.MainViewModel
import me.erfandp.buttondp.viewModels.SignUpViewModel

class SignUpFragment: Fragment() {
	
	private val viewModel: SignUpViewModel by viewModels()
	private val activityViewModel :MainViewModel by activityViewModels()
	private lateinit var binding: SignUpFragmentBinding
	
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = DataBindingUtil.inflate(inflater,
										  R.layout.sign_up_fragment,
										  container,
										  false)
		binding.signupCustomizableGenericButton.onClickListener={
			signUpUser()
		}
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initObservers()
		
	}
	
	private fun initObservers() {
		viewModel.signupResponseLiveData.observe(viewLifecycleOwner) {
			signupResponseAction(it)
		}
	}
	
	private fun signupResponseAction(response:Boolean){
		if(response) {
			context?.toast(getString(R.string.toast_signup_successful), Toast.LENGTH_LONG)
			activityViewModel.navigateToLogin(MainViewModel.NavigationDestinations.Signup())
		}else {
			binding.textFieldSignupUsername.isErrorEnabled = true
			binding.textFieldSignupUsername.error = getString(R.string.signup_failed)
		}
	}
	
	private fun signUpUser(){
		val fullName = binding.textFieldSignupFullName.editText?.text.toString()
		val username = binding.textFieldSignupUsername.editText?.text.toString()
		val password = binding.textFieldSignupPassword.editText?.text.toString()
		
		if(infoValidation(fullName, username, password))
			viewModel.signUpUser(fullName,username,password)
	}
	
	private fun infoValidation(fullName: String, username: String, password: String): Boolean {
		binding.textFieldSignupFullName.isErrorEnabled = false
		binding.textFieldSignupUsername.isErrorEnabled = false
		binding.textFieldSignupPassword.isErrorEnabled = false
		var infoIsValid = true
		if (fullName.isEmpty()) {
			binding.textFieldSignupFullName.isErrorEnabled = true
			binding.textFieldSignupFullName.error = getString(R.string.signup_empty_field_error)
			infoIsValid = false
		}
		if (username.isEmpty()) {
			binding.textFieldSignupUsername.isErrorEnabled = true
			binding.textFieldSignupUsername.error = getString(R.string.signup_empty_field_error)
			infoIsValid = false
		}
		if (username.length > 20) {
			binding.textFieldSignupUsername.isErrorEnabled = true
			binding.textFieldSignupUsername.error = getString(R.string.signup_username_length_error)
			infoIsValid = false
		}
		if (password.isEmpty()) {
			binding.textFieldSignupPassword.isErrorEnabled = false
			binding.textFieldSignupPassword.error = getString(R.string.signup_empty_field_error)
			infoIsValid = false
		}
		return infoIsValid
	}
	
}