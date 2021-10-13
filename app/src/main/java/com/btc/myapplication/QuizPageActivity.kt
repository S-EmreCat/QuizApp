package com.btc.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.btc.myapplication.databinding.ActivityQuizPageBinding
import database.UserDB
import database.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import viewmodel.QuizPageViewModel
import android.os.CountDownTimer as CountDownTimer1

class QuizPageActivity : AppCompatActivity() {
    private lateinit var objectTimer: CountDownTimer1
    private lateinit var quizBinding: ActivityQuizPageBinding
    private lateinit var viewModel: QuizPageViewModel
    private lateinit var username :String
    private lateinit var email:String
    private lateinit var password:String
    private var newscore: Int=0
    private var tCount: Int=0
    private var fCount:Int=0
    private var userid: Int=3673
    private lateinit var userDatabase:UserDB

    override fun onStop() {
        super.onStop()
        println("time durdu")
        objectTimer.cancel()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        quizBinding= ActivityQuizPageBinding.inflate(layoutInflater)
        setContentView(quizBinding.root)

        viewModel=ViewModelProvider(this).get(QuizPageViewModel::class.java)
        userid=intent.getIntExtra("userid",3673)
        userDatabase = UserDB.invoke(this)
       // val userDatabase: UserDB = UserDB.invoke(this)
        println("login userid: $userid")
        objectTimer =object : CountDownTimer1(11000,1000){
            override fun onTick(millisUntilFinished: Long) {
                val seconds =((millisUntilFinished)/1000).toInt()
                quizBinding.textViewTimer.text="$seconds"
                quizBinding.circularProgressBar.apply {
                    progressMax=10f
                    setProgressWithAnimation(seconds.toFloat(),250)
                    progressBarColor= Color.BLUE
                }
            }
            override fun onFinish() {
                println("0000000")
                countControll()
            }
        }
        objectTimer.start()
        quizTimer()
        viewModel.soruYukle(radioButtonA = quizBinding.answer1,quizBinding.answer2,quizBinding.answer3,quizBinding.answer4
            ,questionCountTextView = quizBinding.textViewQuestionCount
            ,questionTextView = quizBinding.textViewQuestion)
        quizBinding.answer1.setOnClickListener{
            objectTimer.start()
            pointControll(quizBinding.answer1)
            countControll()
        }
        quizBinding.answer2.setOnClickListener{
            objectTimer.start()
            pointControll(quizBinding.answer2)
            countControll()
        }
        quizBinding.answer3.setOnClickListener{
            objectTimer.start()
            pointControll(quizBinding.answer3)
            countControll()
        }
        quizBinding.answer4.setOnClickListener{
            objectTimer.start()
            pointControll(quizBinding.answer4)
            countControll()
        }
    }

    private fun quizTimer(){
        if(quizBinding.textViewTimer.text=="0"){
            objectTimer.start()
        }
    }
    private fun pointControll(radioButton: RadioButton) {
        val userAnswer = radioButton.text.toString()
        if (userAnswer == viewModel.currentCorrectAnswer.value.toString()) {
            tCount++
            quizBinding.progressBarCorrect.apply {
                this.progress=tCount
                // this.setProgress(tCount,false)
            }
            quizBinding.textViewTcount.setText(tCount.toString())

        } else {
            fCount++
            quizBinding.progressBarWrong.apply {
                this.setProgress(fCount,false)
            }
            quizBinding.textViewFcount.setText(fCount.toString())
        }
    }
    fun countControll() {
        viewModel.questionCount++
        if (viewModel.questionCount != 10) {
            quizTimer()
            viewModel.soruYukle(
                radioButtonA = quizBinding.answer1,
                quizBinding.answer2,
                quizBinding.answer3,
                quizBinding.answer4,
                questionCountTextView = quizBinding.textViewQuestionCount,
                questionTextView = quizBinding.textViewQuestion

            )
        } else {
            println("1:$newscore")
            newscore=tCount*10
            CoroutineScope(Dispatchers.IO).launch {
                println("2:$newscore")
                if(userDatabase.userDAO().getQuizUser(userid) != null && newscore>userDatabase.userDAO().getQuizUser(userid)!!.score){
                    email=userDatabase.userDAO().getQuizUser(userid)!!.email
                    username=userDatabase.userDAO().getQuizUser(userid)!!.username
                    password=userDatabase.userDAO().getQuizUser(userid)!!.password
                    userDatabase.userDAO().update(UserEntity(userid,email,username,password,newscore))
                    println("güncellendi")
                }
                else{
                    println("hatağğğğğğğğğğğğğğğ")
                }
            }
            val intentResult = Intent(this@QuizPageActivity, ResultActivity::class.java)
            intentResult.putExtra("tcount",tCount)
            intentResult.putExtra("userid",userid)
            startActivity(intentResult)
            objectTimer.cancel()

        }
        println("tcount: ${tCount.toString()} fcount: ${fCount.toString()}")
    }
}