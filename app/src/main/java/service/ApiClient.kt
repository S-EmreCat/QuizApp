package service

import model.QuizQuestions.Questions
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val BASE_URL = "https://opentdb.com/"
    private val api= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MyService::class.java)

    fun getData() : Call<Questions>{
        return api.getList()
    }
}