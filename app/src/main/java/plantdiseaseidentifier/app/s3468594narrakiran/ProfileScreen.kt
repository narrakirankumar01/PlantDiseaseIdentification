package plantdiseaseidentifier.app.s3468594narrakiran


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import plantdiseaseidentifier.app.s3468594narrakiran.ui.theme.ThemePrefs
import plantdiseaseidentifier.app.s3468594narrakiran.ui.theme.p1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    name: String,
    place: String,
    email: String,
    dob: String,
    onLogout: () -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }

    var updatedName by remember { mutableStateOf(name) }
    var updatedPlace by remember { mutableStateOf(place) }
    var updatedDob by remember { mutableStateOf(dob) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.profile), color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
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

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = updatedName.take(1).uppercase(),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = p1
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(updatedName, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = p1)
            Text(updatedPlace, fontSize = 16.sp, color = Color.Gray)

            Spacer(Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(R.string.profile_details), fontWeight = FontWeight.Bold)
                        IconButton(onClick = { showEditDialog = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = p1)
                        }
                    }

                    Spacer(Modifier.height(14.dp))
                    ProfileDetailItem(stringResource(R.string.full_name), updatedName)
                    Spacer(Modifier.height(10.dp))
                    ProfileDetailItem(stringResource(R.string.place), updatedPlace)
                    Spacer(Modifier.height(10.dp))
                    ProfileDetailItem(stringResource(R.string.date_of_birth), updatedDob)
                    Spacer(Modifier.height(10.dp))
                    ProfileDetailItem(stringResource(R.string.email), email)
                }
            }

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Icon(Icons.Default.Logout, null, tint = Color.White)
                Spacer(Modifier.width(10.dp))
                Text(stringResource(R.string.logout), color = Color.White, fontSize = 18.sp)
            }

            Spacer(Modifier.height(16.dp))
            Divider()
            DarkModeToggle()
        }
    }

    // -------- EDIT PROFILE DIALOG ----------
    if (showEditDialog) {
        EditProfileDialog(
            name = updatedName,
            place = updatedPlace,
            dob = updatedDob,
            email = email,
            onDismiss = { showEditDialog = false },
            onSave = { n, p, d ->
                updatedName = n
                updatedPlace = p
                updatedDob = d
                showEditDialog = false
            }
        )
    }
}


@Composable
fun EditProfileDialog(
    name: String,
    place: String,
    dob: String,
    email: String,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    val context = LocalContext.current

    var newName by remember { mutableStateOf(name) }
    var newPlace by remember { mutableStateOf(place) }
    var newDob by remember { mutableStateOf(dob) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.edit_profile)) },
        text = {
            Column {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text(stringResource(R.string.name)) }
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = newPlace,
                    onValueChange = { newPlace = it },
                    label = { Text(stringResource(R.string.place)) }
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = newDob,
                    onValueChange = { newDob = it },
                    label = { Text(stringResource(R.string.date_of_birth)) }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    updateProfileInFirebase(
                        context = context,
                        email = email,
                        name = newName,
                        place = newPlace,
                        dob = newDob,
                        onSuccess = {
                            Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                            onSave(newName, newPlace, newDob)
                        },
                        onError = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            ) {
                Text(stringResource(R.string.save), color = p1)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}


fun updateProfileInFirebase(
    context: Context,
    email: String,
    name: String,
    place: String,
    dob: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val key = email.replace(".", ",")

    val updates = mapOf(
        "name" to name,
        "place" to place,
        "dob" to dob
    )

    FirebaseDatabase.getInstance()
        .getReference("SignedUpUsers")
        .child(key)
        .updateChildren(updates)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener {
            onError(it.localizedMessage ?: "Update failed")
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

@Composable
fun DarkModeToggle() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isDark by remember { mutableStateOf(ThemePrefs.isDark(context)) }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (isDark) Icons.Default.DarkMode else Icons.Default.LightMode,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.dark_mode),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Switch(
            checked = isDark,
            onCheckedChange = {
                scope.launch {
                    ThemePrefs.setDark(context, it)
                }
            }
        )
    }
}
