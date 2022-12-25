package com.deepak.collegemanagementapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
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



//                studentrecyclerview.adapter = MyAdapter(postvalues)
                val productArrayList = ArrayList<postvalues>()

//                for (i in title.indices){
//                    //add data to model
//                    val x = postvalues(title = String(), description = String(), date = String(), url = String(), sino = String())
//                    productArrayList.add(x)
//                }

        db = FirebaseFirestore.getInstance()
        db.collection("posts")
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for (data in it.documents){
                        val postval : postvalues? = data.toObject(com.deepak.collegemanagementapp.postvalues::class.java)
                        if (postval != null) {
                            productArrayList.add(postval)
                            productArrayList.sortBy{
                                it.sino
                            }
                        }


                    }
                }

                val adapterproduct = MyAdapter(this,productArrayList)
                studentrecyclerview.adapter=adapterproduct



            }
            .addOnFailureListener{
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }



        MobileAds.initialize(this,){
                initstatus ->

        }

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ca-app-pub-3940256099942544/2247696110","ca-app-pub-3940256099942544/2247696110")).build()
        )


    }
}