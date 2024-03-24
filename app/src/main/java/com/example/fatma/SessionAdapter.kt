package com.example.fatma
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class SessionAdapter(private val sessions: MutableList<Session>) : RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    class SessionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.card_name)
        val title: TextView = view.findViewById(R.id.card_title)
        val date: TextView = view.findViewById(R.id.card_date)
        val time: TextView = view.findViewById(R.id.card_time)
        val image: ImageView = view.findViewById(R.id.card_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.session_item, parent, false)
        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val session = sessions[position]
        holder.name.text = session.name
        holder.title.text = session.title
        holder.date.text = session.date
        holder.time.text = session.time

        if(sessions[position].picture!="") {
            Glide.with(holder.itemView.context).load(sessions[position].picture.toUri()).into(holder.image)
        }
    }

    override fun getItemCount() = sessions.size
}