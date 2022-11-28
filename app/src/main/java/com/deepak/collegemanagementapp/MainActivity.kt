package com.deepak.collegemanagementapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast

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