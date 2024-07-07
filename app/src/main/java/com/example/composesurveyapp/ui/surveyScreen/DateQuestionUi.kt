package com.example.composesurveyapp.ui.surveyScreen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composesurveyapp.ui.theme.ComposeSurveyAppTheme
import com.example.composesurveyapp.ui.theme.slightlyDeemphasizedAlpha
import com.example.composesurveyapp.util.getDefaultDateInMillis
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


@Composable
fun QuestionWithDateUi(
    title:String,
    description:String,
    onClickedPickDate: () -> Unit,
    dateInMilli:Long?,
    modifier: Modifier = Modifier) {

    QuestionWrapperWithTitleAndDescription(
        questionTitle = title,
        questionDescription = description,
        modifier = modifier
    ) {

        // All times are stored in UTC, so generate the display from UTC also
        val dateFormat = SimpleDateFormat(simpleDateFormatPattern, Locale.getDefault())
        //dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val dateString = dateFormat.format(dateInMilli ?: getDefaultDateInMillis())

        Button(
            onClick = onClickedPickDate,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
                    .copy(alpha = slightlyDeemphasizedAlpha),
            ),
            modifier = Modifier
                .padding(18.dp)
                .height(48.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)),
            shape = MaterialTheme.shapes.small
        ) {

            Text(
                text = dateString,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.8f)
                )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            )

        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun QuestionWithDateUiPreview(){
    ComposeSurveyAppTheme {
        Surface {
            QuestionWithDateUi(
                title = "When was the last time you saw one of your fav anime episode",
                description = "Select a date",
                dateInMilli = null, // 2023-01-01
                onClickedPickDate = {},
            )
        }
    }
}