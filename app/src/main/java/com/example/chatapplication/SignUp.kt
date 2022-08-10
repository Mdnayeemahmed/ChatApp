package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var editname : EditText
private lateinit var editemail : EditText
private lateinit var pass : EditText
private lateinit var btnsignup : Button
private lateinit var mDbRef: DatabaseReference
private lateinit var mauth : FirebaseAuth

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()


        editname=findViewById(R.id.edit_name)
        editemail=findViewById(R.id.edit_email)
        pass=findViewById(R.id.edit_password)
        btnsignup=findViewById(R.id.signup)
        mauth=FirebaseAuth.getInstance()


        btnsignup.setOnClickListener {
            val name= editname.text.toString()
            val email =editemail.text.toString()
            val pass = pass.text.toString()

            signup(name,email,pass)
        }
    }

    private  fun signup (name:String,email:String,pass:String){

        mauth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUsertoDatabase(name,email, mauth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "Some Error Occured", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUsertoDatabase(name: String,email: String,uid:String){
        mDbRef= FirebaseDatabase.getInstance().getReference()

        mDbRef.child("User").child(uid).setValue(User(name,email,uid))

    }
}