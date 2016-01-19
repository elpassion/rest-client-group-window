package pl.elpassion.dmalantowicz.rest_client_example

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import pl.elpassion.dmalantowicz.rest_client_example.adapter.PlacesListAdapter
import pl.elpassion.dmalantowicz.rest_client_example.domain.PlaceListWrapper
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.RxJavaCallAdapterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by dmalantowicz on 18.01.2016.
 */
class ActivityWithRetrofitUsage : AppCompatActivity() {


    companion object {
        val googleApiDomain = "https://maps.googleapis.com"
        val noInternetConnectionMsg = "No internet connection."
    }

    val recyclerView by lazy { findViewById(R.id.rest_list) as RecyclerView }
    val editText by lazy { findViewById(R.id.editText) as TextView }
    val service by lazy {
        Retrofit.Builder()
                .baseUrl(googleApiDomain)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(GoogleAPIService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonOnEditorActionListener()
        setUpRecycleView()
    }

    private fun setUpRecycleView() {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = PlacesListAdapter(ArrayList())
    }

    private fun setButtonOnEditorActionListener() {
        editText.setOnEditorActionListener { editText, id, keyEvent ->
            val name = editText.text.toString()
            service.places(name)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { onResponse(it) },
                        { onFailure(it) })
            true
        }
    }


    fun onFailure(t: Throwable) {
        if (t is Exception) {
            Snackbar.make(recyclerView, noInternetConnectionMsg, Snackbar.LENGTH_LONG).show()
            editText.text = noInternetConnectionMsg
        }
    }

    fun onResponse(placeListWrapper: PlaceListWrapper) {
        val places = placeListWrapper.results
        recyclerView.adapter = PlacesListAdapter(places)
    }

}
