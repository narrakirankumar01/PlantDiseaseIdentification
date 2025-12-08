package plantdiseaseidentifier.kirankumar.teesproject

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import plantdiseaseidentifier.kirankumar.teesproject.mlhelper.DiseaseInfoProvider
import plantdiseaseidentifier.kirankumar.teesproject.ui.theme.p1
import plantdiseaseidentifier.kirankumar.teesproject.ui.theme.p2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemedyScreen(
    navController: NavHostController,
    diseaseName: String
) {
    val p1 = Color(0xFF76267b)
    val p2 = Color(0xFF50bf9e)

    val info = DiseaseInfoProvider.getInfo(diseaseName)


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(info.name, color = Color.White) },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Symptoms
            SectionHeader("Symptoms", p1)
            ModernSectionCard(info.symptoms, p1)

            Spacer(Modifier.height(20.dp))

            // General Management
            SectionHeader("General Management", p2)
            ModernSectionCard(info.management, p2)

            Spacer(Modifier.height(20.dp))

            // Chemical
            SectionHeader("Chemical Treatment", Color(0xFFD32F2F))
            ModernSectionCard(info.chemical, Color(0xFFD32F2F))

            Spacer(Modifier.height(20.dp))

            // Organic
            SectionHeader("Organic Treatment", Color(0xFF388E3C))
            ModernSectionCard(info.organic, Color(0xFF388E3C))

            Spacer(Modifier.height(32.dp))


        }
    }
}

@Composable
fun SectionHeader(title: String, color: Color) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = color
    )
    Spacer(Modifier.height(10.dp))
}




@Composable
fun ModernSectionCard(
    items: List<String>,
    bulletColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            items.forEach { point ->

                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(bulletColor, CircleShape)
                            .padding(top = 6.dp)
                    )

                    Spacer(Modifier.width(12.dp))

                    Text(
                        text = point,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

