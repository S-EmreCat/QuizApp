package viewmodel

import android.graphics.Color
import android.os.CountDownTimer
import android.widget.RadioButton
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.btc.myapplication.QuizPageActivity
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import database.UserDB
import model.QuizQuestions.Questions
import model.QuizQuestions.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.ApiClient

class QuizPageViewModel: ViewModel() {
    val questions= MutableLiveData<Questions>()
    val results= MutableLiveData<List<Result>>()
    val currentQuestion=MutableLiveData<String>()
    val currentCorrectAnswer=MutableLiveData<String>()
    val currentIncorrectAnswers= MutableLiveData<List<String>>()
    val allRandomAnswers =HashSet<String>()
    var questionCount : Int = 0
    private val questionsApiService=ApiClient()

    fun soruYukle(
        radioButtonA: RadioButton,
        radioButtonB: RadioButton,
        radioButtonC: RadioButton,
        radioButtonD: RadioButton,
        questionCountTextView: TextView,
        questionTextView: TextView
    ) {
        println("Question: $questionCount")
        val myCall = questionsApiService.getData()
        myCall.enqueue(object : Callback<Questions> {
            override fun onResponse(
                call: Call<Questions>,
                response: Response<Questions>
            ) {
                if (response.isSuccessful) {
                    questions.value = response.body() as Questions
                    results.value = questions.value?.results
                    currentQuestion.value = results.value!![questionCount].question
                    currentIncorrectAnswers.value = results.value!![questionCount].incorrect_answers
                    currentCorrectAnswer.value = results.value!![questionCount].correct_answer
                    println("question")
                    println(currentQuestion.value.toString())
                    println("correct: ${currentCorrectAnswer.value.toString()}")
                    allRandomAnswers.clear()
                    allRandomAnswers.add(currentCorrectAnswer.value!!)
                    allRandomAnswers.add(currentIncorrectAnswers.value!!.get(index = 0))
                    allRandomAnswers.add(currentIncorrectAnswers.value!!.get(index = 1))
                    allRandomAnswers.add(currentIncorrectAnswers.value!!.get(index = 2))

                    questionCountTextView.setText("Question ${questionCount+1} / 10")
                    questionTextView.setText(currentQuestion.value)
                    radioButtonA.setText(allRandomAnswers.elementAt(0))
                    radioButtonB.setText(allRandomAnswers.elementAt(1))
                    radioButtonC.setText(allRandomAnswers.elementAt(2))
                    radioButtonD.setText(allRandomAnswers.elementAt(3))
                    radioButtonA.isChecked=false
                    radioButtonB.isChecked=false
                    radioButtonC.isChecked=false
                    radioButtonD.isChecked=false
                }
            }

            override fun onFailure(call: Call<Questions>, t: Throwable) {
                println("fail")
            }
        })
    }

}

