package gal.uvigo.mobileTaskManager.repository.network

import android.content.Context
import com.squareup.moshi.Moshi

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import gal.uvigo.mobileTaskManager.R

/**
 * Class to create and expose the Retrofit webservice.
 */
class RetrofitClient private constructor(BASE_URL : String){

    /**
     * Builds a Moshi instance to parse JSON.
     */
    private val moshi = Moshi.Builder().add(LocalDateMoshiAdapter()) //Needed to turn Task to Json
        .build()

    /**
     * Builds a Retrofit instance to handle web services.
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    /**
     * The exposed WebService REST API.
     */
    val service : TaskApiService = retrofit.create(TaskApiService::class.java)

    //Used for Singleton pattern
    companion object {

        /**
         * The singleton Instance.
         */
        @Volatile
        private var instance: RetrofitClient? = null

        /**
         * Returns the singleton Instance of RetrofitClient.
         */
        fun getInstance(context : Context): RetrofitClient {
            return instance ?: synchronized(this) {
                instance ?: RetrofitClient(
                    context.getString(R.string.URL) + context.getString(R.string.API_KEY) + "/"
                ).also { instance = it }
            }
        }
    }
}