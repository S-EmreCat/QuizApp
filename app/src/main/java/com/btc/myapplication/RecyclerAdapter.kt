package com.btc.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import database.UserEntity

class RecyclerAdapter(val userList:List<UserEntity>,val userid:Int) :RecyclerView.Adapter<RecyclerAdapter.UserVH>() {
    class UserVH(itemView: View) :RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview,parent,false)
        return UserVH(itemView)
    }

    override fun onBindViewHolder(holder: UserVH,position: Int) {
        if(userid==userList.get(index=position).userid){
            holder.itemView.findViewById<TextView>(R.id.recyclerTextViewUsername).text ="You"
           holder.itemView.findViewById<TextView>(R.id.recyclerTextViewUsername).setTextColor(Color.parseColor("#3092BF"))
            holder.itemView.findViewById<TextView>(R.id.recyclerTextViewScore).text = "${userList.get(index=position).score}pt"
            holder.itemView.findViewById<TextView>(R.id.recyclerTextViewRank).text =
                (position + 1).toString()
        }
        else{
            holder.itemView.findViewById<TextView>(R.id.recyclerTextViewUsername).text =
                userList.get(index=position).username
            holder.itemView.findViewById<TextView>(R.id.recyclerTextViewScore).text =
                "${userList.get(index=position).score}pt"
            holder.itemView.findViewById<TextView>(R.id.recyclerTextViewRank).text =
                (position + 1).toString()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}