sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("settings") // ✅ Ensure this exists
    object Help : Screen("help") // ✅ Ensure this exists
    object Donate : Screen("donate")
    object Location : Screen("location")
    object Chat : Screen("chat")
    object Safety : Screen("safety")
}
