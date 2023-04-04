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
class ResourcePackBuilder { // ToDo make this a library

    private val customModelDatas = mutableMapOf<Material, MutableList<CustomModelData>>()

    fun addCustomModelData(item: Material, data: CustomModelData) {
        if (customModelDatas.containsKey(item)) {
            customModelDatas[item]!!.add(data)
        } else {
            customModelDatas[item] = mutableListOf(data)
        }
        Debug.RESOURCE_PACKS.log("Added custom model data for ${item.key.key} with value ${data.value}")
    }

    fun addCustomModelData(item: Material, value: Int, texture: Texture, display: Display? = null) {
        addCustomModelData(item, CustomModelData(value, Model(item.key.key, texture, display)))
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
                        "model": "item/${data.model.parent}/${data.model.texture.plainName()}"
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
                saveTexture(material, data.model.texture, buildFolder)
            }

            // save model
            dataList.forEach { data ->
                saveModel(data.model, buildFolder)
            }
        }
        Debug.RESOURCE_PACKS.log("Finished resource pack builder")
    }

    private fun saveTexture(item: Material, texture: Texture, buildFolder: File) {
        Debug.RESOURCE_PACKS.log("Saving texture ${texture.name}")
        val file = File(buildFolder, "assets/minecraft/textures/item/${item.key.key}/${texture.name}")
        createFileAndParentsIfNotExists(file)
        val image = texture.texture
        ImageIO.write(image, "png", file)
        Debug.RESOURCE_PACKS.log("Saved texture ${texture.name} to ${file.absolutePath}")
    }

    private fun saveModel(model: Model, buildFolder: File) {
        Debug.RESOURCE_PACKS.log("Saving model ${model.parent}")
        val file = File(buildFolder, "assets/minecraft/models/item/${model.parent}/${model.texture.plainName()}.json")
        createFileAndParentsIfNotExists(file)
        if (model.display == null) {
            file.writeText(
                """
            {
                "parent": "item/handheld",
                "textures": {
                    "layer0": "item/${model.parent}/${model.texture.plainName()}"
                }
            }
        """.trimIndent()
            )
        } else { // ToDo represent these properties such as display with classes
            val fileContent = StringBuilder()
            fileContent.append(
                """
            {
                "parent": "item/handheld",
                "textures": {
                    "layer0": "item/${model.parent}/${model.texture.plainName()}"
                },""".trimIndent()
            )

            val display = model.display
            fileContent.append("\"display\": {")
            DisplayType.values().forEach { displayType ->
                val scale = display.scales[displayType]
                val rotation = display.rotations[displayType]
                val translation = display.translations[displayType]
                if (scale != null || rotation != null || translation != null) {
                    val displayJson = StringBuilder()
                    displayJson.append(rotation?.toJson() ?: "")
                    displayJson.append(translation?.toJson() ?: "")
                    displayJson.append(scale?.toJson() ?: "")
                    // remove last comma if exists
                    if (displayJson.last() == ',') {
                        displayJson.deleteCharAt(displayJson.length - 1)
                    }
                    fileContent.append(
                        """
                        "${displayType.asKey()}": {
                            $displayJson
                        },""".trimIndent()
                    )
                }
            }
            // remove last comma
            fileContent.deleteCharAt(fileContent.length - 1)
            fileContent.append(
                """
            }
        }
        """.trimIndent()
            )
            file.writeText(fileContent.toString())
        }

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
    val texture: Texture,
    val display: Display? = null
)

class Display( // ToDo one display per display type; display holds one scale, one rotation and one translation
    val scales: Map<DisplayType, Scale> = mapOf(),
    val rotations: Map<DisplayType, Rotation> = mapOf(),
    val translations: Map<DisplayType, Translation> = mapOf()
)

abstract class DisplayProperty(
    private val x: Double,
    private val y: Double,
    private val z: Double,
    private val jsonKey: String
) {
    fun toJson() = "\"$jsonKey\": [$x, $y, $z],"
}

abstract class IntDisplayProperty( //todo copied code
    private val x: Int,
    private val y: Int,
    private val z: Int,
    private val jsonKey: String
) {
    fun toJson() = "\"$jsonKey\": [$x, $y, $z],"
}

class Scale(
    x: Double,
    y: Double,
    z: Double
) : DisplayProperty(x, y, z, "scale") {
    companion object { fun zero() = Scale(0.0, 0.0, 0.0) }
}

class Rotation(
    x: Int,
    y: Int,
    z: Int
) : IntDisplayProperty(x, y, z, "rotation") {
    companion object { fun zero() = Rotation(0, 0, 0) }
}

class Translation(
    x: Double,
    y: Double,
    z: Double
) : DisplayProperty(x, y, z, "translation") {
    companion object { fun zero() = Translation(0.0, 0.0, 0.0) }
}

enum class DisplayType {
    THIRDPERSON_RIGHTHAND,
    THIRDPERSON_LEFTHAND,
    FIRSTPERSON_RIGHTHAND,
    FIRSTPERSON_LEFTHAND,
    GUI,
    HEAD,
    GROUND,
    FIXED;

    fun asKey(): String {
        return name.lowercase()
    }
}

class Texture(
    val name: String,
    val texture: BufferedImage // Todo Make particles configurable
) {
    init {
        if (!name.lowercase().endsWith(".png")) {
            throw IllegalArgumentException("Texture name must have a file extension of .png!")
        }
    }

    fun plainName(): String {
        return name.split(".")[0]
    }
}