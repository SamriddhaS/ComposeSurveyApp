package com.example.composesurveyapp.ui.surveyScreen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composesurveyapp.ui.theme.slightlyDeemphasizedAlpha
import com.example.composesurveyapp.ui.theme.stronglyDeemphasizedAlpha


@Composable
fun QuestionWrapperWithTitleAndDescription(
    questionTitle:String,
    questionDescription:String?=null,
    modifier: Modifier = Modifier,
    customContent: @Composable () -> Unit
) {

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        QuestionTitle(titleString = questionTitle)

        questionDescription?.run {
            Spacer(modifier = Modifier.height(12.dp))
            QuestionDescription(this)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Customisable content which is a composable will be shown here
        // This section will be changing based on the question we are showing
        customContent()
    }

}

@Composable
fun QuestionTitle(
    titleString:String,
    modifier: Modifier = Modifier
) {

    Text(
        text = titleString,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = slightlyDeemphasizedAlpha),
        fontSize = 24.sp,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.inverseOnSurface,
                shape = MaterialTheme.shapes.small
            )

    )

}

@Composable
private fun QuestionDescription(
    descriptionString: String ,
    modifier: Modifier = Modifier,
) {
    Text(
        text = descriptionString,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha),
        fontSize = 14.sp,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
            .fillMaxWidth()
    )
}