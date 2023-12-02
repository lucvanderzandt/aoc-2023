package utils

import java.io.File

object FileReader {
    // Reads the input file as a single string
    private fun readResource(path: String) : String = File(ClassLoader.getSystemResource(path).file).readText()

    // Reads the input file as a list of strings where each line is an element
    fun asStrings(path: String) : List<String> = readResource(path).split("\n")

    // Reads the input file, converts each line to an int, and returns them as a list
    fun asInts(path: String) : List<Int> = asStrings(path).map { it.toInt() }
}