package plantdiseaseidentifier.kirankumar.teesproject


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import plantdiseaseidentifier.kirankumar.teesproject.ui.theme.p1
import plantdiseaseidentifier.kirankumar.teesproject.ui.theme.p2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    name: String = "Kiran",
    place: String = "UK",
    email: String = "kiran@gmail.com",
    onLogout: () -> Unit
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = p1)
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(20.dp))

            // ---------------------------
            // Profile Image Placeholder
            // ---------------------------
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.take(1).uppercase(),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = p1
                )
            }

            Spacer(Modifier.height(20.dp))

            // ---------------------------------
            // Name
            // ---------------------------------
            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = p1
            )

            Spacer(Modifier.height(6.dp))

            // ---------------------------------
            // Place
            // ---------------------------------
            Text(
                text = place,
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(20.dp))

      
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    ProfileDetailItem(label = "Full Name", value = name)
                    Spacer(Modifier.height(14.dp))

                    ProfileDetailItem(label = "Location", value = place)
                    Spacer(Modifier.height(14.dp))

                    ProfileDetailItem(label = "Email Address", value = email)
                }
            }

            Spacer(Modifier.height(30.dp))

            // ---------------------------------
            // Logout Button
            // ---------------------------------
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(10.dp))
                Text("Logout", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun ProfileDetailItem(label: String, value: String) {
    Column {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(
            value,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
