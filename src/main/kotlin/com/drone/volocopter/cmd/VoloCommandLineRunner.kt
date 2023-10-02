package com.drone.volocopter.cmd

import com.drone.volocopter.service.DroneMovementInputService
import org.springframework.stereotype.Service


@Service
class VoloCommandLineRunner
    (private val droneMovementInputService: DroneMovementInputService) {
    fun runCMD() {
        droneMovementInputService.initiateDrone()
    }
}