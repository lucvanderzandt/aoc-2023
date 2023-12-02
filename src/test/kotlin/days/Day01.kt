package days

import org.junit.jupiter.api.DisplayName
import utils.FileReader
import kotlin.test.Test

class Day01 {
    private val data: List<String> = FileReader.asStrings("day-01.txt")
    private val digitMapping: Map<String, Int> = mapOf(
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
    @DisplayName("Part 1")
    fun part1() {
        val values: MutableList<Int> = mutableListOf()

        for (string in data) {
            val value = string.filter(Char::isDigit)
            values.add((value.first().toString() + value.last().toString()).toInt())
        }

        println("Answer part 1: " + values.sum())
    }

    @Test
    @DisplayName("Part 2")
    fun part2() {
        val values: MutableList<Int> = mutableListOf()

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

