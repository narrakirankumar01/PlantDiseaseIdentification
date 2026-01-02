package plantdiseaseidentifier.app.s3468594narrakiran

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import plantdiseaseidentifier.app.s3468594narrakiran.ui.theme.p1
import plantdiseaseidentifier.app.s3468594narrakiran.ui.theme.p2
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    navController: NavHostController,
    plant: String,
    plantConf: Float,
    disease: String,
    diseaseConf: Float,
    imageUriString: String
) {
    val decodedUri = URLDecoder.decode(imageUriString, StandardCharsets.UTF_8.toString())
    val diseaseConfidenceText = String.format("%.1f%%", diseaseConf * 100)

    val p1 = Color(0xFF76267b)
    val p2 = Color(0xFF50bf9e)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.scan_result), color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = p1),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ImagePreviewModern(decodedUri, p1, p2)

            Spacer(Modifier.height(24.dp))

            ModernResultCard(
                title = stringResource(R.string.disease_detected),
                value = disease.replace("___", " "),
                confidence = diseaseConfidenceText,
                primaryColor = p1,
                secondaryColor = p2,
                highlight = true
            )

            Spacer(Modifier.height(24.dp))

            Log.e("Test","${stringResource(R.string.disease_detected)} -${disease.replace("___", " ")}")

            ActionButtonsModern(
                p1 = p1,
                p2 = p2,
                navController = navController,
                plant = plant,
                disease = disease.replace("___", " "),
                confidence = diseaseConf,
                imageUri = decodedUri
            )
        }
    }
}


@Composable
fun ImagePreviewModern(uri: String, p1: Color, p2: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(p2.copy(alpha = 0.2f), Color.White)
                    )
                )
        ) {
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Leaf Preview",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
fun ModernResultCard(
    title: String,
    value: String,
    confidence: String,
    primaryColor: Color,
    secondaryColor: Color,
    highlight: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(Modifier.padding(20.dp)) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = primaryColor
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = if (highlight) primaryColor else secondaryColor
            )

            Spacer(Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .background(
                        color = if (highlight) p1.copy(alpha = 0.1f) else p2.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    "${stringResource(R.string.confidence)}: $confidence",
                    color = if (highlight) primaryColor else secondaryColor,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun ActionButtonsModern(
    p1: Color,
    p2: Color,
    navController: NavHostController,
    plant: String,
    disease: String,
    confidence: Float,
    imageUri: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        // ----------------------------------------------------
        // FIND REMEDY BUTTON
        // ----------------------------------------------------
        Button(
            onClick = {
                navController.navigate("remedy/$disease")
            },
            colors = ButtonDefaults.buttonColors(containerColor = p1),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(stringResource(R.string.find_remedy), color = Color.White)
        }

        // ----------------------------------------------------
        // SAVE REPORT BUTTON
        // ----------------------------------------------------
        Button(
            onClick = {
                val encodedUri = URLEncoder.encode(imageUri, StandardCharsets.UTF_8.toString())
                val encodedDisease = URLEncoder.encode(disease, StandardCharsets.UTF_8.toString())
                val encodedPlant = URLEncoder.encode(plant, StandardCharsets.UTF_8.toString())

                navController.navigate(
                    "save_report_screen/$encodedPlant/$encodedDisease/$confidence/$encodedUri"
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = p1),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(stringResource(R.string.save_report), color = Color.White)
        }


    }
}



