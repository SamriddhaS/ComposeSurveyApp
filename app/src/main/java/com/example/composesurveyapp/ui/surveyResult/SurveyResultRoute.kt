package com.example.composesurveyapp.ui.surveyResult

import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composesurveyapp.ui.theme.ComposeSurveyAppTheme
import com.example.composesurveyapp.util.supportWideScreen


@Composable
fun SurveyResultRoute(
    onTakeSurveyAgainClicked: () -> Unit,
    onShareWithOthers: () -> Unit,
) {

    BackHandler {
        onTakeSurveyAgainClicked()
    }

    Surface(modifier = Modifier.supportWideScreen()) {
        Scaffold(
            bottomBar = {
                BottomSectionUi(
                    onDoneClicked = onTakeSurveyAgainClicked,
                    onShareWithOthers = onShareWithOthers
                )
            },
            modifier = Modifier.navigationBarsPadding()
        ) {
            val modifier = Modifier.padding(it)
            MainContentUi(
                title = "Congratulations",
                subTitle = "Survey is complete",
                description = "Thank you for making time to complete our survey, your response has been recorded.",
                modifier
            )
        }
    }
}

@Composable
fun MainContentUi(
    title: String,
    subTitle: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
        ) {
            Spacer(modifier = Modifier.height(44.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = subTitle,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(20.dp).fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(20.dp).fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BottomSectionUi(
    onDoneClicked: () -> Unit,
    onShareWithOthers: () -> Unit,
) {
    Column {
        Button(
            onClick = onDoneClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(text = "Done")
        }
        OutlinedButton(
            onClick = onShareWithOthers,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Text(text = "Share With Others")
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable

private fun SurveyResultPreview() {
    ComposeSurveyAppTheme {
        SurveyResultRoute(onTakeSurveyAgainClicked = { }, onShareWithOthers = {})
    }
}