package com.drone.volocopter.service

import com.drone.volocopter.model.data.Command
import com.drone.volocopter.model.data.CurrentPosition
import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.model.data.World
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.Scanner

@SpringBootTest
class DroneMovementInputServiceTests {

    @MockBean
    private lateinit var droneMovementProcessor: DroneMovementProcessor

    private lateinit var droneMovementInputService: DroneMovementInputService

    private val endInput = "END\n"

    private val outContent = ByteArrayOutputStream()
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        droneMovementInputService = DroneMovementInputService(droneMovementProcessor)
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun testPrintCommandExecutionLog() {
        val command = Command(1, "UP", 2)
        droneMovementInputService.printCommandExecutionLog(command)

        val expectedOutput = "Executing command: 1 UP 2\n"
        assert(outContent.toString().contains( expectedOutput))
    }

    @Test
    fun testInvalidWorldInput() {
        val invalidInput = "UPP\n$endInput"
        val inputStream = ByteArrayInputStream(invalidInput.toByteArray())
        System.setIn(inputStream)

        droneMovementInputService.initializeWorldAndDrone()

        val expectedOutput = "Invalid World defined\n"
        assert(outContent.toString().contains(expectedOutput))
    }



}
