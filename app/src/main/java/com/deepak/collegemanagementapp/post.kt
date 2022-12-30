package com.deepak.collegemanagementapp

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.deepak.collegemanagementapp.R.id
import com.deepak.collegemanagementapp.R.layout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
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

    //image upload
    lateinit var btn_select: Button
    lateinit var imagetitle: EditText
    lateinit var btn_upload: Button
    lateinit var imageview: ImageView
    lateinit var imageUri: Uri

    var storageRef = Firebase.storage








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

        //image upload
        btn_select = findViewById(R.id.btn_select)
        imageview = findViewById(R.id.imageview)
        imagetitle = findViewById(R.id.et_title)
        storageRef = FirebaseStorage.getInstance()



        btnpost.setOnClickListener(this)
        btn_clock.setOnClickListener(this)
        calendar = Calendar.getInstance()
        eturl.setOnClickListener(this)
        btn_select.setOnClickListener(this)


        eturl.movementMethod = LinkMovementMethod.getInstance()
        eturl.setLinkTextColor(Color.BLUE)


    }



    override fun onClick(v: View?) {

        when(v?.id){


            //imageupload

            id.btn_select->{
                selectimage()
            }




            id.btnpost->{



//                val x: String = db_sino




                uploadimage()



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

    private fun uploadimage() {

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("images/$filename")

        var name_of_url = imagetitle.text.toString()

        storageReference.putFile(imageUri).addOnSuccessListener { task ->
            task.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener {

//                    val userId = FirebaseAuth.getInstance().currentUser!!.uid



                    fun saveFireStore(dbTitle: String, dbDescription: String, dbDate: String,dburl :String,dbsino : Int,dbimage :String) {
                        val db =FirebaseFirestore.getInstance()
                        val posts : MutableMap<String,Any> = HashMap()
                        posts["title"] = dbTitle
                        posts["description"] = dbDescription
                        posts["date"] = dbDate
                        posts["url"]=dburl
                        posts["sino"]=dbsino
                        posts["url1"] = dbimage

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
                    var db_title = ettitle.text.toString()
                    var db_description = etdescription.text.toString()
                    var db_date = etdate.text.toString()
                    var db_url = eturl.text.toString()
                    var db_sino = etsino.getText().toString()
                    val finalval_db_sino = db_sino.toInt()

                    saveFireStore(db_title,db_description,db_date,db_url,finalval_db_sino,it.toString())

                }



            imageview.setImageURI(null)
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            imagetitle.text.clear()

        }.addOnFailureListener {
            Toast.makeText(this, "try one more time", Toast.LENGTH_SHORT).show()

        }


    }

    private fun selectimage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK)

            imageUri = data?.data!!

        imageview.setImageURI(imageUri)

    }



}


