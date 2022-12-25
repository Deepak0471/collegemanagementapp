package com.deepak.collegemanagementapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var admin : TextView
    lateinit var student : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        admin = findViewById(R.id.btadmin)
        student = findViewById(R.id.btstudent)

        student.setOnClickListener(this)
        admin.setOnClickListener(this)

        FirebaseMessaging.getInstance().subscribeToTopic("ntf")


    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.btstudent -> {
                val intent = Intent(this,studentlogin :: class.java)
                startActivity(intent)

            }

            R.id.btadmin -> {
                val intent = Intent(this,adminlogin :: class.java)
                startActivity(intent)

            }


        }
    }
}