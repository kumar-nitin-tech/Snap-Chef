package com.example.snap_chef.presentation.home.camera

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.snap_chef.R
import com.example.snap_chef.presentation.home.camera.viewmodel.CameraViewModel
import com.example.snap_chef.presentation.home.recipe.viewmodel.ImageViewModel
import com.example.snap_chef.presentation.navigation.Routes

@Composable
fun CameraScreen(
    navController: NavHostController,
    imageViewModel: ImageViewModel
){
    val cameraViewModel = viewModel<CameraViewModel>()
    val bitmaps by cameraViewModel.bitmaps.collectAsState()
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (bitmaps == null) {
            //Camera Preview
            CameraPreview(
                controller = controller ,
                modifier = Modifier.fillMaxSize()
            )
            //camera switch
            IconButton(
                onClick = {
                    controller.cameraSelector =
                        if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else {
                            CameraSelector.DEFAULT_BACK_CAMERA
                        }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp , end = 24.dp)
                ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_cameraswitch_24) ,
                    contentDescription = "Switch Camera",
                    )
            }
            //Capture Button

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            ) {
                IconButton(onClick = {
                    takePhoto(
                        controller ,
                        onPhotoTaken = cameraViewModel::onTakePhoto ,
                        context

                    )

                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.camera) ,
                        contentDescription = "Camera click" ,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        } else {
            Image(
                bitmap =  bitmaps!!.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(90f) ,
                contentScale = ContentScale.Fit

            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        imageViewModel.setImageBitmap(bitmaps!!)
                        navController.navigate(Routes.RecipeScreen.routes){
                            popUpTo(Routes.CameraScreen.routes){
                                inclusive = true
                            }
                        }
                    } ,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Confirm")
                }
                Button(
                    onClick = {
                        cameraViewModel.clearCapturedImage()
                    }
                ) {
                    Text("Retake")
                }
            }
        }

    }

}

private fun takePhoto(
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit,
    context: Context,

){
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                onPhotoTaken(image.toBitmap())

            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo")
            }
        }
    )
}



@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun PreviewCameraScreen(){
    CameraScreen(rememberNavController(), viewModel())
}