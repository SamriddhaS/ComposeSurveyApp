package com.example.composesurveyapp.ui.surveyScreen

import android.app.FragmentManager
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composesurveyapp.ui.theme.ComposeSurveyAppTheme
import com.example.composesurveyapp.ui.theme.stronglyDeemphasizedAlpha
import com.example.composesurveyapp.util.PhotoUriManager
import com.example.composesurveyapp.util.supportWideScreen
import com.google.android.material.datepicker.MaterialDatePicker

private const val CONTENT_ANIMATION_DURATION = 500

@Composable
fun SurveyScreenRoute(
    onSurveyComplete: () -> Unit,
    onBackPressed: () -> Unit
) {

    val context = LocalContext.current
    val viewModel: SurveyViewModel = viewModel(factory = SurveyViewModelFactory(PhotoUriManager(context)))

    val surveyScreenData = viewModel.surveyScreenData ?: return

    BackHandler {
        if (!viewModel.onBackPressed()){
            onBackPressed()
        }
    }

    SurveyQuestionScreen(
        surveyData = surveyScreenData,
        isNextEnable = viewModel.isNextEnabled,
        onClosePressed = { onBackPressed() },
        onPreviousPressed = { viewModel.onPreviousPressed() },
        onNextPressed = { viewModel.onNextPressed() },
        onDonePressed = { viewModel.onDonePressed(onSurveyComplete) },
    ){ paddingValue ->

        val modifier = Modifier.padding(paddingValue)

        AnimatedContent(
            targetState = surveyScreenData,
            transitionSpec = {
                val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)
                val direction = getTransitionDirection(
                    initialIndex = initialState.questionIndex,
                    targetIndex = targetState.questionIndex
                )
                slideIntoContainer(
                    towards = direction,
                    animationSpec = animationSpec,
                ) togetherWith slideOutOfContainer(
                    towards = direction,
                    animationSpec = animationSpec
                )
            },
            label = "surveyScreenDataAnimation"
            ) {targetState ->

            when(targetState.surveyQuestion){
                SurveyQuestion.FAV_ANIME -> {
                    QuestionFavAnimeName(
                        selectedAnswers = viewModel.freeTimeResponse,
                        onClickedAnAnswer = { selected,answer -> viewModel.onFreeTimeResponse(selected, answer) },
                        modifier = modifier
                    )
                }
                SurveyQuestion.SUPERHERO -> {
                    SecondQuestion(
                        selectedAnswer = viewModel.favAnimeResponse,
                        onOptionSelected = { favAnime -> viewModel.onSecondQuestionResponse(favAnime) },
                        modifier = modifier
                    )
                }
                SurveyQuestion.LAST_TAKEAWAY -> {
                    val supportFragmentManager =
                        LocalContext.current.findActivity().supportFragmentManager

                    ThirdQuestion(
                        selectedDate = viewModel.takeawayResponse,
                        onClickedPickDate = {
                            showDatePicker(
                                currentSelected = viewModel.takeawayResponse,
                                supportFragmentManager =supportFragmentManager,
                                onDateSelected = {
                                    viewModel.onLastWatchedEpisodeDateResponse(it)
                                }
                            )
                        },
                        modifier = modifier
                    )
                }
                SurveyQuestion.FEELING_ABOUT_SELFIES -> {
                    FourthQuestion(
                        currentPos = viewModel.lastWatchedAnimeRating,
                        onSliderPosChanged = viewModel::onLastWatchedAnimeRatingResponse,
                        modifier = modifier
                    )
                }
                SurveyQuestion.TAKE_SELFIE -> {
                    FifthQuestion(
                        imageUri = viewModel.selfieUri,
                        getNewImageUri = viewModel.getNewSelfieUri(),
                        onPhotoTaken = viewModel::onSelfieResponse,
                        modifier = modifier
                    )
                }
            }
        }


    }

}

