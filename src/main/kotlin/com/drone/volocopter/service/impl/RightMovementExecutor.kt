package com.drone.volocopter.service.impl

import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.model.data.World
import com.drone.volocopter.service.CommandExecutor
import org.springframework.stereotype.Component

@Component
class RightMovementExecutor : CommandExecutor {
    override fun execute(drone: Drone, world: World, distance: Int) {
        val dronePositionChange = Drone(distance, 0, 0)

        val newX = drone.x + distance
        val rangeCheck = validatePositionWithInRange(newX, world.width)

        if (rangeCheck) {
            drone.x = newX
            printNewPosition(dronePositionChange, drone)
        } else {
            printCrashImminentLog(dronePositionChange)
            dronePositionChange.x = world.width - drone.x
            drone.x = world.width
            printNewPosition(dronePositionChange, drone)

        }
    }
}