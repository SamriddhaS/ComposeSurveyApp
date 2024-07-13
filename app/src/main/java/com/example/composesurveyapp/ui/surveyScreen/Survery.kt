package com.example.composesurveyapp.ui.surveyScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composesurveyapp.R
import com.example.composesurveyapp.ui.surveyScreen.model.FavAnime
import com.example.composesurveyapp.ui.surveyScreen.questions.MultipleChoiceQuestions
import com.example.composesurveyapp.ui.surveyScreen.questions.SingleChoiceQuestion


@Composable
fun QuestionFavAnimeName(
    selectedAnswers:List<String>,
    onClickedAnAnswer:(selected:Boolean,answer:String) -> Unit,
    modifier: Modifier = Modifier
) {

    MultipleChoiceQuestions(
        questionTitle = "What is your favourite anime?",
        questionDescription = "You can choose multiple options if you want",
        totalAnswers = listOf(
            "Naruto",
            "Attack on Titen",
            "One Piece",
            "Death Note",
            "Hunter x hunter",
            "Demon Slayer",
            "Jujustu Kaisen"
        ),
        selectedAnswers = selectedAnswers,
        onOptionSelected = onClickedAnAnswer,
        modifier = modifier
    )

}

@Composable
fun SecondQuestion(
    selectedAnswer: FavAnime?,
    onOptionSelected: (answer: FavAnime) -> Unit,
    modifier: Modifier = Modifier
) {

    SingleChoiceQuestion(
        "Select your favourite anime character.",
        "You can select only one of them",
        totalAnswers = listOf(
            FavAnime("Saitama", R.drawable.saitama),
            FavAnime("Naruto", R.drawable.naruto),
            FavAnime("Minato", R.drawable.minato),
            FavAnime("Levai", R.drawable.levai),
            FavAnime("Goku", R.drawable.goku),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier
    )

}
@Composable
fun ThirdQuestion(
    selectedDate:Long?,
    onClickedPickDate:()->Unit,
    modifier: Modifier,
){
    QuestionWithDateUi(
        title = "When was the last time you saw your fav anime?",
        description = "Select a date",
        onClickedPickDate = onClickedPickDate,
        dateInMilli = selectedDate,
        modifier = modifier
    )
}

@Composable
fun FourthQuestion(
    currentPos:Float?,
    onSliderPosChanged:(Float)->Unit,
    modifier: Modifier = Modifier
) {

    SliderQuestion(
        question = "How do you rate the last anime you watched?",
        questionDesc = "Select a rating using the slider",
        valueRange = 1F..10F,
        steps = 1,
        startingText = "Poor",
        middleText = "Fair",
        endText = "Excellent",
        onSliderPositionChanged = onSliderPosChanged,
        sliderPosition = currentPos,
        modifier = modifier
    )
}