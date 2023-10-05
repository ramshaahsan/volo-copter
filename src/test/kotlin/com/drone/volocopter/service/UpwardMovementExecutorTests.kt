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
class UpwardMovementExecutorTests {
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
    fun testMoveUpward_MultipleCommands_ShouldPass() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "UP", 2)
        droneMovementProcessor.executeCommand(currentPosition, command)
        Command(1, "UP", 2)
        droneMovementProcessor.executeCommand(currentPosition, command)

        Assertions.assertEquals(5, droneMovementInputService.drone.x)
        Assertions.assertEquals(9, droneMovementInputService.drone.y)
        Assertions.assertEquals(5, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveUpward_SingleCommand_ShouldPass() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "UP", 5)
        droneMovementProcessor.executeCommand(currentPosition, command)

        Assertions.assertEquals(5, droneMovementInputService.drone.x)
        Assertions.assertEquals(10, droneMovementInputService.drone.y)
        Assertions.assertEquals(5, droneMovementInputService.drone.z)
    }
    @Test
    fun testMoveUpward_Collision() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "UP", 15)
        droneMovementProcessor.executeCommand(currentPosition, command)

        var consoleOutput = outputStreamCaptor.toString().trim()
        Assertions.assertTrue(consoleOutput.contains("CRASH"))
        Assertions.assertTrue(consoleOutput.contains("(0, 5, 0) -> (5, 10, 5)"))

        Command(1, "UP", 10)
        droneMovementProcessor.executeCommand(currentPosition, command)

        consoleOutput = outputStreamCaptor.toString().trim()
        Assertions.assertTrue(consoleOutput.contains("CRASH"))
        Assertions.assertTrue(consoleOutput.contains("(0, 0, 0) -> (5, 10, 5)"))
    }
}