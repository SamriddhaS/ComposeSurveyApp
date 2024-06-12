package com.example.composesurveyapp.ui.landingScreen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composesurveyapp.R
import com.example.composesurveyapp.ui.theme.ComposeSurveyAppTheme
import com.example.composesurveyapp.util.supportWideScreen

@Composable
fun LandingScreenRoute(
    onNavigateSignIn:(email:String) -> Unit,
    onSignAsGuest:() -> Unit
) {

    LandingScreenDesign(
        onSignIn = {  },
        onSignInAsGuest = { }
    )
}


@Composable
fun LandingScreenDesign(
    onSignIn:()->Unit,
    onSignInAsGuest:()->Unit
    ) {

    var showAppLogoSection by rememberSaveable { mutableStateOf(true) }


    Scaffold(modifier = Modifier.supportWideScreen()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            AnimatedVisibility(
                visible = showAppLogoSection
                ,modifier = Modifier.fillMaxWidth()) {

                LandingTopSection()

            }

        }
    }
    
}

@Composable
fun LandingTopSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically)) {
        AppLogo(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(horizontal = 76.dp)
        )
        Text(
            text = stringResource(id = R.string.app_tagline),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    lightTheme: Boolean = LocalContentColor.current.luminance() < 0.5f
) {

    val assetId = if (lightTheme) {
        R.drawable.ic_logo_light
    } else {
        R.drawable.ic_logo_dark
    }
    Image(
        painter = painterResource(id = assetId),
        modifier = modifier,
        contentDescription = null
    )

}

@Preview(name = "Welcome light theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Welcome dark theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun WelcomeScreenPreview() {
    ComposeSurveyAppTheme {
        LandingScreenDesign(
            onSignIn = {},
            onSignInAsGuest = {},
        )
    }
}