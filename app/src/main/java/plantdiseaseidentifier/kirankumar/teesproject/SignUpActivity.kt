package plantdiseaseidentifier.kirankumar.teesproject

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.database.FirebaseDatabase
import plantdiseaseidentifier.kirankumar.teesproject.data.AppRoutes
import plantdiseaseidentifier.kirankumar.teesproject.data.CryptoUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = NavHostController(LocalContext.current))
}


@Composable
fun SignUpScreen(navController: NavHostController) {
    var accName by remember { mutableStateOf("") }
    var accPlace by remember { mutableStateOf("") }

    var accEmail by remember { mutableStateOf("") }
    var accPassword by remember { mutableStateOf("") }

    val context = LocalContext.current.findActivity()

    val context1 = LocalContext.current

    var passwordVisible by remember { mutableStateOf(false) }


    var dobDate by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    fun openDatePicker(
        onSelect: (String) -> Unit,
        minDate: Long? = null,
        maxDate: Long? = null
    ) {
        val dp = DatePickerDialog(
            context1,
            { _, year, month, day ->
                val c = Calendar.getInstance().apply {
                    set(year, month, day)
                }
                onSelect(dateFormat.format(c.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Allow selecting date from minimum date
        minDate?.let { dp.datePicker.minDate = it }

        // Allow selecting date up to maximum date
        maxDate?.let { dp.datePicker.maxDate = it }

        dp.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white)),
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Register",
                color = colorResource(id = R.color.p1),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 4.dp).align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Hello, Welcome Back!",
                color = colorResource(id = R.color.p1),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 32.dp).align(Alignment.CenterHorizontally)
            )

            Column(
                modifier = Modifier
                    .background(
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.white),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(16.dp),

                )
            {

                Text(
                    text = "Full Name",
                    color = colorResource(id = R.color.black),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = accName,
                    onValueChange = { accName = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                DOBDateField(
                    label = "Date of Birth",
                    value = dobDate,
                    onClick = {
                        openDatePicker({ dobDate = it }, 1900)
                    }
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Place",
                    color = colorResource(id = R.color.black),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = accPlace,
                    onValueChange = { accPlace = it }
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Email",
                    color = colorResource(id = R.color.black),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = accEmail,
                    onValueChange = { accEmail = it }
                )
                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = "Password",
                    color = colorResource(id = R.color.black),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = accPassword,
                    onValueChange = { accPassword = it },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(36.dp))

                Button(
                    onClick = {
                        when {

                            accName.isEmpty() -> {
                                Toast.makeText(context, " Please Enter Name", Toast.LENGTH_SHORT).show()
                            }

                            dobDate.isEmpty() ->{
                                Toast.makeText(context, " Please Select DOB", Toast.LENGTH_SHORT).show()
                            }

                            accPlace.isEmpty() -> {
                                Toast.makeText(context, " Please Enter Place", Toast.LENGTH_SHORT).show()
                            }

                            accEmail.isEmpty() -> {
                                Toast.makeText(context, " Please Enter Mail", Toast.LENGTH_SHORT).show()
                            }

                            accPassword.isEmpty() -> {
                                Toast.makeText(context, " Please Enter Password", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            else -> {

                                val userData = AccountDetails(
                                    name = accName,
                                    dob = dobDate,
                                    email = accEmail,
                                    place = accPlace,
                                    password = CryptoUtils.encrypt(accPassword)
                                )


                                val db = FirebaseDatabase.getInstance()
                                val ref = db.getReference("SignedUpUsers")
                                ref.child(userData.email.replace(".", ",")).setValue(userData)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()


                                            navController.navigate(AppRoutes.Login.route) {
                                                popUpTo(AppRoutes.Register.route) { inclusive = true }
                                            }
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "User Registration Failed: ${task.exception?.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(
                                            context,
                                            "User Registration Failed: ${exception.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                            }

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.p2),
                        contentColor = colorResource(id = R.color.white)
                    )
                ) {
                    Text("Continue")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Already a member? ",
                    color = colorResource(id = R.color.p1),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = "Login now",
                    color = colorResource(id = R.color.p3),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black),
                    modifier = Modifier.clickable {
                        navController.navigate(AppRoutes.Login.route) {
                            popUpTo(AppRoutes.Register.route) { inclusive = true }
                        }
                    }
                )

            }

            Spacer(modifier = Modifier.height(24.dp))


        }
    }

}


@Composable
fun DOBDateField(label: String, value: String, onClick: () -> Unit) {
    Column {
        Text(label, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(6.dp))

        Box {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select $label") },
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                Modifier
                    .matchParentSize()
                    .background(Color.Transparent)
                    .clickable { onClick() }
            )
        }
    }
}

data class AccountDetails
    (
    var name: String = "",
    val dob: String = "",
    var place: String ="",
    var email: String ="",
    var password: String ="",
)