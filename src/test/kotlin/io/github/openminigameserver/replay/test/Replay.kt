package io.github.openminigameserver.replay.test

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.extras.MojangAuth
import net.minestom.server.extras.PlacementRules
import net.minestom.server.extras.optifine.OptifineSupport
import net.minestom.server.extras.selfmodification.MinestomRootClassLoader

fun main(args: Array<String>) {
    MinestomRootClassLoader.getInstance().protectedPackages.addAll(
        arrayOf(
            "org.reactivestreams",
            "io.leangen.geantyref",
            "kotlinx"
        )
    )

    val server = MinecraftServer.init()
    MojangAuth.init()
    OptifineSupport.enable()
    PlacementRules.init()
    MinecraftServer.setGroupedPacket(false)

    MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerLoginEvent::class.java, PlayerInit)

    server.start(
        "0.0.0.0", 25566
    ) { _, responseData ->
        responseData.apply {
            responseData.setDescription("Replay")
        }
    }
}
