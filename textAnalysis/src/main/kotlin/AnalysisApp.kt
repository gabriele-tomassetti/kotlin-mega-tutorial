import javafx.geometry.Pos
import tornadofx.*
import javafx.scene.text.Font

class MainController(): Controller() {
    fun getReadability(text: String) = when(TextMetrics.readability((text))) {
            in 0..6 -> "Easy"
            in 7..10 -> "Medium"
            else -> "Hard"
        }

    fun getTimeToRead(text: String): Int {
        val minutes = TextMetrics.timeToRead(text).toInt()

        return if (minutes > 0) minutes else 1
    }
}

class MainView: View() {
    val controller: MainController by inject()

    var timeToRead = text("")
    var readability = text("")
    var textarea = textarea("")

    override val root = vbox {
        prefWidth = 600.0
        prefHeight = 480.0
        alignment = Pos.CENTER
        text("Text Analysis") {
            font = Font(28.0)
            vboxConstraints {
                margin = insets(20.0)
            }
        }
        textarea = textarea("Write your text here") {
            selectAll()
            vboxConstraints {
                margin = insets(20.0)

            }
        }
        textarea.isWrapText = true
        hbox {
            vboxConstraints {
                alignment = Pos.BASELINE_CENTER
                marginBottom = 20.0
            }
            label("Time to Read") {
                hboxConstraints {
                    marginLeftRight(20.0)
                }
            }
            timeToRead = text("No text submitted")
            label("Readability") {
                hboxConstraints {
                    marginLeftRight(20.0)
                }
            }
            readability = text("No text submitted")
        }
        button("Analyze Text") {
            action {
                if(textarea.text.isNotEmpty()) {
                    readability.text = controller.getReadability(textarea.text)
                    timeToRead.text = "${controller.getTimeToRead(textarea.text)} minutes"
                }
            }
        }
    }
}