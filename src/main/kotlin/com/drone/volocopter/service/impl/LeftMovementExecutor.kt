package com.drone.volocopter.service.impl

import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.model.data.World
import com.drone.volocopter.service.CommandExecutor
import org.springframework.stereotype.Component

@Component
class LeftMovementExecutor : CommandExecutor {
    override fun execute(drone: Drone, world: World, distance: Int) {
        val dronePositionChange = Drone(-1 * distance, 0, 0)

        val newX = drone.x - distance
        val rangeCheck = validatePositionWithInRange(newX, world.width)

        if (rangeCheck) {
            drone.x = newX
            printNewPosition(dronePositionChange, drone)
        } else {
            printCrashImminentLog(dronePositionChange)
            dronePositionChange.x = -1 * drone.x
            drone.x = 0
            printNewPosition(dronePositionChange, drone)
        }
    }
}