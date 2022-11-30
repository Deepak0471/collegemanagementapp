package com.deepak.collegemanagementapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class post_delete : AppCompatActivity() {
    lateinit var sirealno : EditText
    lateinit var deletebtn : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_delete)

        sirealno = findViewById(R.id.et_sino)
        deletebtn = findViewById(R.id.btn_del_post)

        deletebtn.setOnClickListener{
            val db = Firebase.firestore

            if(sirealno.text.toString().isNotEmpty()){

                val query = db.collection("posts")
                    .whereEqualTo("sino",sirealno.text.toString())
                    .get()
                query.addOnSuccessListener {
                    for (document in it){

                        db.collection("posts").document(document.id).delete().addOnSuccessListener {
                            Toast.makeText(this, "record deleted", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener{
                            Toast.makeText(this, "record not deleted", Toast.LENGTH_SHORT).show()
                        }
                    }
                }



            }else{

                Toast.makeText(this, "field not deleted", Toast.LENGTH_SHORT).show()

            }
            finish()




        }

    }


}