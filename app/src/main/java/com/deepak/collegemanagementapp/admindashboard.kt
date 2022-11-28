package com.deepak.collegemanagementapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class admindashboard : AppCompatActivity(), View.OnClickListener {

    lateinit var posttxt: TextView
    lateinit var notiftxt: TextView


    lateinit var recyclerView: RecyclerView
    lateinit var postvalues: ArrayList<postvalues>
    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity)

        posttxt = findViewById(R.id.txtpost)
        notiftxt = findViewById(R.id.txtNOTIF)

        posttxt.setOnClickListener(this)
        notiftxt.setOnClickListener(this)



        recyclerView = findViewById(R.id.recyclerinadminact)
        recyclerView.layoutManager = LinearLayoutManager(this)

        postvalues = arrayListOf()

        db = FirebaseFirestore.getInstance()
        db.collection("posts")
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val postval: postvalues? =
                            data.toObject(com.deepak.collegemanagementapp.postvalues::class.java)
                        if (postval != null) {
                            postvalues.add(postval)
                        }

                    }
                }
                recyclerView.adapter = MyAdapter(postvalues)
            }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }


    }

    override fun onRestart() {
        super.onRestart()
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.txtpost -> {

                val intent = Intent(this, post::class.java)
                startActivity(intent)


            }

            R.id.txtNOTIF -> {


            }




        }
    }


}