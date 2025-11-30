package plantdiseaseidentifier.kirankumar.teesproject

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.Surface
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PlantScanScreen(
    navController: NavHostController,
    onImageCaptured: (Uri) -> Unit = {}
) {
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        cameraPermission.launchPermissionRequest()
    }

    when (cameraPermission.status) {
        is PermissionStatus.Granted -> {
            CameraPreviewScreen(onImageCaptured)
        }

        is PermissionStatus.Denied -> {
            if ((cameraPermission.status as PermissionStatus.Denied).shouldShowRationale) {
                PermissionRationaleView {
                    cameraPermission.launchPermissionRequest()
                }
            } else {
                PermissionDeniedView {
                    cameraPermission.launchPermissionRequest()
                }
            }
        }
    }
}

@Composable
fun PermissionRationaleView(onRequest: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Camera permission is needed to scan the plant leaf.")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onRequest) {
            Text("Allow Camera")
        }
    }
}

@Composable
fun PermissionDeniedView(onRequest: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Camera permission denied. Please allow to continue.")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onRequest) {
            Text("Try Again")
        }
    }
}

@Composable
fun CameraPreviewScreen(
    onImageCaptured: (Uri) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val imageCapture = remember {
        ImageCapture.Builder()
            .setTargetRotation(Surface.ROTATION_0)
            .build()
    }

    val previewView = remember { PreviewView(context) }

    Box(modifier = Modifier.fillMaxSize()) {

        // Camera Preview
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { previewView }
        ) { view ->
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(view.surfaceProvider)

            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture
                    )
                } catch (e: Exception) {
                    Log.e("CameraX", "Binding Failed", e)
                }
            }, ContextCompat.getMainExecutor(context))
        }

        // --- 🔥 Scanning Animation Overlay ---
        ScanAnimationOverlay()

        // --- Capture Button ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            FloatingActionButton(
                onClick = {
                    captureImage(context, imageCapture, onImageCaptured)
                },
                modifier = Modifier.size(70.dp)
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Camera,
                    contentDescription = "Capture"
                )
            }
        }
    }
}

@Composable
fun ScanAnimationOverlay() {

    // Pulsing animation
    val infiniteTransition = rememberInfiniteTransition()
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Rotate ring animation
    val rotate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 140.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Scanning circle
        Box(
            modifier = Modifier
                .size((250 * pulse).dp)
                .align(Alignment.CenterHorizontally)
        ) {

            // Rotating ring
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        rotationZ = rotate
                    }
                    .border(
                        width = 3.dp,
                        color = Color.Green.copy(alpha = 0.7f),
                        shape = CircleShape
                    )
            )

            // Inner faded circle
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
                    .background(
                        color = Color.Green.copy(alpha = 0.15f),
                        shape = CircleShape
                    )
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Place the leaf in the center",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}


fun captureImage(
    context: Context,
    imageCapture: ImageCapture,
    onImageCaptured: (Uri) -> Unit
) {
    val outputDir = context.cacheDir
    val file = File(outputDir, "leaf_${System.currentTimeMillis()}.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e("CameraX", "Image capture failed", exc)
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val uri = Uri.fromFile(file)
                Log.d("CameraX", "Image Saved: $uri")
                onImageCaptured(uri)
            }
        }
    )
}



