package yt.sehrschlecht.vanillaenhancements.items.resourcepack

import org.bukkit.Material
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ResourcePackBuilder() {

    private val customModelDatas = mutableMapOf<Material, MutableList<CustomModelData>>()

    fun addCustomModelData(item: Material, data: CustomModelData) {
        if (customModelDatas.containsKey(item)) {
            customModelDatas[item]!!.add(data)
        } else {
            customModelDatas[item] = mutableListOf(data)
        }
    }

    fun addCustomModelData(item: Material, value: Int, texture: Texture) {
        addCustomModelData(item, CustomModelData(value, Model(item.key.key, texture)))
    }

    /*fun addCustomModelData(item: Material, value: Int) { // ToDo handle multiple datas for same item
        val file = File(buildFolder, "assets/minecraft/models/item/${item.key.key}.json")
        if (!file.exists()) {
            file.createNewFile()
        }
        file.writeText(
            """
            {
                "parent": "item/handheld",
                "textures": {
                    "layer0": "item/${item.key.key}"
                },
                "overrides": [
                    {
                        "predicate": {
                            "custom_model_data": $value
                        },
                        "model": "item/${item.key.key}_custom"
                    }
                ]
            }
        """.trimIndent()
        )
    }

     */

    fun run(buildFolder: File) {
        customModelDatas.forEach { (material, dataList) ->
            val file = File(buildFolder, "assets/minecraft/models/item/${material.key.key}_custom.json")
            if (!file.exists()) {
                file.createNewFile()
            }
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
                fileContents.append(
                    """
                    {
                        "predicate": {
                            "custom_model_data": ${data.value}
                        },
                        "model": "${data.model.parent}"
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
    }

    private fun saveTexture(texture: Texture, buildFolder: File) {
        val file = File(buildFolder, "assets/minecraft/textures/item/${texture.name}.png")
        if (!file.exists()) {
            file.createNewFile()
        }
        val image = texture.texture
        ImageIO.write(image, "png", file)
    }

    private fun saveModel(model: Model, buildFolder: File) {
        val file = File(buildFolder, "assets/minecraft/models/item/${model.parent}.json")
        if (!file.exists()) {
            file.createNewFile()
        }
        file.writeText(
            """
            {
                "parent": "item/handheld",
                "textures": {
                    "layer0": "item/${model.parent}/${model.texture}"
                }
            }
        """.trimIndent()
        )
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
)