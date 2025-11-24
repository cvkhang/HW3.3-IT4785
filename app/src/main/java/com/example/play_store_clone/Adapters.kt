package com.example.play_store_clone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// View Types
const val TYPE_HEADER = 0
const val TYPE_APP_VERTICAL = 1
const val TYPE_HORIZONTAL_SECTION = 2

// Wrapper for Horizontal Section
data class HorizontalSectionWrapper(val apps: List<AppItem>)

class MainAdapter(private val items: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  override fun getItemViewType(position: Int): Int {
    return when (items[position]) {
      is String -> TYPE_HEADER
      is AppItem -> TYPE_APP_VERTICAL
      is HorizontalSectionWrapper -> TYPE_HORIZONTAL_SECTION
      else -> throw IllegalArgumentException("Invalid item type")
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return when (viewType) {
      TYPE_HEADER -> {
        val view = inflater.inflate(R.layout.item_section_header, parent, false)
        HeaderViewHolder(view)
      }
      TYPE_APP_VERTICAL -> {
        val view = inflater.inflate(R.layout.item_app_vertical, parent, false)
        VerticalAppViewHolder(view)
      }
      TYPE_HORIZONTAL_SECTION -> {
        val view = inflater.inflate(R.layout.item_horizontal_section, parent, false)
        HorizontalSectionViewHolder(view)
      }
      else -> throw IllegalArgumentException("Invalid view type")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is HeaderViewHolder -> holder.bind(items[position] as String)
      is VerticalAppViewHolder -> holder.bind(items[position] as AppItem)
      is HorizontalSectionViewHolder -> holder.bind(items[position] as HorizontalSectionWrapper)
    }
  }

  override fun getItemCount(): Int = items.size

  // ViewHolders
  class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val sectionTitle: TextView = itemView.findViewById(R.id.sectionTitle)

    fun bind(title: String) {
      sectionTitle.text = title
    }
  }

  class VerticalAppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val appTitle: TextView = itemView.findViewById(R.id.appTitle)
    private val appCategory: TextView = itemView.findViewById(R.id.appCategory)
    private val appRating: TextView = itemView.findViewById(R.id.appRating)
    private val appSize: TextView = itemView.findViewById(R.id.appSize)
    private val appIcon: ImageView = itemView.findViewById(R.id.appIcon)

    fun bind(app: AppItem) {
      appTitle.text = app.title
      appCategory.text = app.category
      appRating.text = "${app.rating} ★"
      appSize.text = app.size
      // In a real app, load image here. We'll use a color based on hash code for variety
      appIcon.setBackgroundColor(getColorForApp(app.title))
    }
  }

  class HorizontalSectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val horizontalRecyclerView: RecyclerView =
            itemView.findViewById(R.id.horizontalRecyclerView)

    fun bind(wrapper: HorizontalSectionWrapper) {
      horizontalRecyclerView.layoutManager =
              LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
      horizontalRecyclerView.adapter = HorizontalAppAdapter(wrapper.apps)
    }
  }
}

class HorizontalAppAdapter(private val apps: List<AppItem>) :
        RecyclerView.Adapter<HorizontalAppAdapter.HorizontalAppViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalAppViewHolder {
    val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_app_horizontal, parent, false)
    return HorizontalAppViewHolder(view)
  }

  override fun onBindViewHolder(holder: HorizontalAppViewHolder, position: Int) {
    holder.bind(apps[position])
  }

  override fun getItemCount(): Int = apps.size

  class HorizontalAppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val appName: TextView = itemView.findViewById(R.id.appName)
    private val appDesc: TextView = itemView.findViewById(R.id.appDesc)
    private val appImage: ImageView = itemView.findViewById(R.id.appImage)

    fun bind(app: AppItem) {
      appName.text = app.title
      appDesc.text = app.category // Using category as description for now
      appImage.setBackgroundColor(getColorForApp(app.title))
    }
  }
}

// Helper to generate consistent colors
fun getColorForApp(name: String): Int {
  val colors =
          listOf(
                  0xFFE57373.toInt(), // Red
                  0xFF81C784.toInt(), // Green
                  0xFF64B5F6.toInt(), // Blue
                  0xFFFFD54F.toInt(), // Yellow
                  0xFFBA68C8.toInt(), // Purple
                  0xFF4DB6AC.toInt() // Teal
          )
  return colors[Math.abs(name.hashCode()) % colors.size]
}
