package days

import org.junit.jupiter.api.DisplayName
import utils.FileReader
import kotlin.properties.Delegates
import kotlin.test.Test

class Day02 {
    private val data: List<String> = FileReader.asStrings("day-02.txt")

    @Test
    @DisplayName("Part 1")
    fun part1() {
        val ids = mutableListOf<Int>()
        Game.bag = Grab(
            red = 12,
            green = 13,
            blue = 14,
        )

        for(line in data) {
            val game = Game.from(line)
            if(game.isPossible) ids.add(game.id)
        }

        println("Answer part 1: " + ids.sum())
    }

    @Test
    @DisplayName("Part 2")
    fun part2() {
        val powers = mutableListOf<Int>()
        for(line in data) {
            powers.add(Game.from(line).power)
        }

        println("Answer part 2: " + powers.sum())
    }
}

class Game(val id: Int = -1, val grabs: List<Grab>) {
    companion object {
        lateinit var bag: Grab

        fun from(source: String): Game {
            var id by Delegates.notNull<Int>()
            val grabs: MutableList<Grab> = mutableListOf()

            val src = source.split(":")
            id = src[0].filter(Char::isDigit).toInt()

            val grabSrc = src[1].split(";")
            for(grab in grabSrc) {
                grabs.add(Grab.from(grab))
            }

            return Game(
                id = id,
                grabs = grabs,
            )
        }
    }

    val isPossible: Boolean
        get() {
            for (grab in grabs) {
                if (grab.blue > bag.blue) {
                    return false
                } else if (grab.red > bag.red) {
                    return false
                } else if (grab.green > bag.green) {
                    return false
                }
            }
            return true
        }

    val power: Int
        get() {
            return minBlue * minRed * minGreen;
        }

    private val minBlue: Int
        get() {
            return grabs.maxOf { it.blue }
        }

    private val minRed: Int
        get() {
            return grabs.maxOf { it.red }
        }

    private val minGreen: Int
        get() {
            return grabs.maxOf { it.green }
        }
}

class Grab(val blue: Int, val red: Int, val green: Int) {
    companion object {
        fun from(source: String): Grab {
            var red = 0
            var blue = 0
            var green = 0

            val colors = source.split(",")

            for(color in colors) {
                val i = color.filter(Char::isDigit).toInt()
                if(color.contains("red")) {
                    red = i
                } else if(color.contains("blue")) {
                    blue = i
                } else if(color.contains("green")) {
                    green = i
                }
            }

            return Grab(
                blue = blue,
                red = red,
                green = green,
            )
        }
    }
}