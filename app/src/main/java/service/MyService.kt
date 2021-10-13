package service

import model.QuizQuestions.Questions
import retrofit2.Call
import retrofit2.http.GET

interface MyService {

    @GET("api.php?amount=10&category=21&difficulty=medium&type=multiple")
    fun getList(): Call<Questions>
}