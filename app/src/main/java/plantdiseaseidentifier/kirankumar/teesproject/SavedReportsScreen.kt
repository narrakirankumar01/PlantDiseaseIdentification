package plantdiseaseidentifier.kirankumar.teesproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import plantdiseaseidentifier.kirankumar.teesproject.data.Report
import plantdiseaseidentifier.kirankumar.teesproject.data.ReportListViewModel
import plantdiseaseidentifier.kirankumar.teesproject.ui.theme.p1
import plantdiseaseidentifier.kirankumar.teesproject.ui.theme.p2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedReportsScreen(
    navController: NavHostController,
    viewModel: ReportListViewModel = viewModel()
) {

    val reports by viewModel.reports.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Saved Reports", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = p1),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->

        if (reports.isEmpty()) {
            EmptyReportsView()
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(reports) { report ->
                    ReportItemCard(
                        report = report,
                        p1 = p1,
                        p2 = p2,
                        onDelete = { viewModel.deleteReport(it) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}


@Composable
fun EmptyReportsView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Info,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(80.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text("No reports found", fontSize = 18.sp, color = Color.Gray)
        Text("Scan a plant leaf and save reports.", color = Color.Gray)
    }
}

@Composable
fun ReportItemCard(
    report: Report,
    p1: Color,
    p2: Color,
    onDelete: (Report) -> Unit
) {

    var showConfirm by remember { mutableStateOf(false) }

    if (showConfirm) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showConfirm = false },
            title = { Text("Delete Report?") },
            text = { Text("Are you sure you want to delete this report?") },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = {
                    showConfirm = false
                    onDelete(report)
                }) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(onClick = { showConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Row(modifier = Modifier.fillMaxSize()) {

            // ----- Left Image -----
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(160.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(report.imageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // ----- Right Side -----
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight()
                    .weight(1f)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        report.disease,
                        fontSize = 18.sp,
                        color = p1,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = { showConfirm = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red
                        )
                    }
                }

                Spacer(Modifier.height(4.dp))

                Text("Plant: ${report.plant}", color = Color.DarkGray)
                Text(
                    "Confidence: ${String.format("%.1f%%", report.confidence * 100)}",
                    color = p2,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(6.dp))
                Text("Date: ${report.date}", fontSize = 13.sp, color = Color.Gray)
            }
        }
    }
}

