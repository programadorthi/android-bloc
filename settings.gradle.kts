import modules.ProjectModules

include(
    ProjectModules.App,
    ProjectModules.Arch.Bloc,
    ProjectModules.Feature.Counter,
    ProjectModules.Feature.InfiniteList,
    ProjectModules.Feature.Login,
    ProjectModules.Feature.Timer
)
