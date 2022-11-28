package com.deepak.collegemanagementapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class studentregister : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var reg_email: EditText
    lateinit var reg_password: EditText
    lateinit var button2: Button
    lateinit var txt_login: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentregister)

        reg_email = findViewById(R.id.reg_email)
        reg_password = findViewById(R.id.reg_password)
        button2 = findViewById(R.id.button2)
        txt_login = findViewById(R.id.txt_login)
        mAuth = FirebaseAuth.getInstance()


        button2.setOnClickListener {
            register()

        }
        txt_login.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    studentlogin::class.java
                )
            )
        }
    }


    private fun register() {
        val user = reg_email.text.toString()
        val pass = reg_password.text.toString()
        if (user.isEmpty()) {
            reg_email.error = "email cannot be empty"
        }
        if (pass.isEmpty()) {
            reg_password.error = "password cannot be empty"
        } else {
            mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "register successfull",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    startActivity(Intent(this, studentlogin::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "register failed" + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }

}