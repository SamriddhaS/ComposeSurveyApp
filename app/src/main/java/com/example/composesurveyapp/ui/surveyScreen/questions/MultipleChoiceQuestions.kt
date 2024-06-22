package com.example.composesurveyapp.ui.surveyScreen.questions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MultipleChoiceQuestions(
    questionTitle:String,
    questionDescription:String,
    totalAnswers:List<Int>,
    selectedAnswers:List<Int>,
    onOptionSelected: (selected: Boolean, answer: Int) -> Unit,
    modifier: Modifier = Modifier
) {



}