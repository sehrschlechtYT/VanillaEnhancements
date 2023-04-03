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
        private fun getFields(clazz: KClass<*>): Map<Field, KProperty<*>> {
            val fields = mutableMapOf<Field, KProperty<*>>()
            val classFields = mutableListOf<Map<Field, KProperty<*>>>()
            var currentClass: KClass<*>? = clazz
            while (currentClass != null && currentClass != Any::class) {
                classFields.add(currentClass.declaredMemberProperties.associateBy {
                    currentClass?.java?.getDeclaredField(
                        it.name
                    ) ?: throw IllegalStateException("Cannot get field for property ${it.name}")
                })
                currentClass = currentClass.superclasses.firstOrNull()
            }
            for (classField in classFields.reversed()) {
                fields.putAll(classField)
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