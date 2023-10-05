package com.drone.volocopter.service

import com.drone.volocopter.model.data.Command
import com.drone.volocopter.model.data.CurrentPosition
import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.model.data.World
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@SpringBootTest
class LeftMovementExecutorTests {
    private lateinit var droneMovementInputService: DroneMovementInputService
    private lateinit var droneMovementProcessor: DroneMovementProcessor
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        droneMovementProcessor = DroneMovementProcessor()
        droneMovementInputService = DroneMovementInputService(droneMovementProcessor)
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @Test
    fun testMoveLeft_MultipleCommands_ShouldPass() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "LEFT", 2)
        droneMovementProcessor.executeCommand(currentPosition, command)
        Command(1, "LEFT", 2)
        droneMovementProcessor.executeCommand(currentPosition, command)

        Assertions.assertEquals(1, droneMovementInputService.drone.x)
        Assertions.assertEquals(5, droneMovementInputService.drone.y)
        Assertions.assertEquals(5, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveLeft_SingleCommand_ShouldPass() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "LEFT", 5)
        droneMovementProcessor.executeCommand(currentPosition, command)

        Assertions.assertEquals(0, droneMovementInputService.drone.x)
        Assertions.assertEquals(5, droneMovementInputService.drone.y)
        Assertions.assertEquals(5, droneMovementInputService.drone.z)
    }
    @Test
    fun testMoveLeft_Collision() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "LEFT", 15)
        droneMovementProcessor.executeCommand(currentPosition, command)

        var consoleOutput = outputStreamCaptor.toString().trim()
        Assertions.assertTrue(consoleOutput.contains("CRASH"))
        Assertions.assertTrue(consoleOutput.contains("(-5, 0, 0) -> (0, 5, 5)"))

        Command(1, "LEFT", 10)
        droneMovementProcessor.executeCommand(currentPosition, command)

        consoleOutput = outputStreamCaptor.toString().trim()
        Assertions.assertTrue(consoleOutput.contains("CRASH"))
        Assertions.assertTrue(consoleOutput.contains("(0, 0, 0) -> (0, 5, 5)"))
    }
}