import java.io.File

fun isNumeric(text: String): Boolean =
        try {
            text.toDouble()
            true
        } catch(e: NumberFormatException) {
            false
        }

fun getElement(text: String) : String {
    when {
        // items to return as they are
        text == "true" || text == "false" || text == "null" || isNumeric(text) -> return text
        // strings must be returned between double quotes
        else -> return "\"$text\""
    }
}

fun main(args: Array<String>) {
    // get a list of files in the input directory
    val files = File("./input").listFiles()

    // walk through the list of files
    for (file in files) {
        // analyze only the CSV files
        if(file.path.endsWith((".csv"))) {
            // get the content of the file divided by lines
            val input: List<String> = File(file.path).readLines()

            // separate the header row from the rest of the content
            val lines = input.takeLast(input.count() - 1)
            val head: List<String> = input.first().split(",")

            var text = StringBuilder("[")

            for (line in lines) {
                // get the individual CSV elements; it's not perfect, but it works
                val values = line.split(",")

                text.appendln("{")
                // walk through the elements of the CSV line
                for (i in 0 until values.count()) {
                    // convert the element in the proper JSON string
                    val element = getElement(values[i].trim())
                    // write the element to the buffer
                    // pay attention to how we write head[i]
                    text.append("\t\"${head[i]}\": $element")

                    // append a comma, except for the last element
                    if(i != values.count() - 1)
                        text.appendln(",")
                    else
                        text.appendln()
                }

                text.append("},")
            }

            // remove the last comma
            text.deleteCharAt(text.length-1)
            // close the JSON array
            text.appendln("]")

            val newFile = file.path.replace(".csv",".json")
            File(newFile).writeText(text.toString())
        }
    }
}