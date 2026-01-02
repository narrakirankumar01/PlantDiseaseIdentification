package plantdiseaseidentifier.app.s3468594narrakiran

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import plantdiseaseidentifier.app.s3468594narrakiran.data.AppRoutes


@Composable
fun HomeScreen(navController: NavHostController) {

    val userName = UserPrefs.getName(LocalContext.current)

    val colors = MaterialTheme.colorScheme

    Scaffold(
        containerColor = colors.background
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .verticalScroll(rememberScrollState())
        ) {

            // 🔝 Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.secondary)
                    .padding(vertical = 6.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {
                            navController.navigate(AppRoutes.Profile.route)
                        }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.headlineSmall,
                        color = colors.onSecondary,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = stringResource(R.string.welcome_back),
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.onSecondary
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                LanguageSelector()
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🌿 Banner Image
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
                    .height(200.dp),
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Plant",
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.heal_your_crop),
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 6.dp)
            )

            // 🔁 Process Card
            Card(
                modifier = Modifier.padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = colors.surface)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    StepItem(R.drawable.take_picture, stringResource(R.string.take_picture), colors)
                    ArrowIcon()
                    StepItem(R.drawable.result, stringResource(R.string.get_result), colors)
                    ArrowIcon()
                    StepItem(R.drawable.diagnosis, stringResource(R.string.get_diagnosis), colors)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Button(
                    onClick = { navController.navigate(AppRoutes.ScanPlant.route) },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = colors.onPrimary
                    ),
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                ) {
                    Text(
                        text = stringResource(R.string.take_picture_btn),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔽 Quick Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                HomeActionCard(
                    icon = R.drawable.saved_report,
                    title = stringResource(R.string.saved_report),
                    colors = colors,
                    modifier = Modifier.weight(1f),
                ) {
                    navController.navigate(AppRoutes.SavedReports.route)
                }

                Spacer(modifier = Modifier.width(6.dp))

                HomeActionCard(
                    icon = R.drawable.prevention_tips,
                    title = stringResource(R.string.prevention_tips),
                    colors = colors,
                    modifier = Modifier.weight(1f),
                ) {
                    navController.navigate(AppRoutes.Articles.route)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔽 Quick Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                HomeActionCard(
                    icon = R.drawable.ic_aboutus,
                    title = stringResource(R.string.about_us),
                    colors = colors,
                    modifier = Modifier.weight(1f),
                ) {
                    navController.navigate(AppRoutes.AboutUs.route)
                }

                Spacer(modifier = Modifier.width(6.dp))

                HomeActionCard(
                    icon = R.drawable.ic_contactus,
                    title = stringResource(R.string.contact_us),
                    colors = colors,
                    modifier = Modifier.weight(1f),
                ) {
                    navController.navigate(AppRoutes.ContactUs.route)
                }
            }
        }
    }
}


@Composable
fun StepItem(icon: Int, text: String, colors: ColorScheme) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(68.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ArrowIcon() {
    Image(
        painter = painterResource(id = R.drawable.right_arrow),
        contentDescription = null,
        modifier = Modifier.size(24.dp)
    )
}

@Composable
fun HomeActionCard(
    icon: Int,
    title: String,
    colors: ColorScheme,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { onClick() }
            .background(
                color = colors.surfaceVariant,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(62.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = colors.onSurface,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LanguageSelector() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    val languages = mapOf(
        "English" to "en",
        "Hindi" to "hi",
        "Telugu" to "te"
    )

    Column {

        Box {


            IconButton(onClick = {
                expanded = true
            }) {
                Icon(Icons.Filled.Translate, null, tint = Color.White)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                languages.forEach { (name, code) ->
                    DropdownMenuItem(
                        text = { Text(name) },
                        onClick = {
                            LanguagePrefs.saveLanguage(context, code)
                            expanded = false

                            // Restart app to apply language
                            (context as Activity).recreate()
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavHostController(LocalContext.current))
}