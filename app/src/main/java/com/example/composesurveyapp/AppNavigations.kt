package com.example.composesurveyapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.composesurveyapp.Destinations.LANDING_PAGE_ROUTE
import com.example.composesurveyapp.Destinations.SIGN_IN_ROUTE
import com.example.composesurveyapp.Destinations.SURVEY_RESULT_ROUTE
import com.example.composesurveyapp.Destinations.SURVEY_SCREEN_ROUTE
import com.example.composesurveyapp.ui.landingScreen.LandingScreenRoute
import com.example.composesurveyapp.ui.surveyResult.SurveyResultRoute
import com.example.composesurveyapp.ui.surveyScreen.SurveyScreenRoute

object Destinations {
    const val LANDING_PAGE_ROUTE = "landingpage"
    const val SIGN_IN_ROUTE = "signin/{email}"
    const val SURVEY_SCREEN_ROUTE = "survey"
    const val SURVEY_RESULT_ROUTE = "surveyResult"
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    ) {

    NavHost(
        navController = navController,
        startDestination = LANDING_PAGE_ROUTE) {

        composable(LANDING_PAGE_ROUTE){
            LandingScreenRoute(
                onNavigateSignIn = {},
                onNavigateSignAsGuest = {
                    navController.navigate(SURVEY_SCREEN_ROUTE)
                }
            )
        }
        composable(SIGN_IN_ROUTE){

        }
        composable(SURVEY_SCREEN_ROUTE){
            SurveyScreenRoute(
                onSurveyComplete = { navController.navigate(SURVEY_RESULT_ROUTE) },
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable(SURVEY_RESULT_ROUTE){
            SurveyResultRoute(
                onTakeSurveyAgainClicked = { navController.popBackStack(LANDING_PAGE_ROUTE,true) },
                onShareWithOthers = {}
            )
        }
    }
}