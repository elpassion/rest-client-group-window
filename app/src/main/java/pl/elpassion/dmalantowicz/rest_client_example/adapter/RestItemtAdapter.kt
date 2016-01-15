package pl.elpassion.dmalantowicz.rest_client_example.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pl.elpassion.dmalantowicz.rest_client_example.R
import pl.elpassion.dmalantowicz.rest_client_example.domain.Place

/**
 * Created by dmalantowicz on 15.01.2016.
 */
class RestItemtAdapter(private val place: Place) : ItemAdapter {

    override val itemViewType = R.id.name_rate_layout

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.rest_response_list_item, parent, false)
        return NameRateItemHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder) {
        val nameRateItemHolder = holder as NameRateItemHolder
        nameRateItemHolder.name.text = place.name
        nameRateItemHolder.rate.text = place.name
    }

    private inner class NameRateItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById(R.id.name) as TextView
        val rate = itemView.findViewById(R.id.rate) as TextView
    }
}