package modules

object ProjectModules {

    const val App = ":app"

    object Arch {
        const val Bloc = ":library:bloc"
        const val AndroidBloc = ":library:androidbloc"
    }

    object Feature {
        const val Counter = ":features:ui:counter"
        const val InfiniteList = ":features:ui:infinitelist"
        const val Login = ":features:ui:login"
        const val Timer = ":features:ui:timer"
        const val Weather = ":features:ui:weather"

        object Data {
            const val InfiniteList = ":features:data:infinitelist-data"
            const val Weather = ":features:data:weather-data"
        }

        object Domain {
            const val Login = ":features:domain:login-domain"
        }
    }

    object Test {
        const val InstrumentationTestHelpers = ":instrumentation-test-helpers"
        const val UnitTestHelpers = ":unit-test-helpers"
    }

}