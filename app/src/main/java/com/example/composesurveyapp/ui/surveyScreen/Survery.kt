package com.example.composesurveyapp.ui.surveyScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composesurveyapp.ui.surveyScreen.questions.MultipleChoiceQuestions


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