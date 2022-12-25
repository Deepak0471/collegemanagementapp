package com.deepak.collegemanagementapp

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.deepak.collegemanagementapp.R.id
import com.deepak.collegemanagementapp.R.layout
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class post : AppCompatActivity(), View.OnClickListener {

    lateinit var ettitle : EditText
    lateinit var etdescription : EditText
    lateinit var etdate : EditText
    lateinit var eturl : EditText
    lateinit var etsino : EditText
    lateinit var btnpost : TextView
    lateinit var calendar : Calendar
    lateinit var btn_clock : ImageButton








    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_post)


        ettitle = findViewById(id.ettitle)
        etdescription = findViewById(id.etdescription)
        etdate = findViewById(id.etdate)
        eturl = findViewById(id.eturl)
        etsino = findViewById(id.etsino)
        btnpost = findViewById(id.btnpost)
        btn_clock = findViewById(id.btn_clock)



        btnpost.setOnClickListener(this)
        btn_clock.setOnClickListener(this)
        calendar = Calendar.getInstance()
        eturl.setOnClickListener(this)


        eturl.movementMethod = LinkMovementMethod.getInstance()
        eturl.setLinkTextColor(Color.BLUE)


    }



    override fun onClick(v: View?) {

        when(v?.id){




            id.btnpost->{

                var db_title = ettitle.text.toString()
                var db_description = etdescription.text.toString()
                var db_date = etdate.text.toString()
                var db_url = eturl.text.toString()
                var db_sino = etsino.getText().toString()
                val finalval_db_sino = db_sino.toInt()

//                val x: String = db_sino



                saveFireStore(db_title,db_description,db_date,db_url,finalval_db_sino)



                finish()





            }



            id.btn_clock ->{

                var dataPickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                    var dateFormat = ""+ dayOfMonth + "/" + "${month+1}" + "/"+ year
                    etdate.setText("$dateFormat")

                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
                dataPickerDialog.show()
            }


        }


    }

    private fun saveFireStore(dbTitle: String, dbDescription: String, dbDate: String,dburl :String,dbsino : Int) {
        val db =FirebaseFirestore.getInstance()
        val posts : MutableMap<String,Any> = HashMap()
        posts["title"] = dbTitle
        posts["description"] = dbDescription
        posts["date"] = dbDate
        posts["url"]=dburl
        posts["sino"]=dbsino

        db.collection("posts")
            .add(posts)
            .addOnSuccessListener {
                Toast.makeText(this, "record added", Toast.LENGTH_SHORT).show()
                ettitle.setText("")
                etdescription.setText("")
                etdate.setText("")
                eturl.setText("")
                etsino.setText("")
            }
            .addOnFailureListener{
                Toast.makeText(this, "record failed to add", Toast.LENGTH_SHORT).show()
            }




    }


//    private fun readFireStore(){
//        val db =FirebaseFirestore.getInstance()
//        db.collection("posts")
//            .get()
//            .addOnCompleteListener{
//                val result: StringBuffer = StringBuffer()
//
//                if(it.isSuccessful){
//                    for (document in it.result!!)
//                        result.append(document.data.getValue("title")).append(" ")
//                            .append(document.data.getValue("description")).append(" ")
//                            .append(document.data.getValue("date")).append("\n\n")
//                }
//                datashow.setText(result)
//            }
//
//    }
}