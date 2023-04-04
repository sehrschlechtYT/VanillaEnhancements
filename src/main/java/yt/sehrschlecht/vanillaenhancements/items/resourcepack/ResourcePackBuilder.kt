package yt.sehrschlecht.vanillaenhancements.items.resourcepack

import org.bukkit.Material
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files
import javax.imageio.ImageIO

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ResourcePackBuilder {

    private val customModelDatas = mutableMapOf<Material, MutableList<CustomModelData>>()

    fun addCustomModelData(item: Material, data: CustomModelData) {
        if (customModelDatas.containsKey(item)) {
            customModelDatas[item]!!.add(data)
        } else {
            customModelDatas[item] = mutableListOf(data)
        }
        Debug.RESOURCE_PACKS.log("Added custom model data for ${item.key.key} with value ${data.value}")
    }

    fun addCustomModelData(item: Material, value: Int, texture: Texture) {
        addCustomModelData(item, CustomModelData(value, Model(item.key.key, texture)))
    }

    fun run(buildFolder: File) {
        Debug.RESOURCE_PACKS.log("Running resource pack builder (build folder: ${buildFolder.absolutePath})")
        customModelDatas.forEach { (material, dataList) ->
            Debug.RESOURCE_PACKS.log("Building custom model data for item ${material.key.key}")
            val file = File(buildFolder, "assets/minecraft/models/item/${material.key.key}.json")
            createFileAndParentsIfNotExists(file)
            val fileContents = StringBuilder()
            fileContents.append(
                """
                {
                    "parent": "item/handheld",
                    "textures": {
                        "layer0": "item/${material.key.key}"
                    },
                    "overrides": [
                """.trimIndent()
            )
            dataList.forEach { data ->
                Debug.RESOURCE_PACKS.log("Adding custom model data ${data.value} for item ${material.key.key}")
                fileContents.append(
                    """
                    {
                        "predicate": {
                            "custom_model_data": ${data.value}
                        },
                        "model": "item/${data.model.parent}/${data.model.texture.name}}"
                    }
                """.trimIndent()
                )
            }
            fileContents.append(
                """
                    ]
                }
            """.trimIndent()
            )
            file.writeText(fileContents.toString())

            // save textures
            dataList.forEach { data ->
                saveTexture(data.model.texture, buildFolder)
            }

            // save model
            dataList.forEach { data ->
                saveModel(data.model, buildFolder)
            }
        }
        Debug.RESOURCE_PACKS.log("Finished resource pack builder")
    }

    private fun saveTexture(texture: Texture, buildFolder: File) {
        Debug.RESOURCE_PACKS.log("Saving texture ${texture.name}")
        val file = File(buildFolder, "assets/minecraft/textures/item/${texture.name}.png")
        createFileAndParentsIfNotExists(file)
        val image = texture.texture
        ImageIO.write(image, "png", file)
        Debug.RESOURCE_PACKS.log("Saved texture ${texture.name} to ${file.absolutePath}")
    }

    private fun saveModel(model: Model, buildFolder: File) {
        Debug.RESOURCE_PACKS.log("Saving model ${model.parent}")
        val file = File(buildFolder, "assets/minecraft/models/item/${model.parent}/${model.texture.name}.json")
        createFileAndParentsIfNotExists(file)
        file.writeText(
            """
            {
                "parent": "item/handheld",
                "textures": {
                    "layer0": "item/${model.parent}/${model.texture.name}"
                }
            }
        """.trimIndent()
        )
        Debug.RESOURCE_PACKS.log("Saved model ${model.parent} to ${file.absolutePath}")
    }

    private fun createFileAndParentsIfNotExists(file: File) {
        Files.createDirectories(file.parentFile.toPath())
        if (!file.exists()) {
            file.createNewFile()
        }
    }

}

class CustomModelData(
    val value: Int,
    val model: Model
)

class Model(
    val parent: String,
    val texture: Texture
)

class Texture(
    val name: String,
    val texture: BufferedImage
) {
    init {
        if (!name.lowercase().endsWith(".png")) {
            throw IllegalArgumentException("Texture name must have a file extension of .png!")
        }
    }
}