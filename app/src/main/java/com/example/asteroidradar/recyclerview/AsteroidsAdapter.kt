package com.example.asteroidradar.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.R
import com.example.asteroidradar.database.AsteroidDatabaseModel
import com.example.asteroidradar.databinding.AsteroidItemBinding

class AsteroidsAdapter: ListAdapter<AsteroidDatabaseModel, AsteroidsAdapter.AsteroidViewHolder>(AsteroidsDiffCallback()) {

    class AsteroidViewHolder(private val binding: AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val name: TextView = binding.name
        val closeApproachDate: TextView = binding.closeApproachDate
        val statue: ImageView = binding.statue

        fun bind(item: AsteroidDatabaseModel){
            binding.asteroid = item
        }

    }

    var onItemClick: ((AsteroidDatabaseModel) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AsteroidsAdapter.AsteroidViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = AsteroidItemBinding.inflate(inflater,parent,false)
        return AsteroidViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: AsteroidsAdapter.AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.codename
        holder.closeApproachDate.text = item.closeApproachDate
        when(item.isPotentiallyHazardous){
            false -> holder.statue.setImageResource(R.drawable.ic_status_normal)
            else -> holder.statue.setImageResource(R.drawable.ic_baseline_sentiment_dissatisfied_24)
        }
        holder.bind(item)


        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

}

class AsteroidsDiffCallback(): DiffUtil.ItemCallback<AsteroidDatabaseModel>(){
    override fun areItemsTheSame(
        oldItem: AsteroidDatabaseModel,
        newItem: AsteroidDatabaseModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AsteroidDatabaseModel,
        newItem: AsteroidDatabaseModel
    ): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

}