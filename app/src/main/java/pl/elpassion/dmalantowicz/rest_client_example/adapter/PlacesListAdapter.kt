package pl.elpassion.dmalantowicz.rest_client_example.adapter

import pl.elpassion.dmalantowicz.rest_client_example.domain.Place

/**
 * Created by dmalantowicz on 15.01.2016.
 */
class PlacesListAdapter(val places : List<Place> ) : BaseAdapter(){

    init{
        places.forEach {
            adapters.add(PlaceItemAdapter( it ))
        }
    }
}