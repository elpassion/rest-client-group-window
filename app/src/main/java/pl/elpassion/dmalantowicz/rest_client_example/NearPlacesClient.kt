package pl.elpassion.dmalantowicz.rest_client_example

import pl.elpassion.dmalantowicz.rest_client_example.domain.Place

/**
 * Created by dmalantowicz on 15.01.2016.
 */
interface NearPlacesClient {
    fun onPlacesUpdate(places : List<Place>)
    fun onNoInternetConnection()
}