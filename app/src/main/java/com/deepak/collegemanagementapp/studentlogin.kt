package com.deepak.collegemanagementapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class studentlogin : AppCompatActivity(), View.OnClickListener {
    lateinit var mAuth: FirebaseAuth
    lateinit var txt_email: EditText
    lateinit var txt_password: EditText
    lateinit var button: Button
    lateinit var txt_register: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentlogin)

        txt_email = findViewById<EditText>(R.id.txt_email)
        txt_password = findViewById<EditText>(R.id.txt_password)
        button = findViewById(R.id.button2)
        txt_register = findViewById(R.id.txt_register)
        mAuth = FirebaseAuth.getInstance()



        button.setOnClickListener(this)

        txt_register.setOnClickListener(this)

        logincheck()
    }

    private fun logincheck() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            val i = Intent(this, studentdashboard::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        } else {
            // User is signed out
        }
    }

    private fun login() {
        val user = txt_email.text.toString().trim { it <= ' ' }
        val pass = txt_password!!.text.toString().trim { it <= ' ' }
        if (user.isEmpty()) {
            txt_email.error = "email cannot be empty"
        }
        if (pass.isEmpty()) {
            txt_password!!.error = "password cannot be empty"
        } else {
            mAuth!!.signInWithEmailAndPassword(user, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "login successfull", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this, studentdashboard::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "login failed" + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.button2 -> {
                login()

            }
            R.id.txt_register -> {

                startActivity(
                    Intent(
                        this,
                        studentregister::class.java
                    )
                )

            }

        }
    }
}