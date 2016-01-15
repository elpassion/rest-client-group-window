package pl.elpassion.dmalantowicz.rest_client_example.task

import android.os.AsyncTask
import pl.elpassion.dmalantowicz.rest_client_example.domain.Place
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

/**
 * Created by dmalantowicz on 15.01.2016.
 */
class NearPlacesDownloader : AsyncTask<URL, Integer, List<Place>>(){



    public override fun doInBackground(vararg params: URL?): List<Place>? {
        val places : List<Place> = ArrayList<Place>()
        val url = params[0]
        if (url != null){
            val urlConnection = url.openConnection() as HttpsURLConnection
            val restResponse = InputStreamReader(urlConnection.inputStream).readText()

        }
        return places
    }
}