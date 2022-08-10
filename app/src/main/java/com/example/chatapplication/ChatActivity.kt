package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var messagerec :RecyclerView
    private lateinit var messagebox:EditText
    private lateinit var sendbtn: ImageView
    private lateinit var messageadapter: Messageadapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef : DatabaseReference

    var recRoom : String?=null
    var sendRoom : String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val recUid = intent.getStringExtra("uid")

        val senderUid= FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        sendRoom = recUid + senderUid

        recRoom = senderUid +recUid

        supportActionBar?.title = name



        messagerec=findViewById(R.id.userrecyclerview23)
        messagebox=findViewById(R.id.msg_box)
        sendbtn=findViewById(R.id.send)

        messageList = ArrayList()
        messageadapter = Messageadapter(this, messageList)

        messagerec.layoutManager =LinearLayoutManager(this)
        messagerec.adapter = messageadapter

        mDbRef.child("chats").child(sendRoom!!).child("message")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(postSnapshot in snapshot.children){
                        val message =postSnapshot.getValue(Message::class.java)

                        messageList.add(message!!)
                    }
                    messageadapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        sendbtn.setOnClickListener {

            val message = messagebox.text.toString()
            val msgobj = Message(message,senderUid)

            mDbRef.child("chats").child(sendRoom!!).child("message").push()
                .setValue(msgobj).addOnSuccessListener {
                    mDbRef.child("chats").child(recRoom!!).child("message").push()
                        .setValue(msgobj)

                }

            messagebox.setText("")

        }
    }
}