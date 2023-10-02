package com.drone.volocopter.service

import com.drone.volocopter.model.data.Command
import com.drone.volocopter.model.data.CurrentPosition
import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.model.data.World
import org.springframework.stereotype.Service
import java.util.*

@Service
class DroneMovementInputService(
    private val droneMovementCalculatorService: DroneMovementCalculatorService
) {
    lateinit var world: World
    lateinit var drone: Drone
    private var commands = mutableListOf<Command>()

    fun initiateDrone() {
        initializeWorldAndDrone()
        printStartingInfo()
        readSensorInput()
    }

    fun initializeWorldAndDrone() {
        this.world = World(10, 10, 10)
        this.drone = Drone(5, 5, 5)
    }

    fun printStartingInfo() {
        println(
            "=== Volodrone Initialising...\n" + "=== Volodrone Sensor Data read."
        )
        println("=== World: (x=range(0, ${world.width}),y=range(0, ${world.depth}),z=range(0, ${world.height}))")
        println("=== Drone: (x=range(0, ${drone.x}),y=range(0, ${drone.y}),z=range(0, ${drone.z}))")
        println("=== Command Format: LEFT 2 and Write 'Run' to execute commands")
        println("=== Volodrone Ready to Take off")

    }

    fun readSensorInput() {

        val scanner = Scanner(System.`in`)

        while (true) {
            println("=== Enter Command...")
            val input = scanner.nextLine()
            if (input.equals("END", ignoreCase = true)) {
                println("=== Volodrone Landing")
                break
            }
            if (input.equals("RUN", ignoreCase = true)) {
                executeCommands()

            } else {
                val parts = input.split(" ")
                if (parts.size == 2) {
                    val direction = parts[0]
                    val distance = parts[1].toIntOrNull()
                    if (distance != null) {
                        val order = commands.size + 1
                        val command = Command(order, direction.uppercase(), distance)
                        commands.add(command)
                    }
                } else {
                    println("Invalid Command")
                }
            }
        }
    }

    fun executeCommands() {
        val currentPosition = CurrentPosition(world, drone)
        for (command in commands) {
            printCommandExecutionLog(command)
            droneMovementCalculatorService.executeCommand(currentPosition, command)
        }
        commands.clear()
    }

    fun printCommandExecutionLog(command: Command) {
        println("Executing command: ${command.order} ${command.direction} ${command.distance}")
    }

}