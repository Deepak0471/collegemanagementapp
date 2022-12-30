package com.deepak.collegemanagementapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class admindashboard : AppCompatActivity(), View.OnClickListener {

    lateinit var posttxt: TextView
    lateinit var notiftxt: TextView
    lateinit var deletetxt: TextView


    lateinit var recyclerView: RecyclerView
    lateinit var postvalues: ArrayList<postvalues>
    var db = Firebase.firestore

    //image upload
    lateinit var databaseReference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_activity)

        posttxt = findViewById(R.id.txtpost)
        notiftxt = findViewById(R.id.txtNOTIF)
        deletetxt = findViewById(R.id.txtdelete)


        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        posttxt.setOnClickListener(this)
        notiftxt.setOnClickListener(this)
        deletetxt.setOnClickListener(this)

        recyclerView = findViewById(R.id.recyclerinadminact)

        recyclerView = findViewById(R.id.recyclerinadminact)
        recyclerView.layoutManager = LinearLayoutManager(this)

        postvalues = arrayListOf()


        val productArrayList = ArrayList<postvalues>()



        db = FirebaseFirestore.getInstance()
        db.collection("posts")
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val postval: postvalues? =
                            data.toObject(com.deepak.collegemanagementapp.postvalues::class.java)
                        if (postval != null) {
                            productArrayList.add(postval)

                            productArrayList.sortBy {
                                it.sino
                            }
                        }

                    }
                }


                val adapterproduct = MyAdapter(this, productArrayList)
                recyclerView.adapter = adapterproduct

            }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }






        MobileAds.initialize(this) { initstatus ->

        }

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(
                listOf(
                    "ca-app-pub-3940256099942544/2247696110",
                    "ca-app-pub-3940256099942544/2247696110"
                )
            ).build()
        )


        databaseReference = FirebaseDatabase.getInstance().getReference(userId)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (dataSnapshot in snapshot.children){
                        val image = dataSnapshot.getValue(postvalues::class.java)
                        productArrayList.add(postvalues(image.toString()))
                    }
                    val adapterproduct = MyAdapter(this@admindashboard, productArrayList)
                    recyclerView.adapter = adapterproduct

                }
            }

            override fun onCancelled(error: DatabaseError){
                Toast.makeText(this@admindashboard, "errorrrrrrr", Toast.LENGTH_SHORT).show()
            }
        })

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

                val url =
                    "http://console.firebase.google.com/u/0/project/collegemanagement-b46af/notification/compose"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)


            }

            R.id.txtdelete -> {

                val intent = Intent(this, post_delete::class.java)
                startActivity(intent)
            }


        }
    }


}