package plantdiseaseidentifier.app.s3468594narrakiran


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.database.FirebaseDatabase
import plantdiseaseidentifier.app.s3468594narrakiran.data.AppRoutes
import plantdiseaseidentifier.app.s3468594narrakiran.data.CryptoUtils


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = NavHostController(LocalContext.current))
}


@Composable
fun LoginScreen(navController: NavHostController) {
    var accEmail by remember { mutableStateOf("") }
    var accPassword by remember { mutableStateOf("") }

    val context = LocalContext.current.findActivity()

    val context1 = LocalContext.current

    var passwordVisible by remember { mutableStateOf(false) }

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
                text = "Login",
                color = colorResource(id = R.color.p1),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Hello, Welcome Back!",
                color = colorResource(id = R.color.p1),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .align(Alignment.CenterHorizontally)
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
                    text = "EmailId",
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
                            accEmail.isEmpty() -> {
                                Toast.makeText(context, " Please Enter Mail", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            accPassword.isEmpty() -> {
                                Toast.makeText(
                                    context,
                                    " Please Enter Password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {

                                val database = FirebaseDatabase.getInstance()
                                val databaseReference = database.reference

                                val sanitizedEmail = accEmail.replace(".", ",")

                                databaseReference.child("SignedUpUsers").child(sanitizedEmail).get()
                                    .addOnSuccessListener { snapshot ->
                                        if (snapshot.exists()) {
                                            val chefData =
                                                snapshot.getValue(AccountDetails::class.java)
                                            chefData?.let {

                                                if (CryptoUtils.decrypt(it.password) == accPassword) {

                                                    UserPrefs.markLoginStatus(context1, true)
                                                    UserPrefs.saveEmail(
                                                        context1,
                                                        email = accEmail
                                                    )
                                                    UserPrefs.saveName(context1, it.name)
                                                    UserPrefs.savePlace(context1, it.place)
                                                    UserPrefs.saveDob(context1,it.dob)

                                                    Toast.makeText(
                                                        context,
                                                        "Login Successfull",
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                    navController.navigate(AppRoutes.Home.route) {
                                                        popUpTo(AppRoutes.Login.route) {
                                                            inclusive = true
                                                        }
                                                    }

                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "Incorrect Credentials",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "No User Found",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }.addOnFailureListener { exception ->
                                        println("Error retrieving data: ${exception.message}")
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

            Text(
                text = "Forgot Password?",
                color = colorResource(id = R.color.p3),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .clickable {
                        navController.navigate(AppRoutes.ForgotPassword.route)
                    }
            )


            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Not a member yet? ",
                    color = colorResource(id = R.color.p1),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = "Join now",
                    color = colorResource(id = R.color.p3),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black),
                    modifier = Modifier.clickable {

                        navController.navigate(AppRoutes.Register.route)

//                        context!!.startActivity(Intent(context, SignUpActivity::class.java))
//                        context.finish()
                    }
                )

            }

            Spacer(modifier = Modifier.height(24.dp))


        }
    }

}
