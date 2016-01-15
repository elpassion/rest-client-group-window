package pl.elpassion.dmalantowicz.rest_client_example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import pl.elpassion.dmalantowicz.rest_client_example.adapter.RestListAdapter
import pl.elpassion.dmalantowicz.rest_client_example.domain.Place
import pl.elpassion.dmalantowicz.rest_client_example.task.NearPlacesDownloader
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById(R.id.rest_list) as RecyclerView
        val nearPlacesDownloader = NearPlacesDownloader()
        nearPlacesDownloader.doInBackground(URL("dasdasd"))
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        val list : List<Place> = ArrayList()
        recyclerView.adapter = RestListAdapter(list)
    }
}
