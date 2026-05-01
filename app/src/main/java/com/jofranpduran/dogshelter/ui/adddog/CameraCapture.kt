package com.jofranpduran.dogshelter.ui.adddog

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.camera.core.AspectRatio
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.jofranpduran.dogshelter.ui.theme.DogShelterTheme
import java.io.File

@Composable
fun CameraCapture(
    onImageCaptured: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
            val resolutionSelector = ResolutionSelector.Builder()
                .setAspectRatioStrategy(
                    AspectRatioStrategy(
                        AspectRatio.RATIO_4_3,
                        AspectRatioStrategy.FALLBACK_RULE_AUTO
                    )
                ).build()

            imageCaptureResolutionSelector = resolutionSelector
            previewResolutionSelector = resolutionSelector
        }
    }

    val factory = remember(cameraController) {
        { ctx: Context ->
            PreviewView(ctx).apply {
                controller = cameraController
                cameraController.bindToLifecycle(lifecycleOwner)
            }
        }
    }

    val onButtonClick = remember(context) {
        {
            val file = File(context.cacheDir, "dog_${System.currentTimeMillis()}.jpg")
            val outputOptions =
                ImageCapture.OutputFileOptions.Builder(file).build()

            cameraController.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        onImageCaptured(file.path)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e("CameraCapture", "Error capturing image", exception)
                    }
                }
            )
        }
    }

    CameraCaptureContent(
        androidViewFactory = factory,
        onButtonClick = onButtonClick,
        onDismiss = onDismiss
    )

    BackHandler {
        onDismiss()
    }
}

@Composable
fun CameraCaptureContent(
    androidViewFactory: (Context) -> PreviewView,
    onButtonClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .align(Alignment.Center)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = androidViewFactory
            )
        }

        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(16.dp)
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close", tint = Color.White
            )
        }

        LargeFloatingActionButton(
            onClick = onButtonClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
            containerColor = Color.White,
            contentColor = Color.Black,
            shape = CircleShape
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = "Take photo"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CameraCapturePreview() {
    DogShelterTheme {
        CameraCaptureContent(
            androidViewFactory = { context: Context -> PreviewView(context) },
            onButtonClick = { },
            onDismiss = { }
        )
    }
}
