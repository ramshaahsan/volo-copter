package com.drone.volocopter.service

import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.model.data.World

interface CommandExecutor {
    fun execute(drone: Drone, world: World, distance: Int)

    fun printNewPosition(oldDrone: Drone, drone: Drone) {
        println("(${oldDrone.x}, ${oldDrone.y}, ${oldDrone.z}) -> (${drone.x}, ${drone.y}, ${drone.z})")
    }

    fun printCrashImminentLog(oldDrone: Drone) {
        println("(${oldDrone.x}, ${oldDrone.y}, ${oldDrone.z}) -> CRASH IMMINENT - AUTOMATIC COURSE CORRECTION")
    }

    fun validatePositionWithInRange(position: Int, range: Int): Boolean {
        if (position >= 0 && position <= range) {
            return true
        }
        return false
    }
}