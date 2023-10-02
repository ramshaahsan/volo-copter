package com.drone.volocopter.service

import com.drone.volocopter.model.data.Command
import com.drone.volocopter.model.data.CurrentPosition
import com.drone.volocopter.model.data.Drone
import org.springframework.stereotype.Service

@Service
class DroneMovementCalculatorService {
    lateinit var currentPosition: CurrentPosition

    fun executeCommand(currentPosition: CurrentPosition, command: Command) {
        this.currentPosition = currentPosition
        val distance = command.distance
        when (command.direction) {
            "LEFT" -> moveLeft(distance)
            "RIGHT" -> moveRight(distance)
            "UP" -> moveUp(distance)
            "DOWN" -> moveDown(distance)
            "FORWARD" -> moveForward(distance)
            "BACKWARD" -> moveBackward(distance)

            else -> {
                print("Invalid direction found")
            }
        }
    }

    fun moveLeft(distance: Int) {
        var drone = currentPosition.drone
        var dronePositionChange = Drone(-1 * distance, 0, 0)

        var world = currentPosition.world
        val newX = currentPosition.drone.x - distance

        if (newX >= 0 && newX <= world.width) {
            drone.x = newX
            printNewPosition(dronePositionChange, drone)
        } else {
            printCrashImminentLog(dronePositionChange)
            dronePositionChange.x = -1 * drone.x
            drone.x = 0
            printNewPosition(dronePositionChange, drone)
        }
    }

    fun moveRight(distance: Int) {
        var drone = currentPosition.drone
        var dronePositionChange = Drone(distance, 0, 0)

        var world = currentPosition.world
        var newX = currentPosition.drone.x + distance

        if (newX >= 0 && newX <= world.width) {
            drone.x = newX
            printNewPosition(dronePositionChange, drone)
        } else {
            printCrashImminentLog(dronePositionChange)
            dronePositionChange.x = world.width - drone.x
            drone.x = world.width
            printNewPosition(dronePositionChange, drone)

        }
    }

    fun moveUp(distance: Int) {
        var drone = currentPosition.drone
        var dronePositionChange = Drone(0, distance, 0)

        var world = currentPosition.world
        val newY = currentPosition.drone.y + distance
        if (newY >= 0 && newY <= world.height) {
            drone.y = newY
            printNewPosition(dronePositionChange, drone)
        } else {
            printCrashImminentLog(dronePositionChange)
            dronePositionChange.y = world.height - drone.y
            drone.y = world.height
            printNewPosition(dronePositionChange, drone)
        }
    }

    fun moveDown(distance: Int) {
        val drone = currentPosition.drone
        var dronePositionChange = Drone(0, -1 * distance, 0)

        val world = currentPosition.world
        val newY = currentPosition.drone.y - distance
        if (newY >= 0 && newY <= world.height) {
            drone.y = newY
            printNewPosition(dronePositionChange, drone)
        } else {
            printCrashImminentLog(dronePositionChange)
            dronePositionChange.y = -1 * drone.y
            drone.y = 0
            printNewPosition(dronePositionChange, drone)
        }
    }

    fun moveForward(distance: Int) {
        var drone = currentPosition.drone
        var dronePositionChange = Drone(0, 0, distance)

        var world = currentPosition.world
        val newZ = currentPosition.drone.z + distance

        if (newZ >= 0 && newZ <= world.depth) {
            drone.z = newZ
            printNewPosition(dronePositionChange, drone)
        } else {
            printCrashImminentLog(dronePositionChange)
            dronePositionChange.z = world.depth - drone.z
            drone.z = world.depth
            printNewPosition(dronePositionChange, drone)
        }
    }

    fun moveBackward(distance: Int) {
        var drone = currentPosition.drone
        var dronePositionChange = Drone(0, 0, -1 * distance)

        val world = currentPosition.world
        val newZ = currentPosition.drone.z - distance

        if (newZ >= 0 && newZ <= world.depth) {
            drone.z = newZ
            printNewPosition(dronePositionChange, drone)
        } else {
            printCrashImminentLog(dronePositionChange)
            dronePositionChange.z = -1 * drone.z
            drone.z = 0
            printNewPosition(dronePositionChange, drone)
        }
    }

    fun printNewPosition(oldDrone: Drone, drone: Drone) {
        println("(${oldDrone.x}, ${oldDrone.y}, ${oldDrone.z}) -> (${drone.x}, ${drone.y}, ${drone.z})")
    }

    fun printCrashImminentLog(oldDrone: Drone) {
        println("(${oldDrone.x}, ${oldDrone.y}, ${oldDrone.z}) -> CRASH IMMINENT - AUTOMATIC COURSE CORRECTION")
    }
}