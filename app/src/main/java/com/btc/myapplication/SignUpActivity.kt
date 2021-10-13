package com.btc.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.btc.myapplication.databinding.ActivitySignUpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import database.UserDB
import database.UserEntity
import kotlinx.coroutines.withContext

class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpBinding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        val userDatabase: UserDB = UserDB.invoke(this)


        signUpBinding.SignUpGoLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            CoroutineScope(Dispatchers.Default).launch {
                // delete all code block
                //userDatabase?.userDAO()?.deleteAll()
                val d:ArrayList<UserEntity> = userDatabase.userDAO().getAllUser() as ArrayList<UserEntity>
                println("user size: ${d.size}")
                for (item in d){
                    println("id:  ${item.userid}")
                    println("username:  ${item.username}")
                    println("password:  ${item.password}")
                    println("score:  ${item.score}")
                }
            }
            startActivity(intent)
        }
        signUpBinding.SignUpButton.setOnClickListener{
            val email=signUpBinding.SignUpEmail.text.toString()
            val username=signUpBinding.SignUpTextPersonName.text.toString()
            val password=signUpBinding.SignUpPassword.text.toString()

            if(email==""||username==""||password==""){
                Toast.makeText(applicationContext,"Please fill all fields.",Toast.LENGTH_LONG).show()
            }
            else{
            CoroutineScope(Dispatchers.Default).launch {
                if (userDatabase.userDAO().getUserName(username) != null) {
                    signUpBinding.SignUpEmail.text.clear()
                    signUpBinding.SignUpTextPersonName.text.clear()
                    signUpBinding.SignUpPassword.text.clear()
                    signUpBinding.SignUpPassword.clearFocus()
                    withContext(Dispatchers.Main){
                        Toast.makeText(applicationContext,"Username already taken. Please enter a different username.",Toast.LENGTH_LONG).show()
                    }

                } else {
                    val user= UserEntity(email = email,username = username,password = password)
                    userDatabase.userDAO().insert(user)
                    withContext(Dispatchers.Main){
                        Toast.makeText(applicationContext,"Congratulations, you have successfully signed up.",Toast.LENGTH_LONG).show()
                    }
                }
            }
            }
        }
    }
}