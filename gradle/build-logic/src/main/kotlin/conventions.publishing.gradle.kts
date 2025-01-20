import org.incendo.cloudbuildlogic.city
import org.incendo.cloudbuildlogic.jmp

plugins {
    id("org.incendo.cloud-build-logic.publishing")
}

indra {
    publishAllTo("sayanrepo", "https://repo.sayandev.org/snapshots")
    /*github("Incendo", "cloud-minecraft") {
        ci(true)
    }*/
    mitLicense()

    configurePublications {
        pom {
            developers {
                jmp()
                city()
            }
        }
    }
}
