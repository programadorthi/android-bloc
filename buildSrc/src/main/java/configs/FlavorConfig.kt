package configs

object FlavorConfig {
    const val defaultDimensionName = "default"

    object Endpoint {
        const val jsonplaceholder = "\"https://jsonplaceholder.typicode.com\""
        const val metaweather = "\"https://www.metaweather.com\""
    }

    object Flavor {
        const val development = "dev"
        const val production = "prod"
    }

    object Manifest {
        val development = mapOf("crashlyticsEnabled" to "false")
        val production = mapOf("crashlyticsEnabled" to "true")
    }
}