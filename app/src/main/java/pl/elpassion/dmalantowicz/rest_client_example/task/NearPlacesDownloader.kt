package pl.elpassion.dmalantowicz.rest_client_example.task

import android.os.AsyncTask
import com.google.gson.Gson
import pl.elpassion.dmalantowicz.rest_client_example.NearPlacesClient
import pl.elpassion.dmalantowicz.rest_client_example.Response
import pl.elpassion.dmalantowicz.rest_client_example.domain.Place
import java.io.InputStreamReader
import java.net.URL
import java.net.URLEncoder
import java.util.*
import javax.net.ssl.HttpsURLConnection


class NearPlacesDownloader(val nearPlacesClient: NearPlacesClient) : AsyncTask<String, Int, List<Place>>() {

    val urlFirstPart = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.2398936,20.9880451&radius=5000&name="
    val urlSecondPart = "&key=%20AIzaSyC8Cl3TYbzkZ6bb8_fwKeMhFvx_Be6B0CY"

    public override fun doInBackground(vararg params: String): List<Place> {
        val places: MutableList<Place> = ArrayList<Place>()
        val text = params[0]
        val phrase = URLEncoder.encode(text, "UTF-8")
        val urlString = urlFirstPart + phrase + urlSecondPart
        val url = URL(urlString)
        val urlConnection = url.openConnection() as HttpsURLConnection

        val restResponse: String
        try {
            restResponse = InputStreamReader(urlConnection.inputStream).readText()
            places.addAll(parseJson(restResponse))
            return places
        } catch(e: Exception) {
            return places
        }

    }

    private fun parseJson(restResponse: String): List<Place>{
        val response : Response = Gson().fromJson(restResponse, Response::class.java)
        return response.results
    }

    override fun onPostExecute(result: List<Place>) {
        nearPlacesClient.update(result)
    }

}