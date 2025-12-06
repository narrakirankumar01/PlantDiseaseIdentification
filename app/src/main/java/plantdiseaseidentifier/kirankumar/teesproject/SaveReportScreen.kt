package plantdiseaseidentifier.kirankumar.teesproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import plantdiseaseidentifier.kirankumar.teesproject.data.Report
import plantdiseaseidentifier.kirankumar.teesproject.data.SaveReportViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveReportScreen(
    navController: NavHostController,
    plant: String,
    disease: String,
    confidence: Float,
    imageUri: String,
    viewModel: SaveReportViewModel = viewModel()
) {
    val context = LocalContext.current
    val p1 = Color(0xFF76267b)
    val snackbarHostState = remember { SnackbarHostState() }

    val saveState by viewModel.saveState.collectAsState()

    // When saved → show message + navigate back
    LaunchedEffect(saveState) {
        if (saveState) {
            snackbarHostState.showSnackbar("Report Saved Successfully!")
            navController.popBackStack() // Go back to results
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Save Report", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = p1),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // Preview Image
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(20.dp))

            Text("Plant: $plant", fontSize = 18.sp)
            Text("Disease: $disease", fontSize = 18.sp)
            Text("Confidence: ${String.format("%.1f%%", confidence * 100)}", fontSize = 18.sp)

            Spacer(Modifier.height(20.dp))

            val notes = remember { mutableStateOf("") }

            OutlinedTextField(
                value = notes.value,
                onValueChange = { notes.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Notes (optional)") }
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    val report = Report(
                        plant = plant,
                        disease = disease,
                        confidence = confidence,
                        imageUri = imageUri,
                        notes = notes.value,
                        date = SimpleDateFormat(
                            "dd/MM/yyyy HH:mm",
                            Locale.getDefault()
                        ).format(Date())
                    )
                    viewModel.saveReport(report)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = p1),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Save Report", color = Color.White)
            }
        }
    }
}
