package plantdiseaseidentifier.kirankumar.teesproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import plantdiseaseidentifier.kirankumar.teesproject.data.AppRoutes


@Composable
fun HomeScreen(navController: NavHostController)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())

    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.p2))
                .padding(vertical = 6.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {

            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "",
                modifier = Modifier
                    .size(44.dp)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Column()
            {

                Text(
                    text = "Allen",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,

                    )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

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
            text = "Heal Your Crop",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 6.dp)
        )



        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
        )
        {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            )
            {

                Column(

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.take_picture),
                        contentDescription = "",
                        modifier = Modifier
                            .size(68.dp)

                    )
                    Text(
                        text = "Take a \npicture",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )

                }




                Image(
                    painter = painterResource(id = R.drawable.right_arrow),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)

                )

                Column(

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.result),
                        contentDescription = "",
                        modifier = Modifier
                            .size(68.dp)

                    )
                    Text(
                        text = "Get the \nresult",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )

                }


                Image(
                    painter = painterResource(id = R.drawable.right_arrow),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)

                )

                Column(

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.diagnosis),
                        contentDescription = "",
                        modifier = Modifier
                            .size(68.dp)

                    )
                    Text(
                        text = "Get \ndiagnosis",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )

                }
            }
            Spacer(modifier = Modifier.height(6.dp))
                Button(
                    onClick = { navController.navigate(AppRoutes.ScanPlant.route) },
                    shape = RoundedCornerShape(50),     // makes the button curved / pill shaped
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),   // background color
                        contentColor = Color.White            // text color
                    ),
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                ) {
                    Text(
                        text = "Take a picture",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            Spacer(modifier = Modifier.height(18.dp))

        }

        Spacer(modifier= Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = Color(0xFFEFEFEF),   // your background color
                        shape = RoundedCornerShape(12.dp) // optional rounded corners
                    )
                    .padding(12.dp) // inner padding so content doesn't touch edges
            )
            {

                Image(
                    painter = painterResource(id = R.drawable.saved_report),
                    contentDescription = "",
                    modifier = Modifier
                        .size(62.dp)
                )
                Text(
                    text = "Saved Report",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center

                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = Color(0xFFEFEFEF),   // your background color
                        shape = RoundedCornerShape(12.dp) // optional rounded corners
                    )
                    .padding(12.dp) // inner padding so content doesn't touch edges
            )
            {

                Image(
                    painter = painterResource(id = R.drawable.prevention_tips),
                    contentDescription = "",
                    modifier = Modifier
                        .size(62.dp)
                )
                Text(
                    text = "Prevention Tips",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center

                )


            }



        }







    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
   HomeScreen(navController = NavHostController(LocalContext.current))
}