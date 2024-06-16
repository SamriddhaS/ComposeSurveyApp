package com.example.composesurveyapp.ui.surveyScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SurveyScreenRoute(
    onSurveyComplete:()->Unit,
    onBackPressed:()->Unit
) {


}

@Composable
fun SurveyQuestionScreen(
    surveyData: SurveyScreenData,
    isNextEnable:Boolean,
    onClosePressed:()->Unit,
    onNextPressed:()->Unit,
    onPreviousPressed:()->Unit,
    onDonePressed:()->Unit,
    content: @Composable (PaddingValues) -> Unit
    )
{


}