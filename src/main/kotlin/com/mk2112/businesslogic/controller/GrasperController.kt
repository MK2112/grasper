package com.mk2112.businesslogic.controller

import sun.nio.cs.UTF_8
import java.io.File
import java.lang.System.exit
import java.lang.Thread.sleep
import java.nio.channels.AsynchronousFileChannel.open

class GrasperController: AbstractGrasperController {

    override fun start(args: Array<String>) {
        if (args.size != 2) {
            exit(0)
        }

        val filePath = args[0]
        val wpm = Integer.parseInt(args[1])

        if (args[0].lowercase().endsWith(".pdf")) {
            //processPDF(filePath, wpm)
        } else if (args[0].lowercase().endsWith(".txt")) {
            processTXT(filePath, wpm)
        }

    }

    fun processTXT (filePath: String, wpm: Int) {

        val delay = getDelay(wpm)

        clearScreen("java -jar grasper.jar " + filePath + " " + wpm)

        File(filePath).forEachLine {
            val words = it.split(" ")
            for (word in words) {
                val printWord = writeBuffer(word) + word
                print(printWord)
                sleep(delay)
                clearScreen(printWord)
            }
        }
    }

    fun clearScreen(printWord: String) {
        for (char in printWord) {
            print("\b")
        }
    }

    fun getDelay(wpm: Int): Long {
        return (60.0 / wpm.toDouble() * 1000.0).toLong()
    }

    fun writeBuffer(word: String): String {
        val blank = "                    "
        return blank.substring(0, blank.length - word.length / 2)
    }
}