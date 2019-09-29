package modules

object ProjectModules {

    const val App = ":app"

    object Arch {
        const val Bloc = ":library:bloc"
        const val AndroidBloc = ":library:androidbloc"
    }

    object Feature {
        const val Counter = ":features:ui:counter"
        const val InfiniteList = ":infinitelist"
        const val Login = ":login"
        const val Timer = ":timer"
        const val Weather = ":weather"

        object Domain {
            const val Counter = ":features:domain:counter-domain"
        }
    }

    object Test {
        const val InstrumentationTestHelpers = ":instrumentation-test-helpers"
        const val UnitTestHelpers = ":unit-test-helpers"
    }

}