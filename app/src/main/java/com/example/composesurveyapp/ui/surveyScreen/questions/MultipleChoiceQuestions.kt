package com.example.composesurveyapp.ui.surveyScreen.questions

import android.widget.CheckBox
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composesurveyapp.ui.surveyScreen.QuestionWrapperWithTitleAndDescription

@Composable
fun MultipleChoiceQuestions(
    questionTitle:String,
    questionDescription:String,
    totalAnswers:List<String>,
    selectedAnswers:List<String>,
    onOptionSelected: (selected: Boolean, answer: String) -> Unit,
    modifier: Modifier = Modifier
) {

    QuestionWrapperWithTitleAndDescription(
        questionTitle = questionTitle,
        questionDescription = questionDescription,
        modifier = modifier
    ){
        // We will add checkboxes for each of the answers that will be shown in the screen
        totalAnswers.forEach{
            val selected = selectedAnswers.contains(it)
            CheckBoxItem(
                modifier = Modifier.padding(vertical = 8.dp),
                text = it.toString(),
                selected = selected,
                onOptionSelected = { onOptionSelected(!selected, it) }
            )

        }

    }

}

@Composable
fun CheckBoxItem(
    text:String,
    selected:Boolean,
    onOptionSelected:()->Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        shape = MaterialTheme.shapes.small,
        color = if(selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = 3.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(onClick = onOptionSelected)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            Box(modifier = Modifier.padding(8.dp) ){
                Checkbox(checked = selected, onCheckedChange = null)
            }
        }
    }
}

@Preview
@Composable
fun MultipleChoiceQuestionPreview() {
    val possibleAnswers = listOf("Option 1","Option 2","option 3")
    val selectedAnswers = remember { mutableStateListOf("Option 2") }
    MultipleChoiceQuestions(
        questionTitle = "R.string.in_my_free_time",
        questionDescription = "This is the description Of the Question",
        totalAnswers = possibleAnswers,
        selectedAnswers = selectedAnswers,
        onOptionSelected = { _, _ -> }
    )
}













