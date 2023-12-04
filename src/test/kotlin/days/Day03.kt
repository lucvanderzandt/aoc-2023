package days

import utils.FileReader
import kotlin.test.Test

class Day03 {
    private val data = FileReader.asStrings("day-03.txt")
    private val schematics = Schematics(data)

    @Test
    fun `Part 1`() {
        print("Answer part 1: " + schematics.partNumbers.sumOf { it.value.toInt() })
    }

    @Test
    fun `Part 2`() {
        val values = schematics.gears
            .map { gear -> schematics.partNumbersOf(gear) }
            .map { partNumbers -> partNumbers.first().value.toInt() * partNumbers.last().value.toInt() }

        print("Answer part 2: " + values.sum())
    }
}

class Schematics(data: List<String>) {
    private val lines = data.map { Line(it) }

    val partNumbers get() = lines.flatMap { line ->
            line.numbers.filter { number -> getAdjacentSymbols(number).isNotEmpty() }
        }

    val gears get() = lines.flatMap { line ->
            line.symbols.filter { it.value == "*" && partNumbersOf(it).size == 2 }
        }

    fun partNumbersOf(symbol: Symbol) = getAdjacentSymbols(symbol).filter { it.isNumeric }

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
        return if (currentIndex == 0) emptyList() else lines[currentIndex - 1].symbolsInRange(IntRange(symbol.range.first - 1, symbol.range.last + 1))
    }

    private fun below(symbol: Symbol): List<Symbol> {
        val currentIndex = lines.indexOf(symbol.line)
        return if (currentIndex == lines.size - 1) emptyList() else lines[currentIndex + 1].symbolsInRange(IntRange(symbol.range.first - 1, symbol.range.last + 1))
    }
}

class Line(value: String) {
    val symbols: List<Symbol> = Regex("\\d+|[^0-9.]").findAll(value).map { Symbol(it.value, this, it.range) }.toList()
    val numbers: List<Symbol> = symbols.filter { it.isNumeric }

    fun symbolsInRange(range: IntRange) = symbols.filter { it.range.intersect(range).isNotEmpty() }

    fun before(symbol: Symbol) = symbols.find { it.range.last == symbol.range.first - 1 }

    fun after(symbol: Symbol) = symbols.find { it.range.first == symbol.range.last + 1 }
}

class Symbol(val value: String, val line: Line, val range: IntRange) {
    val isNumeric get() = value.toIntOrNull() != null
}