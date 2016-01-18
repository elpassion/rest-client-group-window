package pl.elpassion.dmalantowicz.rest_client_example

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import pl.elpassion.dmalantowicz.rest_client_example.adapter.RestListAdapter
import pl.elpassion.dmalantowicz.rest_client_example.domain.Place
import pl.elpassion.dmalantowicz.rest_client_example.task.NearPlacesDownloader
import java.util.*

class MainActivity : AppCompatActivity(), NearPlacesClient {

    val recyclerView by lazy { findViewById(R.id.rest_list) as RecyclerView }
    val editText by lazy { findViewById(R.id.editText) as TextView}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText.setOnEditorActionListener { editText, id, keyEvent ->
            val nearPlacesDownloader = NearPlacesDownloader(this)
            nearPlacesDownloader.execute(editText.text.toString())
            true
        }
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = RestListAdapter(ArrayList())
    }

    override fun onPlacesUpdate(places: List<Place>) {
        recyclerView.adapter = RestListAdapter(places)
    }

    override fun onNoInternetConnection() {
        Snackbar.make(recyclerView, "No internet connection.", Snackbar.LENGTH_LONG).show()
        editText.text = "test"
    }
}
