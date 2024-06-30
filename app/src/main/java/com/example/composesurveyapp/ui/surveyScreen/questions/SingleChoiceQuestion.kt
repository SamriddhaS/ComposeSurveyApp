package com.example.composesurveyapp.ui.surveyScreen.questions

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composesurveyapp.R
import com.example.composesurveyapp.ui.surveyScreen.QuestionWrapperWithTitleAndDescription
import com.example.composesurveyapp.ui.surveyScreen.model.FavAnime

@Composable
fun SingleChoiceQuestion(
    questionTitle:String,
    questionDescription:String,
    totalAnswers:List<FavAnime>,
    selectedAnswer:FavAnime?,
    onOptionSelected: (answer: FavAnime) -> Unit,
    modifier: Modifier = Modifier
) {

    QuestionWrapperWithTitleAndDescription(
        questionTitle = questionTitle,
        questionDescription = questionDescription,
        modifier = modifier.selectableGroup()
    ) {

        totalAnswers.forEach {
            val selected = it == selectedAnswer
            RadioButtonWithImageItem(
                modifier = Modifier.padding(vertical = 8.dp),
                text = it.name,
                imageResourceId = it.imageResourceId,
                onOptionSelected = { onOptionSelected(it) },
                selected = selected
            )
        }

    }
}

@Composable
fun RadioButtonWithImageItem(
    text: String,
    @DrawableRes imageResourceId: Int,
    onOptionSelected: () -> Unit,
    selected : Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        border = BorderStroke(
            width = 5.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .selectable(
                selected,
                onClick = onOptionSelected,
                role = Role.RadioButton
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = imageResourceId),
                contentDescription = "",
                modifier = Modifier
                    .size(56.dp)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .padding(start = 0.dp, end = 8.dp)
            )

            Spacer(Modifier.width(8.dp))

            Text(text, Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)

            Box(Modifier.padding(8.dp)){
                RadioButton(selected = selected, onClick = null)
            }

        }
    }

}

@Preview
@Composable
private fun SingleChoiceQuestionPreview() {
    val possibleAnswers = listOf(
        FavAnime("Saitama", R.drawable.saitama),
        FavAnime("Naruto", R.drawable.naruto),
        FavAnime("Minato", R.drawable.minato),
        FavAnime("Levai", R.drawable.levai)
    )

    var selectedAnswer by remember { mutableStateOf<FavAnime?>(null) }

    SingleChoiceQuestion(
        questionTitle = "Select single choice question...",
        questionDescription = "You can choose only one type of question.",
        totalAnswers = possibleAnswers,
        selectedAnswer = selectedAnswer,
        onOptionSelected = { selectedAnswer = it },
    )
}