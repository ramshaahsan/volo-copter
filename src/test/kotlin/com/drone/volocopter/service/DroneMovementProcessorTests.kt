package com.drone.volocopter.service

import com.drone.volocopter.model.data.Command
import com.drone.volocopter.model.data.CurrentPosition
import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.model.data.World
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@SpringBootTest
class DroneMovementProcessorTests {

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
    fun testMoveLeft_WithinBoundary_ShouldPass() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)

        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "LEFT", 2);
        droneMovementProcessor.executeCommand(currentPosition, command)

        assertEquals(3, droneMovementInputService.drone.x)
        assertEquals(5, droneMovementInputService.drone.y)
        assertEquals(5, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveDown_WithinBoundary_ShouldPass() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "DOWN", 2);
        droneMovementProcessor.executeCommand(currentPosition, command)

        assertEquals(5, droneMovementInputService.drone.x)
        assertEquals(3, droneMovementInputService.drone.y)
        assertEquals(5, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveUp_Collision() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "UP", 15);
        droneMovementProcessor.executeCommand(currentPosition, command)

        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(0, 5, 0) -> (5, 10, 5)"))

    }

    @Test
    fun testMoveDown_Collision() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "DOWN", 15)
        droneMovementProcessor.executeCommand(currentPosition, command)
        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(0, -5, 0) -> (5, 0, 5)"))

    }

    @Test
    fun testMoveUp_WithinBoundary_ShouldPass() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "UP", 2)
        droneMovementProcessor.executeCommand(currentPosition, command)

        assertEquals(5, droneMovementInputService.drone.x)
        assertEquals(7, droneMovementInputService.drone.y)
        assertEquals(5, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveRight_WithinBoundary_ShouldPass() {
        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)

        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "RIGHT", 2)
        droneMovementProcessor.executeCommand(currentPosition, command)

        assertEquals(7, droneMovementInputService.drone.x)
        assertEquals(5, droneMovementInputService.drone.y)
        assertEquals(5, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveRight_Collision() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "RIGHT", 15)
        droneMovementProcessor.executeCommand(currentPosition, command)

        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(5, 0, 0) -> (10, 5, 5)"))

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

        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(-5, 0, 0) -> (0, 5, 5)"))
    }

    @Test
    fun testMoveForward_WithinBoundary_ShouldPass() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "FORWARD", 2)
        droneMovementProcessor.executeCommand(currentPosition, command)
        assertEquals(5, droneMovementInputService.drone.x)
        assertEquals(5, droneMovementInputService.drone.y)
        assertEquals(7, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveBackward_WithinBoundary_ShouldPass() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "BACKWARD", 2)
        droneMovementProcessor.executeCommand(currentPosition, command)
        assertEquals(5, droneMovementInputService.drone.x)
        assertEquals(5, droneMovementInputService.drone.y)
        assertEquals(3, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveForward_Collision() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "FORWARD", 15)
        droneMovementProcessor.executeCommand(currentPosition, command)

        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(0, 0, 5) -> (5, 5, 10)"))

    }

    @Test
    fun testMoveBackward_Collision() {

        droneMovementInputService.drone = Drone(5, 5, 5)
        droneMovementInputService.world = World(10, 10, 10)
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementProcessor.currentPosition = currentPosition

        val command = Command(1, "BACKWARD", 15)
        droneMovementProcessor.executeCommand(currentPosition, command)

        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(0, 0, -5) -> (5, 5, 0)"))
    }


}