@Composable
fun SurveyQuestionScreen(
    surveyData: SurveyScreenData?,
    isNextEnable: Boolean,
    onClosePressed: () -> Unit,
    onNextPressed: () -> Unit,
    onPreviousPressed: () -> Unit,
    onDonePressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {

    Surface(Modifier.supportWideScreen()) {
        Scaffold(
            topBar = {
                SurveyTopAppBar(
                    currentQuestionIndex = surveyData?.questionIndex?:0,
                    totalQuestionCount = surveyData?.questionCount?:10,
                    onClosePressed = onClosePressed
                )
                     },
            content = content,
            bottomBar = {
                SurveyBottomBar(
                    shouldShowPreviousButton = surveyData?.shouldShowPreviousButton ?: true,
                    shouldEnableNextButton = isNextEnable,
                    shouldShowDoneButton = surveyData?.shouldShowDoneButton ?: false,
                    onPreviousPressed = { onPreviousPressed() },
                    onNextPressed = { onNextPressed() },
                    onDonePressed = { onDonePressed() }
                )
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class) // CenterAlignedTopAppBar is experimental in m3
@Composable
fun SurveyTopAppBar(
    currentQuestionIndex: Int,
    totalQuestionCount: Int,
    onClosePressed: () -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        CenterAlignedTopAppBar(
            title = {
                TopAppBarTitle(
                    questionIndex = currentQuestionIndex,
                    totalQuestionsCount = totalQuestionCount
                )
            },
            actions = {
                IconButton(
                    onClick = onClosePressed,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface.copy(stronglyDeemphasizedAlpha)
                    )
                }
            }
        )

        val animatedProgress by animateFloatAsState(
            targetValue = (currentQuestionIndex + 1) / totalQuestionCount.toFloat(),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )

        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        )

    }
}

@Composable
fun TopAppBarTitle(
    questionIndex: Int,
    totalQuestionsCount: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = (questionIndex + 1).toString(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha)
        )
        Text(
            text = "of $totalQuestionsCount",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    }
}

@Composable
fun SurveyBottomBar(
    shouldShowPreviousButton:Boolean,
    shouldEnableNextButton:Boolean,
    shouldShowDoneButton:Boolean,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit
) {

    Surface(shadowElevation = 8.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom))
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {

            /*
            * Our Previous Button
            * */
            if(shouldShowPreviousButton){
                OutlinedButton(
                    onClick = onPreviousPressed,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text(text = "Previous")
                }
                Spacer(modifier = Modifier.width(18.dp))
            }

            if(shouldShowDoneButton){
                Button(
                    onClick = onDonePressed,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text(text = "Done")
                }
            }else{
                Button(
                    onClick = onNextPressed,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    enabled = shouldEnableNextButton
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}

private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentTransitionScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        AnimatedContentTransitionScope.SlideDirection.Right
    }
}

fun showDatePicker(
    currentSelected:Long?,
    supportFragmentManager: androidx.fragment.app.FragmentManager,
    onDateSelected:(Long)->Unit
) {

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setSelection(currentSelected)
        .build()

    datePicker.show(supportFragmentManager,datePicker.toString())
    datePicker.addOnPositiveButtonClickListener {
        datePicker.selection?.let {
            onDateSelected(it)
        }
    }
}

@Preview(name = "Welcome light theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Welcome dark theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun WelcomeScreenPreview() {
    ComposeSurveyAppTheme {
        SurveyQuestionScreen(
            surveyData = null,
            isNextEnable = false,
            onClosePressed = { /*TODO*/ },
            onNextPressed = { /*TODO*/ },
            onPreviousPressed = { /*TODO*/ },
            onDonePressed = { /*TODO*/ }) {

        }
    }
}

private tailrec fun Context.findActivity(): AppCompatActivity =
    when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> this.baseContext.findActivity()
        else -> throw IllegalArgumentException("Could not find activity!")
    }