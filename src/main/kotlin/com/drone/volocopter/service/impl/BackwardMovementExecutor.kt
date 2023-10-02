package com.drone.volocopter.service.impl

import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.model.data.World
import com.drone.volocopter.service.CommandExecutor
import org.springframework.stereotype.Component

@Component
class BackwardMovementExecutor : CommandExecutor {
    override fun execute(drone: Drone, world: World, distance: Int) {
        val dronePositionChange = Drone(0, 0, -1 * distance)

        val newZ = drone.z - distance

        val rangeCheck = validatePositionWithInRange(newZ, world.depth)

        if (rangeCheck) {
            drone.z = newZ
            printNewPosition(dronePositionChange, drone)
        } else {
            printCrashImminentLog(dronePositionChange)
            dronePositionChange.z = -1 * drone.z
            drone.z = 0
            printNewPosition(dronePositionChange, drone)
        }
    }

}