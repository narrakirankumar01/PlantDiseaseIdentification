package plantdiseaseidentifier.kirankumar.teesproject

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import plantdiseaseidentifier.kirankumar.teesproject.data.PlantArticle
import plantdiseaseidentifier.kirankumar.teesproject.data.PlantArticlesProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    navController: NavHostController
) {
    val p1 = Color(0xFF76267b)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Prevention Tips", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = p1),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            items(PlantArticlesProvider.articles) { article ->
                ArticleItemCard(article) {
                    navController.navigate("article_detail/${article.id}")
                }
                Spacer(Modifier.height(14.dp))
            }
        }
    }
}


@Composable
fun ArticleItemCard(article: PlantArticle, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(article.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(article.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    navController: NavHostController,
    articleId: Int
) {
    val article = PlantArticlesProvider.articles.first { it.id == articleId }

    val p1 = Color(0xFF76267b)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(article.title, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = p1),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                article.content,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray,
                lineHeight = 22.sp
            )
        }
    }
}

