package com.btc.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.btc.myapplication.databinding.ActivityLoginBinding
import database.UserDB
import kotlinx.coroutines.*
import viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding : ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding= ActivityLoginBinding.inflate(layoutInflater)
        loginBinding.root
        setContentView(loginBinding.root)
        viewModel=ViewModelProvider(this).get(LoginViewModel::class.java)

        loginBinding.LoginButton.setOnClickListener{
            logIn(it)
        }
    }

    fun gotoSignUp(view: View){
        val intent= Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        println("gidiliyor")
    }
    fun logIn(view:View){
        val userDatabase: UserDB = UserDB.invoke(this)
        viewModel.currentUsername=loginBinding.LoginEmail.text.toString()
        viewModel.currentPassword= loginBinding.LoginPassword.text.toString()

        if(TextUtils.isEmpty(viewModel.currentPassword)||TextUtils.isEmpty(  viewModel.currentUsername)){
            Toast.makeText(applicationContext,"Please enter your username and password.",Toast.LENGTH_LONG).show()
        }
        else {
            CoroutineScope(Dispatchers.Default).launch {
                if (userDatabase.userDAO().getUser(viewModel.currentUsername, viewModel.currentPassword) != null) {
                    val intentQuiz=Intent(this@LoginActivity, QuizPageActivity::class.java)
                    intentQuiz.putExtra("userid",(userDatabase.userDAO().getUser(viewModel.currentUsername, viewModel.currentPassword))?.userid?:1111)
                    startActivity(intentQuiz)
                } else {
                    withContext(Dispatchers.Main){
                        Toast.makeText(applicationContext,"Incorrect username or password.",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}