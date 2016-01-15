package pl.elpassion.dmalantowicz.rest_client_example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import pl.elpassion.dmalantowicz.rest_client_example.adapter.RestListAdapter
import pl.elpassion.dmalantowicz.rest_client_example.domain.NearPlacesClient
import pl.elpassion.dmalantowicz.rest_client_example.domain.Place
import pl.elpassion.dmalantowicz.rest_client_example.task.NearPlacesDownloader

class MainActivity : AppCompatActivity(), NearPlacesClient {

    val recyclerView by lazy { findViewById(R.id.rest_list) as RecyclerView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputText = findViewById(R.id.inputText) as TextView
        inputText.setOnEditorActionListener { textView, id, keyEvent ->
            val APIExecuter = NearPlacesDownloader(this)
            APIExecuter.execute(textView)
            true
        }
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

    }

    override fun update(places: List<Place>) {
        recyclerView.adapter = RestListAdapter(places)
    }

}
