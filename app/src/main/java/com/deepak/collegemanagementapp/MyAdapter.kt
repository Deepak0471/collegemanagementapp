package com.deepak.collegemanagementapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class MyAdapter (private val postvalues :ArrayList<postvalues>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder ( cardview  : View ) : RecyclerView.ViewHolder(cardview) {

        val title : TextView = cardview.findViewById(R.id.txt_title)
        val description : TextView = cardview.findViewById(R.id.txt_description)
        val date : TextView = cardview.findViewById(R.id.txt_date)
        val url : TextView = cardview.findViewById(R.id.txt_url)
        val sino : TextView = cardview.findViewById(R.id.sino)



    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cradview,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = postvalues[position].title
        holder.description.text = postvalues[position].description
        holder.date.text = postvalues[position].date
        holder.url.text = postvalues[position].url
        holder.sino.text = postvalues[position].sino
    }

    override fun getItemCount(): Int {
        return postvalues.size
    }
}