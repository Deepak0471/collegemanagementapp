package com.deepak.collegemanagementapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class adminlogin : AppCompatActivity(), View.OnClickListener {
    lateinit var etadminid :EditText
    lateinit var etpassword :EditText
    lateinit var login :TextView
    lateinit var back :TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminlogin)

        etadminid = findViewById(R.id.etadminid)
        etpassword = findViewById(R.id.etpassword)

        login = findViewById(R.id.btlogin)
        back = findViewById(R.id.btback)


        login.setOnClickListener(this)
        back.setOnClickListener(this)



    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.btlogin ->{

                var id1 = etadminid.text.toString()
                var password1 = etpassword.text.toString()


                if(id1 == "gmc@nlg" && password1 == "2019gmc@nlg" ){

                    val intent = Intent(this,admindashboard :: class.java)
                    startActivity(intent)
                    finish()


                }else{
                    Toast.makeText(this, "invalid credentials", Toast.LENGTH_SHORT).show()
                }


            }

            R.id.btback ->{

                etadminid.setText("")
                etpassword.setText("")

            }



        }

    }

}
