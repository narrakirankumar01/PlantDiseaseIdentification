package plantdiseaseidentifier.kirankumar.teesproject.data



sealed class AppRoutes(val route: String) {
    object Splash : AppRoutes("splash_route")
    object Login : AppRoutes("login_route")
    object Home : AppRoutes("home_route")
    object Register : AppRoutes("register_route")
    object ScanPlant : AppRoutes("scan_plant")

}