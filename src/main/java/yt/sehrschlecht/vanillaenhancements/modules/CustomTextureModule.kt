package yt.sehrschlecht.vanillaenhancements.modules

import yt.sehrschlecht.vanillaenhancements.items.resourcepack.ResourcePackBuilder

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
abstract class CustomTextureModule(description: String? = null, since: String? = null) : VEModule(description, since) {
    abstract fun createResourcePack() : ResourcePackBuilder

    fun packBuilder(builder: ResourcePackBuilder.() -> Unit): ResourcePackBuilder {
        val packBuilder = ResourcePackBuilder()
        builder.invoke(packBuilder)
        return packBuilder
    }

}