package days

import utils.FileReader
import kotlin.test.Test

class Day02 {
    private val data: List<String> = FileReader.asStrings("day-02.txt")

    @Test
    fun `Part 1`() {
        Game.bag = Grab(
            red = 12,
            green = 13,
            blue = 14
        )

        val ids: List<Int> = data.mapNotNull { line ->
            Game.from(line).takeIf { it.isPossible }?.id
        }

        println("Answer part 1: " + ids.sum())
    }

    @Test
    fun `Part 2`() {
        val powers = data.map { Game.from(it).power }

        println("Answer part 2: " + powers.sum())
    }
}

class Game(val id: Int = -1, val grabs: List<Grab>) {
    companion object {
        lateinit var bag: Grab

        fun from(source: String): Game {
            val src = source.split(":")
            val id = src[0].filter(Char::isDigit).toInt()
            val grabs = src[1].split(";").map { Grab.from(it) }

            return Game(
                id = id,
                grabs = grabs,
            )
        }
    }

    val isPossible get() = grabs.all { it.blue <= bag.blue && it.red <= bag.red && it.green <= bag.green }

    val power get() = minBlue * minRed * minGreen

    private val minBlue get() = grabs.maxOf { it.blue }

    private val minRed get() = grabs.maxOf { it.red }

    private val minGreen get() = grabs.maxOf { it.green }
}

class Grab(val blue: Int, val red: Int, val green: Int) {
    companion object {
        fun from(source: String): Grab {
            val colorMap = source
                .split(",")
                .map { it.trim() }
                .associate { color ->
                    val value = color.filter(Char::isDigit).toInt()
                    val type = when {
                        color.contains("red") -> "red"
                        color.contains("blue") -> "blue"
                        color.contains("green") -> "green"
                        else -> throw IllegalArgumentException("Unknown color type in: $color")
                    }
                    type to value
                }

            return Grab(
                blue = colorMap["blue"] ?: 0,
                red = colorMap["red"] ?: 0,
                green = colorMap["green"] ?: 0
            )
        }
    }
}