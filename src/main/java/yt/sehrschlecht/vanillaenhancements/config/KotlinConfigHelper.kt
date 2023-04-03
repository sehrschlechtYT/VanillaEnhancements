package yt.sehrschlecht.vanillaenhancements.config

import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.superclasses

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class KotlinConfigHelper {
    companion object {
        // ToDo this is not a permanent solution
        private fun getFields(clazz: KClass<*>): Map<Field, KProperty<*>> {
            val fields = mutableMapOf<Field, KProperty<*>>()
            fields.putAll(clazz.declaredMemberProperties.map { Pair<Field, KProperty<*>>(clazz.java.getDeclaredField(it.name), it) })
            if (clazz.superclasses.isNotEmpty()) {
                fields.putAll(getFields(clazz.superclasses.first()))
            }
            return fields
        }

        fun getFields(jClass: Class<*>): Map<Field, KProperty<*>> {
            return getFields(jClass.kotlin)
        }

        @Deprecated("old")
        private fun getFieldValue(clazz: KClass<*>, module: VEModule, field: Field): Any? {
            if (module::class.declaredMemberProperties.none { it.name == field.name }) {
                if (clazz.superclasses.isNotEmpty()) {
                    return getFieldValue(clazz.superclasses.first(), module, field)
                }
                return null
            }
            val kotlinField = module::class.declaredMemberProperties.first { it.name == field.name }
            return kotlinField.getter.call(module)
        }

        @Deprecated("old")
        private fun getFieldValue(module: VEModule, field: Field): Any? {
            return getFieldValue(module::class, module, field)
        }

        fun getFieldValue(field: KProperty<*>, module: VEModule): Any? {
            return field.getter.call(module)
        }
    }
}