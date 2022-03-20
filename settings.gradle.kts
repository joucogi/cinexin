rootProject.name = "cinexin"

include(":visualizer")
project(":visualizer").projectDir = File("src/visualizer")

include(":backoffice")
project(":backoffice").projectDir = File("src/backoffice")

include(":shared")
project(":shared").projectDir = File("src/shared")