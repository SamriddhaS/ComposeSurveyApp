package com.example.composesurveyapp.ui.landingScreen

import android.content.res.Configuration
import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composesurveyapp.R
import com.example.composesurveyapp.ui.theme.ComposeSurveyAppTheme
import com.example.composesurveyapp.ui.theme.stronglyDeemphasizedAlpha
import com.example.composesurveyapp.util.supportWideScreen

@Composable
fun LandingScreenRoute(
    onNavigateSignIn:(email:String) -> Unit,
    onNavigateSignAsGuest:() -> Unit
) {

    val welcomeViewModel:LandingViewModel = viewModel(factory = WelcomeViewModelFactory())

    LandingScreenDesign(
        onSignIn = {  },
        onSignInAsGuest = {
            welcomeViewModel.signInAsGuest {
                onNavigateSignAsGuest()
            }
        }
    )
}


@Composable
fun LandingScreenDesign(
    onSignIn:(String)->Unit,
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

            LandingScreenBottomSection(
                onSignIn,
                onSignInAsGuest,
                onFocusChanged = { focoused -> showAppLogoSection = !focoused },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

        }
    }
    
}

@Composable
fun LandingScreenBottomSection(
    onSignIn: (email: String) -> Unit,
    onSignInAsGuest: () -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier
) {

    val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
        mutableStateOf(EmailState())
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Sign in or continue as guest",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 62.dp, bottom = 12.dp)
        )

        onFocusChanged(emailState.isFocused)

        val onSubmit = {
            if (emailState.isValid){
                onSignIn(emailState.text)
            }else{
                emailState.enableShowErrors()
            }
        }

        Email(emailState, imeAction = ImeAction.Done, onImeAction = onSubmit)

        Button(onClick = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 8.dp, end = 8.dp)
            )
        {
            Text(text = "Continue",style = MaterialTheme.typography.titleSmall)
        }

        Text(
            text = "Or",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
        )

        OutlinedButton(onClick = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 8.dp, end = 8.dp)
        )
        {
            Text(
                text = "Sign in as guest",
                style = MaterialTheme.typography.titleSmall
            )
        }


    }


}

@Composable
fun Email(
    textFieldState: TextFieldState = remember { EmailState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction:()->Unit = {}
    ) {

    OutlinedTextField(
        value = textFieldState.text ,
        onValueChange = {
            textFieldState.text = it
        },
        label = {
            Text(text = "Email", style = MaterialTheme.typography.bodyMedium)
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                textFieldState.onFocusChange(it.isFocused)
                if (!it.isFocused) {
                    textFieldState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = textFieldState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onDone = { onImeAction() }
        ),
        singleLine = true
    )
    
    textFieldState.getError()?.let {
        TextFieldError(it)
    }

}

@Composable
fun TextFieldError(message:String) {
    Row(Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(18.dp))
        Text(text = message,Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.error)
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