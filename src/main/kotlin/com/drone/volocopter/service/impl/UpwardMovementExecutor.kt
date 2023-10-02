package com.drone.volocopter.service.impl

import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.model.data.World
import com.drone.volocopter.service.CommandExecutor
import org.springframework.stereotype.Component

@Component
class UpwardMovementExecutor : CommandExecutor {
    override fun execute(drone: Drone, world: World, distance: Int) {
        val dronePositionChange = Drone(0, distance, 0)

        val newY = drone.y + distance

        val rangeCheck = validatePositionWithInRange(newY, world.height)

        if (rangeCheck) {
            drone.y = newY
            printNewPosition(dronePositionChange, drone)
        } else {
            printCrashImminentLog(dronePositionChange)
            dronePositionChange.y = world.height - drone.y
            drone.y = world.height
            printNewPosition(dronePositionChange, drone)
        }
    }
}