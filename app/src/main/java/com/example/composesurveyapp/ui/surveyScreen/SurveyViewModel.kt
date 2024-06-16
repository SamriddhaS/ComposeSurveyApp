package com.example.composesurveyapp.ui.surveyScreen

import android.net.Uri
import android.view.View
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composesurveyapp.ui.surveyScreen.questions.Superhero

class SurveyViewModel:ViewModel() {

    private val questionOrder: List<SurveyQuestion> = listOf(
        SurveyQuestion.FREE_TIME,
        SurveyQuestion.SUPERHERO,
        SurveyQuestion.LAST_TAKEAWAY,
        SurveyQuestion.FEELING_ABOUT_SELFIES,
        SurveyQuestion.TAKE_SELFIE,
    )

    private var questionIndex = 0

    // ----- Responses exposed as State -----

    private val _freeTimeResponse = mutableStateListOf<Int>()
    val freeTimeResponse: List<Int>
        get() = _freeTimeResponse

    private val _superheroResponse = mutableStateOf<Superhero?>(null)
    val superheroResponse: Superhero?
        get() = _superheroResponse.value

    private val _takeawayResponse = mutableStateOf<Long?>(null)
    val takeawayResponse: Long?
        get() = _takeawayResponse.value

    private val _feelingAboutSelfiesResponse = mutableStateOf<Float?>(null)
    val feelingAboutSelfiesResponse: Float?
        get() = _feelingAboutSelfiesResponse.value

    private val _selfieUri = mutableStateOf<Uri?>(null)
    val selfieUri
        get() = _selfieUri.value


    fun onBackPressed(): Boolean {
        if (questionIndex == 0) {
            return false
        }
        changeQuestion(questionIndex - 1)
        return true
    }



    private val _surveyScreenData = mutableStateOf(createSurveyScreenData())
    val surveyScreenData: SurveyScreenData?
        get() = _surveyScreenData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value

    fun onPreviousPressed() {
        if (questionIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changeQuestion(questionIndex - 1)
    }

    fun onNextPressed() {
        changeQuestion(questionIndex + 1)
    }

    private fun changeQuestion(newQuestionIndex:Int){
        questionIndex = newQuestionIndex
        _isNextEnabled.value = getIsNextEnabled()
        _surveyScreenData.value = createSurveyScreenData()
    }

    private fun createSurveyScreenData(): SurveyScreenData {
        return SurveyScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1,
            surveyQuestion = questionOrder[questionIndex],
        )
    }

    private fun getIsNextEnabled(): Boolean {
        return when (questionOrder[questionIndex]) {
            SurveyQuestion.FREE_TIME -> _freeTimeResponse.isNotEmpty()
            SurveyQuestion.SUPERHERO -> _superheroResponse.value != null
            SurveyQuestion.LAST_TAKEAWAY -> _takeawayResponse.value != null
            SurveyQuestion.FEELING_ABOUT_SELFIES -> _feelingAboutSelfiesResponse.value != null
            SurveyQuestion.TAKE_SELFIE -> _selfieUri.value != null
        }
    }


}

class SurveyViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyViewModel::class.java)) {
            return SurveyViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

enum class SurveyQuestion {
    FREE_TIME,
    SUPERHERO,
    LAST_TAKEAWAY,
    FEELING_ABOUT_SELFIES,
    TAKE_SELFIE,
}
data class SurveyScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val surveyQuestion: SurveyQuestion,
)