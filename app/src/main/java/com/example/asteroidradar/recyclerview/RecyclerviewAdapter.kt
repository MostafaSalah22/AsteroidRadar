package com.example.asteroidradar.recyclerview;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.R
import com.example.asteroidradar.database.AsteroidDatabaseModel


class RecyclerviewAdapter(private var arrayList: ArrayList<AsteroidDatabaseModel>) :
    RecyclerView.Adapter<RecyclerviewAdapter.AsteroidViewHolder>() {

    var onItemClick: ((AsteroidDatabaseModel) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.asteroid_item, parent, false)
        return AsteroidViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = arrayList[position]
        holder.name.text = item.codename
        holder.closeApproachDate.text = item.closeApproachDate
        when(item.isPotentiallyHazardous){
            false -> holder.statue.setImageResource(R.drawable.ic_status_normal)
            else -> holder.statue.setImageResource(R.drawable.ic_baseline_sentiment_dissatisfied_24)
        }


        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun update(modelList:ArrayList<AsteroidDatabaseModel>){
        arrayList = modelList
        this.notifyDataSetChanged()
    }


    class AsteroidViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name:TextView = itemView.findViewById(R.id.name)
        val closeApproachDate:TextView = itemView.findViewById(R.id.closeApproachDate)
        val statue:ImageView = itemView.findViewById(R.id.statue)

    }
}