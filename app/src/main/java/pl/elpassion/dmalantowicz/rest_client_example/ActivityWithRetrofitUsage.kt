package pl.elpassion.dmalantowicz.rest_client_example

import android.os.Bundle
import pl.elpassion.dmalantowicz.rest_client_example.domain.PlaceListWrapper
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import pl.elpassion.dmalantowicz.rest_client_example.adapter.RestListAdapter
import retrofit2.Callback
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.RxJavaCallAdapterFactory
import rx.Subscription
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
        val retrofit = Retrofit.Builder()
                .baseUrl(googleApiDomain)
                .addConverterFactory(GsonConverterFactory.create()) // drugi factory pozwala dodac inna konfiguracje json
                .addCallAdapterFactory( RxJavaCallAdapterFactory.create())
                .build()
        retrofit.create(NearPlacesApi::class.java)
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

    var subscription : Subscription? = null

    private fun setButtonOnEditorActionListener() {
        editText.setOnEditorActionListener { editText, id, keyEvent ->
            val name = editText.text.toString()
            subscription = service.call(name)
                    //background actions
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({onResponse(it)} ,
                            {
                                onFailure(it)
                            })
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription?.unsubscribe()
    }

    fun onFailure(t: Throwable) {
        if (t is Exception) {
            Snackbar.make(recyclerView, noInternetConnectionMsg, Snackbar.LENGTH_LONG).show()
            editText.text = noInternetConnectionMsg
        }
    }

    fun onResponse(response: PlaceListWrapper) {
        val places = response.results

        recyclerView.adapter = RestListAdapter(places)
    }


}
