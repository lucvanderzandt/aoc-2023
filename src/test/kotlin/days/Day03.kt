package days

import org.junit.jupiter.api.DisplayName
import utils.FileReader
import kotlin.test.Test

class Day03 {
    private val data: List<String> = FileReader.asStrings("day-03.txt")
    private val schematics = Schematics(data)


    @Test
    @DisplayName("Part 1")
    fun part1() {
        print("Answer part 1: " + schematics.partNumbers.sumOf { it.value.toInt() })
    }

    @Test
    @DisplayName("Part 2")
    fun part2() {
        val values: MutableList<Int> = mutableListOf()

        for(gear in schematics.gears) {
            val partNumbers = schematics.partNumbersOf(gear)
            values.add(partNumbers.first().value.toInt() * partNumbers.last().value.toInt())
        }

        print("Answer part 2: " + values.sum())
    }
}

class Schematics(data: List<String>) {
    private val lines: List<Line> = data.map { Line(it) }

    val partNumbers: List<Symbol>
        get() {
            val values: MutableList<Symbol> = mutableListOf()
            for(line in lines) {
                for(number in line.numbers) {
                    if(getAdjacentSymbols(number).isNotEmpty()) {
                        values.add(number)
                    }
                }
            }
            return values
        }

    val gears: List<Symbol>
        get() {
            val values: MutableList<Symbol> = mutableListOf()

            for(line in lines) {
                val asterisks = line.symbols.filter { it.value == "*" }
                for(a in asterisks) {
                    if (partNumbersOf(a).size == 2) {
                        values.add(a)
                    }
                }
            }

            return values
        }

    fun partNumbersOf(symbol: Symbol): List<Symbol> {
        return getAdjacentSymbols(symbol).filter { it.isNumeric }
    }

    private fun getAdjacentSymbols(symbol: Symbol): List<Symbol> {
        val symbols: MutableList<Symbol> = mutableListOf()
        symbols.addAll(above(symbol))
        symbols.addAll(below(symbol))

        val before = symbol.line.before(symbol)
        val after = symbol.line.after(symbol)
        if(before != null) symbols.add(before)
        if(after != null) symbols.add(after)

        return symbols
    }

    private fun above(symbol: Symbol): List<Symbol> {
        val currentIndex = lines.indexOf(symbol.line)
        if(currentIndex == 0) return emptyList()

        return lines[currentIndex - 1].symbolsInRange(IntRange(symbol.range.first - 1, symbol.range.last + 1))
    }

    private fun below(symbol: Symbol): List<Symbol> {
        val currentIndex = lines.indexOf(symbol.line)
        if(currentIndex == lines.size - 1) return emptyList()

        return lines[currentIndex + 1].symbolsInRange(IntRange(symbol.range.first - 1, symbol.range.last + 1))
    }
}

class Line(value: String) {
    val symbols: List<Symbol> = Regex("\\d+|[^0-9.]").findAll(value).map { Symbol(it.value, this, it.range) }.toList()
    val numbers: List<Symbol> = symbols.filter { it.isNumeric }

    fun symbolsInRange(range: IntRange): List<Symbol> {
        return symbols.filter { it.range.intersect(range).isNotEmpty() }
    }

    fun before(symbol: Symbol): Symbol? {
        return symbols.find { it.range.last == symbol.range.first - 1 }
    }

    fun after(symbol: Symbol) : Symbol? {
        return symbols.find { it.range.first == symbol.range.last + 1 }
    }
}

class Symbol(val value: String, val line: Line, val range: IntRange) {
    val isNumeric: Boolean
        get() = value.toIntOrNull() != null
}