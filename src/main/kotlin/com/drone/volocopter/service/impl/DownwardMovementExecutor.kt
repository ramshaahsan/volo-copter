package com.drone.volocopter.service.impl

import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.model.data.World
import com.drone.volocopter.service.CommandExecutor
import org.springframework.stereotype.Component

@Component
class DownwardMovementExecutor : CommandExecutor {
    override fun execute(drone: Drone, world: World, distance: Int) {
        val dronePositionChange = Drone(0, -1 * distance, 0)

        val newY = drone.y - distance
        val rangeCheck = validatePositionWithInRange(newY, world.height)

        if (rangeCheck) {
            drone.y = newY
            printNewPosition(dronePositionChange, drone)
        } else {
            printCrashImminentLog(dronePositionChange)
            dronePositionChange.y = -1 * drone.y
            drone.y = 0
            printNewPosition(dronePositionChange, drone)
        }
    }
}