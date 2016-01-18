package pl.elpassion.dmalantowicz.rest_client_example

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import pl.elpassion.dmalantowicz.rest_client_example.adapter.RestListAdapter
import pl.elpassion.dmalantowicz.rest_client_example.domain.PlaceListWrapper
import retrofit2.Callback
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import java.util.*

/**
 * Created by dmalantowicz on 18.01.2016.
 */
class ActivityWithRetrofitUsage : AppCompatActivity(), Callback<PlaceListWrapper> {

    companion object {
        val endPointDomain = "https://maps.googleapis.com"
        val noInternetConnectionMsg = "No internet connection."
    }

    val recyclerView by lazy { findViewById(R.id.rest_list) as RecyclerView }
    val editText by lazy { findViewById(R.id.editText) as TextView }
    val service by lazy{
        val retrofit = Retrofit.Builder()
                .baseUrl(endPointDomain)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create(GoogleAPIService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonOnEditorActionListener()
        setUpRecycleView()
    }

    private fun setUpRecycleView() {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = RestListAdapter(ArrayList())
    }

    private fun setButtonOnEditorActionListener() {
        editText.setOnEditorActionListener { editText, id, keyEvent ->
            val name = editText.text.toString()
            service.places(name).enqueue(this)
            true
        }
    }

    override fun onFailure(t: Throwable) {
        if (t is Exception) {
            Snackbar.make(recyclerView, noInternetConnectionMsg, Snackbar.LENGTH_LONG).show()
            editText.text = noInternetConnectionMsg
        }
    }

    override fun onResponse(response: retrofit2.Response<PlaceListWrapper>) {
        val places = response.body().results
        recyclerView.adapter = RestListAdapter(places)
    }
}
