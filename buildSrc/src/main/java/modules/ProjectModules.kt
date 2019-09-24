package modules

object ProjectModules {

    const val App = ":app"

    object Arch {
        const val Bloc = ":bloc"
    }

    object Feature {
        const val Counter = ":counter"
        const val InfiniteList = ":infinitelist"
        const val Login = ":login"
        const val Timer = ":timer"
        const val Weather = ":weather"
    }

    object Test {
        const val UnitTestHelpers = ":unit-test-helpers"
    }

}