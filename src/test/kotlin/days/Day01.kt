package days

import utils.FileReader
import kotlin.test.Test

class Day01 {
    private val data = FileReader.asStrings("day-01.txt")
    private val digitMapping = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
    )

    @Test
    fun `Part 1`() {
        val values = data.map {string ->
            string.filter(Char::isDigit)
                .let {
                    (it.first().toString() + it.last().toString()).toInt()
                }
        }

        println("Answer part 1: " + values.sum())
    }

    @Test
    fun `Part 2`() {
        val values = mutableListOf<Int>()

        for (string in data) {
            var first: Int? = null
            var firstDigit: Int? = null
            var last: Int? = null
            var lastDigit: Int? = null

            for (digit in digitMapping.keys) {
                var i = string.indexOf(digit)

                while(i >= 0) {
                    if (first == null || i < first) {
                        first = i
                        firstDigit = digitMapping[digit]
                    }

                    if (last == null || i > last) {
                        last = i
                        lastDigit = digitMapping[digit]
                    }

                    i = string.indexOf(digit, i + 1)
                }
            }

            values.add((firstDigit.toString() + lastDigit.toString()).toInt())
        }

        println("Answer part 2: " + values.sum())
    }
}

