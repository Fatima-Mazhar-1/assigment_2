package com.example.fatma

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SearchAdapter(private val items: List<Mentor>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardImage: ImageView = view.findViewById(R.id.card_image)
        val cardName: TextView = view.findViewById(R.id.card_name)
        val cardTitle: TextView = view.findViewById(R.id.card_title)
        val cardPrice: TextView = view.findViewById(R.id.card_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.searchcard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.cardName.text = item.name
        holder.cardTitle.text = item.title
        holder.cardPrice.text = item.price
        if(item.picture!="") {
            Glide.with(holder.itemView.context).load(item.picture.toUri()).into(holder.cardImage)
        }
        holder.cardImage.setOnClickListener{
            val intent = Intent(holder.itemView.context, tenpage::class.java)
            intent.putExtra("name", item.name)
            intent.putExtra("title", item.title)
            intent.putExtra("price", item.price)
            intent.putExtra("imageUrl", item.picture)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

    }

    override fun getItemCount() = items.size
}