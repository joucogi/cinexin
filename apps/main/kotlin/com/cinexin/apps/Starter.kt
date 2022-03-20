package com.cinexin.apps

import com.cinexin.apps.backoffice.BackofficeApplication
import com.cinexin.apps.visualizer.VisualizerApplication
import org.springframework.boot.SpringApplication
import kotlin.reflect.KClass

fun main(args: Array<String>) {
    val starter = Starter()
    starter.run(args)
}

class Starter {
    private val applications = hashMapOf<String, KClass<*>>(
        "backoffice" to BackofficeApplication::class,
        "visualizer" to VisualizerApplication::class
    )

    fun run(args: Array<String>) {
        ensureThereAreEnoughArguments(args)

        val applicationName = args[0]

        ensureApplicationExists(applicationName)

        val applicationClass: KClass<*>? = applications[applicationName]
        val application = SpringApplication(applicationClass?.java)

        application.run(*args)
    }

    private fun ensureApplicationExists(applicationName: String) {
        if (!applications.containsKey(applicationName)) {
            throw RuntimeException("Application $applicationName does not exist")
        }
    }

    private fun ensureThereAreEnoughArguments(args: Array<String>) {
        if (args.isEmpty()) {
            throw RuntimeException("There are not enough arguments")
        }
    }
}
