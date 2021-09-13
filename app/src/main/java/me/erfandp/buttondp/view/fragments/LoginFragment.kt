package me.erfandp.buttondp.view.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.erfandp.buttondp.R
import me.erfandp.buttondp.data.model.User
import me.erfandp.buttondp.databinding.LoginFragmentBinding
import me.erfandp.buttondp.utils.extentions.toast
import me.erfandp.buttondp.viewModels.LoginViewModel
import me.erfandp.buttondp.viewModels.MainViewModel
import java.util.*

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
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		firstTimeActionInit()
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
		navigateToSignup()
	}
	
	
	private fun onLoginButtonCLicked() {
		CoroutineScope(Dispatchers.Main).launch {
			val username = binding.textFieldLoginUsername.editText?.text.toString()
			val password = binding.textFieldLoginPassword.editText?.text.toString()
			withContext(Dispatchers.IO) {
				val user = viewModel.checkLoginInfo(username, password)
				if (user != null) {
					withContext(Dispatchers.Main) {
						context?.toast("welcome ${user.fullName}", Toast.LENGTH_SHORT)
						navigateHome(user.id)
					}
				} else {
					withContext(Dispatchers.Main) {
						context?.toast(getString(R.string.login_failed), Toast.LENGTH_SHORT)
					}
				}
			}
		}
	}
	
	
	private fun firstTimeActionInit() {
		CoroutineScope(Dispatchers.IO).launch {
			if (viewModel.isFirstTime()) {
				withContext(Dispatchers.Main) {
					context?.let {
						MaterialAlertDialogBuilder(it)
							.setTitle(getString(R.string.login_dialog_welcome))
							.setMessage(getString(R.string.login_first_time_dialog))
							.setPositiveButton(getString(R.string.register_now)) { _, _ ->
								navigateToSignup()
							}
							.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
								dialog.dismiss()
							}
							.setCancelable(false)
							.show()
					}
				}
				
			}
		}
	}
	
	private fun navigateHome(userid: UUID) {
		activityViewModel.navigateToHome(
			MainViewModel.NavigationDestinations.Login(),
			userid
		)
	}
	
	private fun navigateToSignup() {
		activityViewModel.navigateToSignUp(
			MainViewModel.NavigationDestinations.Login()
		)
	}
	
}