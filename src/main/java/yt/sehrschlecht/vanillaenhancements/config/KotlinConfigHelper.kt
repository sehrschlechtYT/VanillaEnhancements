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

        fun getFieldValue(field: KProperty<*>, module: VEModule): Any? {
            return field.getter.call(module)
        }
    }
}