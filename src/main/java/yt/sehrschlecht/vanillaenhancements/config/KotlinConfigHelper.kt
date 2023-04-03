package yt.sehrschlecht.vanillaenhancements.config

import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import java.lang.reflect.Field
import kotlin.reflect.full.declaredMemberProperties

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class KotlinConfigHelper {
    companion object {
        // ToDo this is not a permanent solution
        fun getFields(module: VEModule): List<Field> {
            return module::class.declaredMemberProperties.map { module::class.java.getDeclaredField(it.name) }
        }

        fun getFieldValue(module: VEModule, field: Field): Any? {
            val kotlinField = module::class.declaredMemberProperties.first { it.name == field.name }
            return kotlinField.getter.call(module)
        }
    }
}