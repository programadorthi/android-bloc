import modules.ProjectModules

include(
    ProjectModules.App,
    ProjectModules.Arch.Bloc,
    ProjectModules.Arch.AndroidBloc,
    ProjectModules.Feature.Counter,
    ProjectModules.Feature.InfiniteList,
    ProjectModules.Feature.Login,
    ProjectModules.Feature.Timer,
    ProjectModules.Feature.Weather,
    ProjectModules.Feature.Domain.Login,
    ProjectModules.Test.InstrumentationTestHelpers,
    ProjectModules.Test.UnitTestHelpers
)
