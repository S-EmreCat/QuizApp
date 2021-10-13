package com.btc.myapplication

import android.annotation.SuppressLint
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.btc.myapplication.databinding.ActivityLeaderBoardBinding
import database.UserDB
import database.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeaderBoardActivity : AppCompatActivity() {
    private lateinit var bindingLeaderBoard:ActivityLeaderBoardBinding
    private lateinit var userList:List<UserEntity>


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLeaderBoard= ActivityLeaderBoardBinding.inflate(layoutInflater)
        setContentView(bindingLeaderBoard.root)
        val userDatabase: UserDB = UserDB.invoke(this)
        val layoutManager=LinearLayoutManager(this)
        bindingLeaderBoard.recyclerView.layoutManager=layoutManager



        CoroutineScope(Dispatchers.Default).launch {
            userList=userDatabase.userDAO().getScoreTable()

            createString(1,bindingLeaderBoard.textView1ndScore)
            bindingLeaderBoard.textView1ndUsername.text=userList.get(index=0).username

            createString(2,bindingLeaderBoard.textView2ndScore)
            bindingLeaderBoard.textView2ndUsername.text=userList.get(index=1).username

            createString(3,bindingLeaderBoard.textView3ndScore)
            bindingLeaderBoard.textView3ndUsername.text=userList.get(index=2).username

            val userid=intent.getIntExtra("userid",21)
            val adapter=RecyclerAdapter(userList = userList,userid)
            bindingLeaderBoard.recyclerView.adapter=adapter
        }
    }
    private fun createString(i:Int,textView: TextView){
        val str1=SpannableString("$i"+"\n"+userList.get(index=i-1).score+"pt")
        val ss1=SpannableString(str1)
        ss1.setSpan(RelativeSizeSpan(3f),0,1,0)
        ss1.setSpan(StyleSpan(Typeface.BOLD),0,1,0)
        textView.text = ss1
    }

}