package com.mk2112.businesslogic.controller

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File
import java.lang.System.exit
import java.lang.Thread.sleep

class GrasperController: AbstractGrasperController {

    override fun start(args: Array<String>) {
        if (args.size != 2) {
            exit(0)
        }

        val filePath = args[0]
        val wpm = Integer.parseInt(args[1])

        if (args[0].lowercase().endsWith(".pdf")) {
            processPDF(filePath, wpm)
        } else if (args[0].lowercase().endsWith(".txt")) {
            processTXT(filePath, wpm)
        }

    }

    fun processPDF(filePath: String, wpm: Int) {
        val delay = getDelay(wpm)
        clearScreen()

        val document = PDDocument.load(File(filePath))
        if (!document.isEncrypted) {
            val stripper = PDFTextStripper()
            val words = stripper.getText(document).replace("\n", "").replace("\t", " ").split(" ")
            for (word in words) {
                val printWord = writeBuffer(word) + word.replace(" ", "")
                print(printWord)
                sleep(delay)
                clearScreen()
            }
        }
        document.close()
    }

    fun processTXT (filePath: String, wpm: Int) {
        val delay = getDelay(wpm)
        clearScreen()

        File(filePath).forEachLine {
            val words = it.split(" ")
            for (word in words) {
                val printWord = writeBuffer(word) + word.replace(" ", "").replace("\t", "")
                print(printWord)
                sleep(delay)
                clearScreen()
            }
        }
    }

    fun clearScreen() {
        val os = System.getProperty("os.name")
        if (os.contains("Windows")) ProcessBuilder("cmd", "/c", "cls").inheritIO().start()
            .waitFor() else Runtime.getRuntime().exec("clear")
    }

    fun getDelay(wpm: Int): Long {
        return (60.0 / wpm.toDouble() * 1000.0).toLong()
    }

    fun writeBuffer(word: String): String {
        val blank = "\n\n\n\n\n\n\n\n\n\n\n\n\n                                                           "
        return blank.substring(0, blank.length - word.length / 2)
    }
}