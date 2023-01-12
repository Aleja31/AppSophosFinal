package com.example.app_sophos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.app_sophos.databinding.LoginActivityBinding
import com.example.app_sophos.common.GeneralActions.Companion.prefer
import com.example.app_sophos.viewModel.UserViewModel
import java.util.concurrent.Executor


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding
    val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check in first instance if email and password fields are empty or not matching with email pattern
        // if not disabled the login button
        val emailText = binding.textEmail
        val passText = binding.textPassword

        checkOldLogin()

        binding.buttonlogin.setOnClickListener{
            login()
        }

        binding.buttonfinger.setOnClickListener { validateFinger() }


        //Observing if there is any change in the viewModel


        userViewModel.getUserViewModelObserver().observe(this) {
            if (it.acceso) {
                checkOldLogin()
                prefer.setDataName(it.nombre)
                val intent = Intent(this,MainMenu::class.java)
                startActivity(intent)
                this.finish()
            }
            else  Toast.makeText(this, "El usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show()
        }

        userViewModel.getIsLoadingObserver().observe(this) {
           binding.progressBar.isVisible = it
        }

    }
    private fun validateFields() {
        val email= binding.textEmail
        val password = binding.textPassword
        val errorPassword = binding.errorPassword
        val errorEmail = binding.errorEmail

        var validatedEmail : Boolean = true
        var validatedpassword : Boolean = true

        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches() || email.text.toString().isNullOrEmpty()){
            errorEmail.text = "Invalid Email. Email@example.com"
            validatedEmail= false
        }
        val passwordText = password.text.toString()
       if(passwordText.isNullOrEmpty()){
           errorPassword.text = "This field is required"
           validatedpassword  = false
       }
        else {
           if(passwordText.length < 8) {
               errorPassword.text= "Minimum 8 Character Password"
               validatedpassword  = false
           }
           if(!passwordText.matches(".*[A-Z].*".toRegex())) {
               errorPassword.text= "Must Contain 1 Upper-case Character"
               validatedpassword  = false
           }
           if(!passwordText.matches(".*[a-z].*".toRegex())) {
               errorPassword.text = "Must Contain 1 Lower-case Character"
               validatedpassword  = false
           }
        }

        if (validatedEmail ){
            errorEmail.text = null
        }
        if (validatedpassword){
            errorPassword.text = null
        }
    }

    private fun login(){
       validateFields()
       val  errorEmail = binding.errorEmail
        val errorpassword = binding.errorPassword

        if (errorEmail.text.toString().isNullOrEmpty() && errorpassword.text.toString().isNullOrEmpty()) {
            userViewModel.login(binding.textEmail.text.toString(),binding.textPassword.text.toString())
        }
    }

    fun checkOldLogin(){
        var  list : MutableList<String> = prefer.getDataLogin()

        if (
            (list[0].isNullOrEmpty() || ( !list[0].isNullOrEmpty() && binding.textEmail.text.toString()!= list[0]))
            && ( !binding.textEmail.text.toString().isNullOrEmpty()
                    && !binding.textPassword.text.toString().isNullOrEmpty())

        ){
            prefer.setDataLogin(binding.textEmail.text.toString(), binding.textPassword.text.toString())
        }
        else {
            if (!list[0].isNullOrEmpty()) {
                binding.buttonfinger.isEnabled = true
            }
        }

    }

    fun validateFinger(){

        if (binding.buttonfinger.isEnabled) {
            lateinit var executor: Executor
            lateinit var biometricPrompt: BiometricPrompt
            lateinit var promptInfo: BiometricPrompt.PromptInfo

            executor = ContextCompat.getMainExecutor(this)

            biometricPrompt = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(
                            applicationContext,
                            "Authentication error: $errString", Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult
                    ) {
                        super.onAuthenticationSucceeded(result)
                        // Get the username and password from Preferences
                        var list: MutableList<String> = prefer.getDataLogin()
                        binding.textEmail.setText(list[0])
                        binding.textPassword.setText(list[1])
                        login()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(
                            applicationContext, "Authentication failed, Please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Verify your identity")
                .setSubtitle("Use your fingerprint to verify your identity")
                .setNegativeButtonText("CANCEL")
                .build()

            biometricPrompt.authenticate(promptInfo)

        }
    }
}





