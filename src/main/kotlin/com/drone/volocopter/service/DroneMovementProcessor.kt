package com.drone.volocopter.service

import com.drone.volocopter.model.data.Command
import com.drone.volocopter.model.data.CurrentPosition
import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.service.impl.*
import org.springframework.stereotype.Service

@Service
class DroneMovementProcessor {
    lateinit var currentPosition: CurrentPosition
    private val executors = mapOf(
        "LEFT" to LeftMovementExecutor(),
        "RIGHT" to RightMovementExecutor(),
        "UP" to UpwardMovementExecutor(),
        "DOWN" to DownwardMovementExecutor(),
        "FORWARD" to ForwardMovementExecutor(),
        "BACKWARD" to BackwardMovementExecutor()
    )

    fun executeCommand(currentPosition: CurrentPosition, command: Command) {
        this.currentPosition = currentPosition
        val executor = getCommandExecutor(command.direction)

        if (executor != null) {
            executor.execute(
                currentPosition.drone,
                currentPosition.world, command.distance
            )
        } else {
            println("Invalid direction found")
        }
    }

    private fun getCommandExecutor(direction: String): CommandExecutor? {
        return executors[direction.uppercase()]
    }
}