object TextMetrics {

    // for the theory behind this calculation
    // see http://iovs.arvojournals.org/article.aspx?articleid=2166061
    fun timeToRead(text: String) : Double =
            text.count { it.isLetterOrDigit() }.toDouble() / 987

    // Coleman–Liau index
    fun readability(text: String) : Double {
        val words = calculateWords(text).toDouble()
        val sentences = calculateSentences(text).toDouble()
        val letters = text.count { it.isLetterOrDigit() }.toDouble()

        // average number of letters per 100 words
        val l = letters / words * 100
        // average number of sentences per 100 words
        val s = sentences / words * 100

        val grade = 0.0588 * l - 0.296 * s - 15.8

        return if(grade > 0) grade else 0.0
    }

    private fun calculateSentences(text: String) : Int {
        var index = 0
        var sentences = 0
        while(index < text.length) {
            // we find the next period
            index = text.indexOf('.', index)
            // if there are no full stops, we end the cycle
            if (index == -1) index = text.length

            when {
                // if we have reached the end, we add a sentence
                // this ensures that there is at least 1 sentence
                index + 1 >= text.length -> sentences++
                // we need to check that we are not at the end of the text
                index + 1 < text.length
                        // and that the full stop is not part of an acronym (e.g. S.M.A.R.T.)
                        && index > 2
                        && !text[index - 2].isWhitespace() && text[index - 2] != '.'
                        // and that after the period there is a space
                        // (i.e., it is not a number, like 4.5)
                        && text[index + 1].isWhitespace()
                -> sentences++
            }

            index++
        }

        return sentences
    }

    private fun calculateWords(text:String) : Int {
        var words = 1
        var index = 1
        while(index < text.length) {
            if(text[index].isWhitespace()) {
                words++
                while(index + 1 < text.length && text[index + 1].isWhitespace()) index++
            }

            index++
        }

        return words
    }
}