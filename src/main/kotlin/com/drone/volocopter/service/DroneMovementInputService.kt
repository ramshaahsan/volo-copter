package com.drone.volocopter.service

import com.drone.volocopter.model.data.Command
import com.drone.volocopter.model.data.CurrentPosition
import com.drone.volocopter.model.data.Drone
import com.drone.volocopter.model.data.World
import org.springframework.stereotype.Service
import java.util.*

@Service
class DroneMovementInputService(
    private val droneMovementProcessor: DroneMovementProcessor
) {
    lateinit var world: World
    lateinit var drone: Drone
    var commands = mutableListOf<Command>()

    fun initiateDrone() {
        initializeWorldAndDrone()
        printStartingInfo()
        readSensorInput()
    }

    fun initializeWorldAndDrone() {
        val scanner = Scanner(System.`in`)

        while (true) {

            println("WORLD e.g 10 10 10")
            val input = scanner.nextLine()
            if (input.equals("END", ignoreCase = true)) {
                println("=== Volodrone Landing")
                break
            }

            val parts = input.trim().split(" ")
            if (parts.size == 3) {
                val width = parts[0].toIntOrNull()
                val height = parts[1].toIntOrNull()
                val depth = parts[2].toIntOrNull()

                val isWorldValid = initWorld(width, height, depth)

                if (isWorldValid) {
                    val isDroneValid = initDrone()
                    if (isDroneValid) {
                        break
                    }
                }
            } else {
                println("Invalid World defined")
            }
        }
    }

    fun initDrone(): Boolean {
        val scanner = Scanner(System.`in`)
        while (true) {
            println("DRONE e.g 5 5 5")
            val input = scanner.nextLine()
            if (input.equals("END", ignoreCase = true)) {
                println("=== Volodrone Landing")
                break
            }
            val parts = input.trim().split(" ")

            val x = parts[0].toIntOrNull()
            val y = parts[1].toIntOrNull()
            val z = parts[2].toIntOrNull()
            if (validateDrone(x, y, z)) {
                return true
            } else {
                this.drone = Drone(-1, -1, -1)
            }

        }
        return false;
    }


    fun validateDrone(x: Int?, y: Int?, z: Int?): Boolean {
        if ((x != null && (world.width >= x && x >= 0))
            &&
            (y != null && (world.height >= y && y >= 0))
            &&
            (z != null && (world.depth >= z && z >= 0))
        ) {
            this.drone = Drone(x, y, z)
            return true
        }
        println("Drone position cannot exceed World's Range")
        return false
    }

    fun initWorld(width: Int?, height: Int?, depth: Int?): Boolean {
        if (width == null || width < 0) {
            println("Invalid Width")
            return false
        }
        if (height == null || height < 0) {
            println("Invalid Height")
            return false

        }
        if (depth == null || depth < 0) {
            println("Invalid Depth")
            return false

        }
        this.world = World(width, height, depth)
        return true
    }

    fun printStartingInfo() {
        println(
            "=== Volodrone Initialising...\n" + "=== Volodrone Sensor Data read."
        )
        println("=== World: (x=range(0, ${world.width}),y=range(0, ${world.depth}),z=range(0, ${world.height}))")
        println("=== Drone: (${drone.x}, ${drone.y}, ${drone.z})")
        println("=== Command Format: LEFT 2 and Write 'Run' to execute commands")
        println("=== Volodrone Ready to Take off")

    }

    fun readSensorInput() {

        val scanner = Scanner(System.`in`)
        println("=== Enter Command(s)... type 'RUN' to execute or 'END' to end process")

        while (true) {

            val input = scanner.nextLine()
            if (input.equals("END", ignoreCase = true)) {
                println("=== Volodrone Landing")
                break
            }

            if (input.equals("RUN", ignoreCase = true)) {
               commands = commands.sortedBy { it.order }.toMutableList()
                executeCommands()

            } else {
                val parts = input.split(" ")
                if (parts.size == 3) {
                    val order = parts[0].toIntOrNull()
                    val direction = parts[1]
                    val distance = parts[2].toIntOrNull()
                    if (order != null && distance != null) {
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
            droneMovementProcessor.executeCommand(currentPosition, command)
        }
        commands.clear()
    }

    fun printCommandExecutionLog(command: Command) {
        println("Executing command: ${command.order} ${command.direction} ${command.distance}")
    }

}