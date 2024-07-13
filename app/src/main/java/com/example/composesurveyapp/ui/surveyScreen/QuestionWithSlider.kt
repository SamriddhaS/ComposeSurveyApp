package com.example.composesurveyapp.ui.surveyScreen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composesurveyapp.ui.theme.ComposeSurveyAppTheme

@Composable
fun SliderQuestion(
    question:String,
    questionDesc:String,
    startingText:String,
    middleText:String,
    endText:String,
    sliderPosition:Float?,
    onSliderPositionChanged:(Float)->Unit,
    valueRange:ClosedFloatingPointRange<Float> = 0f..1f,
    steps:Int=1,
    modifier: Modifier = Modifier
) {

    var pos by remember {
        mutableStateOf(sliderPosition?:((valueRange.endInclusive-valueRange.start)/2))
    }

    QuestionWrapperWithTitleAndDescription(
        questionTitle = question,
        questionDescription = questionDesc,
        modifier = modifier
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

        }
        Row {
            Slider(value = pos,
                onValueChange = {
                    pos = it
                    onSliderPositionChanged(it)
                },
                valueRange = valueRange,
                steps = steps,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
            )
        }

        Row {
            Text(
                text = startingText,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)

            )
            Text(
                text = middleText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Text(
                text = endText,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)

            )
        }

    }
}
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSliderQuestion(modifier: Modifier = Modifier) {

    ComposeSurveyAppTheme {
        Surface {
            SliderQuestion(
                question = "Whats the rating?",
                questionDesc = "Select from slider",
                valueRange = 0F..10F,
                startingText = "Poor",
                middleText = "Fair",
                endText = "Outstanding",
                sliderPosition = null,
                onSliderPositionChanged = {},
                steps = 1
            )
        }
    }
}