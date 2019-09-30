package modules

object ProjectModules {

    const val App = ":app"

    object Arch {
        private const val ArchPrefix = ":arch:"
        const val Bloc = "${ArchPrefix}bloc"
        const val AndroidBloc = "${ArchPrefix}androidbloc"
    }

    object Feature {
        private const val FeaturePrefix = ":features:ui:"
        const val Counter = "${FeaturePrefix}counter"
        const val InfiniteList = "${FeaturePrefix}infinitelist"
        const val Login = "${FeaturePrefix}login"
        const val Timer = "${FeaturePrefix}timer"
        const val Weather = "${FeaturePrefix}weather"

        object Data {
            private const val DataPrefix = ":features:data:"
            const val InfiniteList = "${DataPrefix}infinitelist-data"
            const val Weather = "${DataPrefix}weather-data"
        }

        object Domain {
            private const val DomainPrefix = ":features:domain:"
            const val Login = "${DomainPrefix}login-domain"
        }
    }

    object Test {
        const val InstrumentationTestHelpers = ":instrumentation-test-helpers"
        const val UnitTestHelpers = ":unit-test-helpers"
    }

}