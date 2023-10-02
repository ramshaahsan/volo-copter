package com.drone.volocopter.service

import com.drone.volocopter.model.data.CurrentPosition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@SpringBootTest
class DroneMovementCalculatorServiceTests {

    private lateinit var droneMovementInputService: DroneMovementInputService
    private lateinit var droneMovementCalculatorService: DroneMovementCalculatorService
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        droneMovementCalculatorService = DroneMovementCalculatorService()
        droneMovementInputService = DroneMovementInputService(droneMovementCalculatorService)
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @Test
    fun testMoveLeft_WithinBoundary_ShouldPass() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveLeft(2)

        assertEquals(3, droneMovementInputService.drone.x)
        assertEquals(5, droneMovementInputService.drone.y)
        assertEquals(5, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveDown_WithinBoundary_ShouldPass() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveDown(2)

        assertEquals(5, droneMovementInputService.drone.x)
        assertEquals(3, droneMovementInputService.drone.y)
        assertEquals(5, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveUp_Collision() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveUp(15)
        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(0, 5, 0) -> (5, 10, 5)"))

    }

    @Test
    fun testMoveDown_Collision() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveDown(15)
        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(0, -5, 0) -> (5, 0, 5)"))

    }

    @Test
    fun testMoveUp_WithinBoundary_ShouldPass() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveUp(2)

        assertEquals(5, droneMovementInputService.drone.x)
        assertEquals(7, droneMovementInputService.drone.y)
        assertEquals(5, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveRight_WithinBoundary_ShouldPass() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveRight(2)

        assertEquals(7, droneMovementInputService.drone.x)
        assertEquals(5, droneMovementInputService.drone.y)
        assertEquals(5, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveRight_Collision() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveRight(15)
        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(5, 0, 0) -> (10, 5, 5)"))

    }

    @Test
    fun testMoveLeft_Collision() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveLeft(15)
        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(-5, 0, 0) -> (0, 5, 5)"))
    }

    @Test
    fun testMoveForward_WithinBoundary_ShouldPass() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveForward(2)

        assertEquals(5, droneMovementInputService.drone.x)
        assertEquals(5, droneMovementInputService.drone.y)
        assertEquals(7, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveBackward_WithinBoundary_ShouldPass() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveBackward(2)

        assertEquals(5, droneMovementInputService.drone.x)
        assertEquals(5, droneMovementInputService.drone.y)
        assertEquals(3, droneMovementInputService.drone.z)
    }

    @Test
    fun testMoveForward_Collision() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveForward(15)
        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(0, 0, 5) -> (5, 5, 10)"))

    }

    @Test
    fun testMoveBackward_Collision() {
        droneMovementInputService.initializeWorldAndDrone()
        val currentPosition = CurrentPosition(
            droneMovementInputService.world,
            droneMovementInputService.drone
        )
        droneMovementCalculatorService.currentPosition = currentPosition

        droneMovementCalculatorService.moveBackward(15)
        val consoleOutput = outputStreamCaptor.toString().trim()
        assertTrue(consoleOutput.contains("CRASH"))
        assertTrue(consoleOutput.contains("(0, 0, -5) -> (5, 5, 0)"))
    }


}
