package com.drone.volocopter

import com.drone.volocopter.cmd.VoloCommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class VoloCopterApplication(private val voloCommandLineRunner: VoloCommandLineRunner) {

    fun runCMD(args: Array<String>) {
        voloCommandLineRunner.runCMD()
    }
}

fun main(args: Array<String>) {
    val context = runApplication<VoloCopterApplication>(*args)
    val app = context.getBean(VoloCopterApplication::class.java)
    app.runCMD(args)
}

