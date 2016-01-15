package pl.elpassion.dmalantowicz.rest_client_example.task

import android.os.AsyncTask
import android.widget.TextView
import org.json.JSONObject
import pl.elpassion.dmalantowicz.rest_client_example.domain.NearPlacesClient
import pl.elpassion.dmalantowicz.rest_client_example.domain.Place
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

/**
 * Created by dmalantowicz on 15.01.2016.
 */
class NearPlacesDownloader(val nearPlacesClient: NearPlacesClient) : AsyncTask<TextView, Integer, List<Place>>(){

    val urlFirstPart = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.2398936,20.9880451&radius=5000&name="
    val urlSecondPart = "&key=%20AIzaSyC8Cl3TYbzkZ6bb8_fwKeMhFvx_Be6B0CY"

    public override fun doInBackground(vararg params: TextView?): List<Place>? {
        val places : MutableList<Place> = ArrayList<Place>()
        val textView = params[0]
        if (textView != null){
            val url = URL(urlFirstPart + textView.text + urlSecondPart)
            val urlConnection = url.openConnection() as HttpsURLConnection
            val restResponse = InputStreamReader(urlConnection.inputStream).readText()
            parseJson(places, restResponse)
        }
        return places
    }

    private fun parseJson(places: MutableList<Place>, restResponse: String) {
        val values = JSONObject(restResponse).getJSONArray("results")
        for (i in 0..values.length() - 1) {
            val placeJson = values.getJSONObject(i)
            val name = placeJson.getString("name")
            val place : Place
            if (!placeJson.isNull("rating")) {
                val rate = placeJson.getDouble("rating")
                place = Place(name, rate)
            } else {
                place = Place(name = name)
            }
            places.add(place)
        }
    }

    override fun onPostExecute(result: List<Place>?) {
        nearPlacesClient.update(result!!)
    }

}