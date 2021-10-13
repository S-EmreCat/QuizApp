package model.QuizQuestions

import com.google.gson.annotations.SerializedName

data class Questions(
    @SerializedName("response_code")
    val response_code: Int,
    @SerializedName("results")
    val results: List<Result>
)