package com.example.composesurveyapp.ui.surveyScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composesurveyapp.R
import com.example.composesurveyapp.ui.surveyScreen.questions.MultipleChoiceQuestions
import com.example.composesurveyapp.ui.surveyScreen.questions.SingleChoiceQuestion
import com.example.composesurveyapp.ui.surveyScreen.questions.Superhero


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
    selectedAnswer: Superhero?,
    onOptionSelected: (answer: Superhero) -> Unit,
    modifier: Modifier = Modifier
) {

    SingleChoiceQuestion(
        "Select your favourite anime character.",
        "You can select only one of them",
        totalAnswers = listOf(
            Superhero("Saitama", R.drawable.saitama),
            Superhero("Naruto", R.drawable.naruto),
            Superhero("Minato", R.drawable.minato),
            Superhero("Levai", R.drawable.levai),
            Superhero("Goku", R.drawable.goku),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier
    )

}