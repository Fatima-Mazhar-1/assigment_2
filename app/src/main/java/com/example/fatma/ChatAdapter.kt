package  com.example.fatma
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fatma.Message
import com.example.fatma.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView


class ChatAdapter(
    private val context: Context,
    private val messages: MutableList<Message>,
    private val senderId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_SENDER = 1
    private val VIEW_TYPE_RECEIVER = 2
    private val VIEW_TYPE_SENDER_PICTURE = 3
    private val VIEW_TYPE_RECEIVER_PICTURE = 4
    private val VIEW_TYPE_SENDER_VIDEO = 5
    private val VIEW_TYPE_RECEIVER_VIDEO = 6
    private val VIEW_TYPE_VIDEO_PLAYER = 7

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewHolder for message views
    }

    inner class VideoPlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exoPlayerView: PlayerView = itemView.findViewById(R.id.lul1)
        val player: SimpleExoPlayer = SimpleExoPlayer.Builder(itemView.context).build()

        init {
            exoPlayerView.player = player
        }

        fun bind(videoUrl: String) {
            val mediaItem = MediaItem.fromUri(videoUrl)
            player.setMediaItem(mediaItem)
            player.prepare()
        }

        fun releasePlayer() {
            player.release()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            VIEW_TYPE_SENDER -> inflater.inflate(R.layout.sender_chat, parent, false)
            VIEW_TYPE_RECEIVER -> inflater.inflate(R.layout.reciever_chat, parent, false)
            VIEW_TYPE_SENDER_PICTURE -> inflater.inflate(R.layout.sender_picture, parent, false)
            VIEW_TYPE_RECEIVER_PICTURE -> inflater.inflate(R.layout.reciever_picture, parent, false)
            VIEW_TYPE_SENDER_VIDEO -> inflater.inflate(R.layout.sender_video, parent, false)
            VIEW_TYPE_RECEIVER_VIDEO -> inflater.inflate(R.layout.reciever_video, parent, false)
            else -> throw IllegalArgumentException("Invalid view type")
        }
        return when (viewType) {
            VIEW_TYPE_SENDER_VIDEO, VIEW_TYPE_RECEIVER_VIDEO -> VideoPlayerViewHolder(view)
            else -> MessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        when (holder.itemViewType) {
            VIEW_TYPE_SENDER_VIDEO, VIEW_TYPE_RECEIVER_VIDEO -> {
                val videoUrl = message.link
                val videoHolder = holder as VideoPlayerViewHolder
                videoHolder.bind(videoUrl)
                holder.itemView.setOnClickListener {
                    // Perform action when the message is clicked
                    onMessageClicked(message)
                }
            }
            VIEW_TYPE_SENDER, VIEW_TYPE_RECEIVER -> {
                holder.itemView.findViewById<TextView>(R.id.textViewMessage).text = message.message
                holder.itemView.findViewById<TextView>(R.id.textViewTimestamp).text = message.timestamp
                Glide.with(holder.itemView)
                    .load(message.image.toUri())
                    .into(holder.itemView.findViewById(R.id.imageViewProfile))
                holder.itemView.setOnClickListener {
                    // Perform action when the message is clicked
                    onMessageClicked(message)
                }
            }
            VIEW_TYPE_SENDER_PICTURE, VIEW_TYPE_RECEIVER_PICTURE -> {
                Glide.with(holder.itemView)
                    .load(message.image.toUri())
                    .into(holder.itemView.findViewById(R.id.imageViewProfile))
                holder.itemView.findViewById<TextView>(R.id.textViewTimestamp).text = message.timestamp
                Glide.with(holder.itemView)
                    .load(message.link.toUri())
                    .into(holder.itemView.findViewById(R.id.lul))
                holder.itemView.setOnClickListener {
                    // Perform action when the message is clicked
                    onMessageClicked(message)
                }
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    private fun onMessageClicked(message: Message) {

        val intent = Intent(context,edit_delete::class.java)
        intent.putExtra("messageId", message.messageId)
        intent.putExtra("message", message.message)
        intent.putExtra("senderId", message.senderId)
        intent.putExtra("timestamp", message.timestamp)
        intent.putExtra("image", message.image)
        intent.putExtra("type", message.type)
        intent.putExtra("link", message.link)
        context.startActivity(intent)



    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is VideoPlayerViewHolder) {
            holder.releasePlayer()
        }
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.senderId == senderId) {
            when (message.type) {
                "message" -> VIEW_TYPE_SENDER
                "picture" -> VIEW_TYPE_SENDER_PICTURE
                "video" -> VIEW_TYPE_SENDER_VIDEO
                else -> throw IllegalArgumentException("Unknown message type")
            }
        } else {
            when (message.type) {
                "message" -> VIEW_TYPE_RECEIVER
                "picture" -> VIEW_TYPE_RECEIVER_PICTURE
                "video" -> VIEW_TYPE_RECEIVER_VIDEO
                else -> throw IllegalArgumentException("Unknown message type")
            }
        }
    }
}
