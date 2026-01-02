package plantdiseaseidentifier.app.s3468594narrakiran


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.database.FirebaseDatabase
import plantdiseaseidentifier.app.s3468594narrakiran.data.AppRoutes
import plantdiseaseidentifier.app.s3468594narrakiran.data.CryptoUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var step2 by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(80.dp))

        // ---------------------------
        //  HEADER TITLE
        // ---------------------------
        Text(
            text = "Forgot Password",
            color = colorResource(id = R.color.p1),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Reset your password securely",
            color = colorResource(id = R.color.p1),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))


        // ---------------------------
        //  INPUT CARD UI
        // ---------------------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                // -------------------- STEP 1 : VERIFY --------------------
                if (!step2) {
                    Text("Email", color = colorResource(id = R.color.black))
                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter your registered email") }
                    )

                    Spacer(Modifier.height(16.dp))

                    Text("Date of Birth", color = colorResource(id = R.color.black))
                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = dob,
                        onValueChange = { dob = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("dd-mm-yyyy") }
                    )

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (email.isEmpty() || dob.isEmpty()) {
                                errorMessage = "Please fill all fields"
                                return@Button
                            }

                            loading = true
                            errorMessage = ""

                            val key = email.replace(".", ",")

                            FirebaseDatabase.getInstance().getReference("SignedUpUsers")
                                .child(key)
                                .get()
                                .addOnSuccessListener { snapshot ->
                                    loading = false

                                    if (!snapshot.exists()) {
                                        errorMessage = "User not found"
                                        return@addOnSuccessListener
                                    }

                                    val storedDob = snapshot.child("dob").value?.toString() ?: ""

                                    if (storedDob == dob) {
                                        step2 = true
                                    } else {
                                        errorMessage = "Email or DOB is incorrect"
                                    }
                                }
                                .addOnFailureListener {
                                    loading = false
                                    errorMessage = "Error: ${it.localizedMessage}"
                                }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.p2),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Verify")
                    }
                }


                // -------------------- STEP 2 : RESET PASSWORD --------------------
                if (step2) {
                    Text("New Password", color = colorResource(id = R.color.black))
                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter new password") }
                    )

                    Spacer(Modifier.height(16.dp))

                    Text("Confirm Password", color = colorResource(id = R.color.black))
                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Confirm new password") }
                    )

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (newPassword != confirmPassword) {
                                errorMessage = "Passwords do not match"
                                return@Button
                            }

                            loading = true
                            val key = email.replace(".", ",")

                            FirebaseDatabase.getInstance().getReference("SignedUpUsers")
                                .child(key)
                                .child("password")
                                .setValue(CryptoUtils.encrypt(newPassword))
                                .addOnSuccessListener {
                                    loading = false
                                    successMessage = "Password updated successfully!"

                                    navController.navigate(AppRoutes.Login.route) {
                                        popUpTo(AppRoutes.ForgotPassword.route) { inclusive = true }
                                    }
                                }
                                .addOnFailureListener {
                                    loading = false
                                    errorMessage = "Failed to update password"
                                }

                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.p2),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Update Password")
                    }
                }
            }
        }


        // STATUS MESSAGES
        Spacer(modifier = Modifier.height(20.dp))

        if (loading)
            Text("Processing...", color = colorResource(id = R.color.p1))

        if (errorMessage.isNotEmpty())
            Text(errorMessage, color = MaterialTheme.colorScheme.error)

        if (successMessage.isNotEmpty())
            Text(successMessage, color = colorResource(id = R.color.p3))
    }
}
