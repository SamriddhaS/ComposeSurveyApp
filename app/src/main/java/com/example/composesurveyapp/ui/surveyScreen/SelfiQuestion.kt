package com.example.composesurveyapp.ui.surveyScreen

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composesurveyapp.R
import com.example.composesurveyapp.ui.theme.ComposeSurveyAppTheme

@Composable
fun SelfiQuestion(
    question:String,
    questionDes:String,
    photoUri:Uri?,
    newPhotoUri:Uri?,
    onPhotoTaken:(Uri)->Unit,
    modifier: Modifier = Modifier) {

    val hasTakenPhoto = photoUri!=null

    var newImageUri:Uri? by remember {
        mutableStateOf(null)
    }

    val cameraLauncher = rememberLauncherForActivityResult (
        contract = ActivityResultContracts.TakePicture(),
        onResult = {isSuccess->
            if(isSuccess){
                onPhotoTaken(newImageUri!!)
            }
        }
    )

    QuestionWrapperWithTitleAndDescription(
        questionTitle = question,
        questionDescription = questionDes,
        modifier = modifier
        ) {

        OutlinedButton(
            onClick = {
                newImageUri = newPhotoUri
                cameraLauncher.launch(newImageUri)
            },
            shape = MaterialTheme.shapes.small,
            contentPadding = PaddingValues(),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if(hasTakenPhoto){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photoUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(76.dp)
                            .aspectRatio(4 / 3f)
                    )
                }else{
                    PhotoDefault()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.BottomCenter)
                        .padding(top = 36.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    val iconResource = if (hasTakenPhoto) {
                        R.drawable.camera_retake
                    } else {
                        R.drawable.icon_camera
                    }

                    Icon(
                        painter = painterResource(id=iconResource),
                        contentDescription = "",
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(text = if(hasTakenPhoto) "Retake Photo" else "Capture Photo" )
                }
            }

        }

    }
}


@Composable
fun PhotoDefault(
    modifier: Modifier = Modifier,
    lightTheme: Boolean = LocalContentColor.current.luminance() < 0.5f
) {
    val image = if (lightTheme){
        R.drawable.ic_selfie_light
    }else{
        R.drawable.ic_selfie_dark
    }
    Image(
        painter = painterResource(id=image),
        contentDescription = "",
        modifier = modifier
    )
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSelfiQuestion(modifier: Modifier = Modifier) {
    ComposeSurveyAppTheme {
        Surface {
            SelfiQuestion(
                question = "Take A photo with poster of your fav charecter.",
                questionDes = "",
                onPhotoTaken = {},
                photoUri = null,
                newPhotoUri = null
            )
        }
    }
}