package plantdiseaseidentifier.app.s3468594narrakiran.data



sealed class AppRoutes(val route: String) {
    object Splash : AppRoutes("splash_route")
    object Login : AppRoutes("login_route")
    object Home : AppRoutes("home_route")
    object Register : AppRoutes("register_route")
    object ScanPlant : AppRoutes("scan_plant")

    object AboutUs : AppRoutes("aboutus")
    object ContactUs : AppRoutes("contactus")

    object SavedReports : AppRoutes("saved_reports")
    object Articles : AppRoutes("prevention_tips")
    object Profile : AppRoutes("user_profile")
    object ForgotPassword : AppRoutes("forgot_password")

}