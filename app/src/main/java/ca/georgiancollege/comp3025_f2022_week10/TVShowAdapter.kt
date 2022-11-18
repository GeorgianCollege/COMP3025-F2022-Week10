package ca.georgiancollege.comp3025_f2022_week10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TVShowAdapter(private val dataSet: MutableList<TVShow>):
    RecyclerView.Adapter<TVShowAdapter.ViewHolder>() {

    var onTVShowClick: ((TVShow)-> Unit)? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val title: TextView
        val studio: TextView

        init {
            // Define click listener for the ViewHolder's View.
            title = view.findViewById(R.id.tv_show_title)
            studio = view.findViewById(R.id.studio)

            view.setOnClickListener{
                onTVShowClick?.invoke(dataSet[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = dataSet[position].title // index of the array
        viewHolder.studio.text = dataSet[position].studio
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}