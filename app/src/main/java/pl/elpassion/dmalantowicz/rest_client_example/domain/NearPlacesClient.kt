package pl.elpassion.dmalantowicz.rest_client_example.domain

/**
 * Created by dmalantowicz on 15.01.2016.
 */
interface NearPlacesClient {

    fun update(places : List<Place>)
}