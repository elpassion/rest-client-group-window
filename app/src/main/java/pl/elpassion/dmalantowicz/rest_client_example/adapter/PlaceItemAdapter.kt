package pl.elpassion.dmalantowicz.rest_client_example.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import pl.elpassion.dmalantowicz.rest_client_example.R
import pl.elpassion.dmalantowicz.rest_client_example.domain.Place

/**
 * Created by dmalantowicz on 15.01.2016.
 */
class PlaceItemAdapter(private val place: Place) : ItemAdapter {

    override val itemViewType = R.id.name_rate_layout

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.rest_response_list_item, parent, false)
        return NameRateItemHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder) {
        val nameRateItemHolder = holder as NameRateItemHolder
        Glide.with(nameRateItemHolder.icon.context)
                .load(place.icon)
                .into(nameRateItemHolder.icon)
        nameRateItemHolder.name.text = place.name
        nameRateItemHolder.rate.text = if (place.rating != null ) place.rating.toString() else ""
    }

    private inner class NameRateItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById(R.id.name) as TextView
        val rate = itemView.findViewById(R.id.rate) as TextView
        val icon = itemView.findViewById(R.id.placeIcon) as ImageView
    }
}