package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private lateinit var editemail :EditText
    private lateinit var editpass : EditText
    private lateinit var btnlogin :Button
    private lateinit var btnsignup :Button
    private lateinit var mauth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        editemail=findViewById(R.id.edit_email)
        editpass=findViewById(R.id.edit_password)
        btnlogin=findViewById(R.id.login)
        btnsignup=findViewById(R.id.signup)

        mauth=FirebaseAuth.getInstance()

        btnsignup.setOnClickListener {
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        btnlogin.setOnClickListener {
            val email =editemail.text.toString()
            val pass = editpass.text.toString()

            login(email,pass)
        }

    }

    private  fun login (email:String,pass:String){

        mauth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@Login, "User Doesn't Exist", Toast.LENGTH_SHORT).show()


                }
            }

    }
}