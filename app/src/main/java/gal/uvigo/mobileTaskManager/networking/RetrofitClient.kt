package gal.uvigo.mobileTaskManager.networking


import android.content.Context
import com.squareup.moshi.Moshi

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import gal.uvigo.mobileTaskManager.R

class RetrofitClient private constructor(private val BASE_URL : String){

    private val moshi = Moshi.Builder().add(LocalDateJsonAdapter()) //Needed to turn Task to Json
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val service : TaskApiService = retrofit.create(TaskApiService::class.java)

    //Used for Singleton pattern
    companion object {
        // Single instance of the class
        @Volatile
        private var instance: RetrofitClient? = null

        fun getInstance(context : Context): RetrofitClient {
            return instance ?: synchronized(this) {
                instance ?: RetrofitClient(
                    context.getString(R.string.URL) + context.getString(R.string.API_KEY) + "/"
                ).also { instance = it }
            }
        }
    }
}