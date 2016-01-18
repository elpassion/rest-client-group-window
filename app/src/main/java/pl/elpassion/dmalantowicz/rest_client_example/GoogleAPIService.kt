package pl.elpassion.dmalantowicz.rest_client_example

import pl.elpassion.dmalantowicz.rest_client_example.domain.PlaceListWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by dmalantowicz on 18.01.2016.
 */
interface GoogleAPIService {

    @GET("maps/api/place/nearbysearch/json?location=52.2398936,20.9880451&radius=5000&&key=%20AIzaSyC8Cl3TYbzkZ6bb8_fwKeMhFvx_Be6B0CY")
    fun places (@Query("name") name : String) : Call<PlaceListWrapper>

}