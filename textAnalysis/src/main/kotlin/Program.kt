import javafx.application.Application
import tornadofx.App

class AnalysisApp: App(MainView::class)

fun main(args: Array<String>) {
    Application.launch(AnalysisApp::class.java, *args)
}