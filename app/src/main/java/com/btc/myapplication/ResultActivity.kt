package com.btc.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.btc.myapplication.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var resultBinding : ActivityResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultBinding= ActivityResultBinding.inflate(layoutInflater)
        setContentView(resultBinding.root)

        val tcount=intent.getIntExtra("tcount",1)
        val userid=intent.getIntExtra("userid",3673)
        resultBinding.textViewScore.text="${tcount*10}pt"
        resultBinding.correct.text="$tcount"
        resultBinding.wrong.text="${10-tcount}"

        resultBinding.correct.text="$tcount"

        resultBinding.buttonLeaderBoard.setOnClickListener {
            val intentLeaderBoard= Intent(this@ResultActivity, LeaderBoardActivity::class.java)
            intentLeaderBoard.putExtra("userid",userid)
            startActivity(intentLeaderBoard)
        }
        resultBinding.buttonPlayAgain.setOnClickListener {
            val intentQuizpage=Intent(this,QuizPageActivity::class.java)
            intentQuizpage.putExtra("userid",userid)
            startActivity(intentQuizpage)
        }
        resultBinding.buttonHome.setOnClickListener {
            val intentLogin=Intent(this,LoginActivity::class.java)
            startActivity(intentLogin)
        }



    }
}