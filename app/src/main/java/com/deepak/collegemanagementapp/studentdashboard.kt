package com.deepak.collegemanagementapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class studentdashboard : AppCompatActivity() {

    lateinit var studentrecyclerview : RecyclerView
    lateinit var postvalues: ArrayList<postvalues>
    var db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_activity)


        studentrecyclerview = findViewById(R.id.studentrecyclerview)

        studentrecyclerview = findViewById(R.id.studentrecyclerview)
        studentrecyclerview.layoutManager = LinearLayoutManager(this)

        postvalues = arrayListOf()

        db = FirebaseFirestore.getInstance()
        db.collection("posts")
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for (data in it.documents){
                        val postval : postvalues? = data.toObject(com.deepak.collegemanagementapp.postvalues::class.java)
                        if (postval != null) {
                            postvalues.add(postval)
                        }

                    }
                }
                studentrecyclerview.adapter = MyAdapter(postvalues)
            }
            .addOnFailureListener{
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }


    }
}