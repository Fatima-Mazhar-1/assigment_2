package com.example.fatma

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CardAdapter(val cardArray: Array<CardData>) :
    RecyclerView.Adapter<CardAdapter.ModelViewHolder>() {
    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameTextView: TextView
        var titleTextView: TextView
        var priceTextView: TextView

        init {

            nameTextView = itemView.findViewById(R.id.card_name)
            titleTextView = itemView.findViewById(R.id.card_title)
            priceTextView = itemView.findViewById(R.id.card_price)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardAdapter.ModelViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view: View = when (viewType) {
            0 -> {
                inflater.inflate(R.layout.cardview, parent, false)
            }

            1 -> {
                inflater.inflate(R.layout.cardview, parent, false)
            }

            else -> {
                inflater.inflate(R.layout.cardview, parent, false)
            }
        }
        return ModelViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    override fun onBindViewHolder(holder: CardAdapter.ModelViewHolder, position: Int) {
        holder.nameTextView.text = cardArray[position].name
        holder.priceTextView.text = cardArray[position].price
        holder.titleTextView.text = cardArray[position].title

        Glide.with(holder.itemView)
            .load(cardArray[position].imageUrl)
            .into(holder.itemView.findViewById(R.id.image))

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, tenpage::class.java)
            intent.putExtra("name", cardArray[position].name)
            intent.putExtra("price", cardArray[position].price)
            intent.putExtra("title", cardArray[position].title)
            intent.putExtra("imageUrl", cardArray[position].imageUrl)
            it.context.startActivity(intent)
        }


    }




    override fun getItemCount(): Int {
        return cardArray.size
    }

}