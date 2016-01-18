package pl.elpassion.dmalantowicz.rest_client_example.domain

import java.io.Serializable

/**
 * Created by dmalantowicz on 15.01.2016.
 */
class Place(val name : String, val rating: Double? = null, val icon : String?) : Serializable