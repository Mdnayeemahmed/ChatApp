package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class Messageadapter(val context: Context,val messagelist :ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val item_rec=1;
    val item_sent=2;


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType==1){
            val view:View= LayoutInflater.from(context).inflate(R.layout.rec,parent,false)
            return RecViewHolder(view)

        }else{

            val view:View= LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return SentViewHolder(view)

        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMsg= messagelist[position]

        if (holder.javaClass==SentViewHolder::class.java){
            val viewHolder=holder as SentViewHolder
            holder.sent.text= currentMsg.message
        }else{
            val viewHolder = holder as RecViewHolder
            holder.rec.text= currentMsg.message

        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMsg =messagelist[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMsg.senderId)){
            return item_sent
        }else{
            return item_rec
        }
    }

    override fun getItemCount(): Int {
        return messagelist.size
    }


    class SentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sent=itemView.findViewById<TextView>(R.id.send_msg)
    }

    class RecViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val rec=itemView.findViewById<TextView>(R.id.rec_message)

    }

}