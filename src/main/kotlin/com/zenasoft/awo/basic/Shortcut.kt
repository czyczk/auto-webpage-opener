package com.zenasoft.awo.basic

import com.zenasoft.awo.manager.SubentryManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.qualifier.TypeQualifier
import kotlin.reflect.KClass

/**
 *
 * @author Zenas Chen
 * @version 2022-10-16 20:37
 */
open class Shortcut(
    name: String,
    partialPath: String,
    baseEntryType: KClass<*>,
    protected val targetType: KClass<*>,
) : KoinComponent, Entry(name, partialPath, baseEntryType) {

    override val subentryManager: SubentryManager by inject()

    override val entryType = "Shortcut"

    /**
     * A [Shortcut] resolves to an instance of [IEntry].
     * For example, the resolved target can be an [Entry], an [IAction] instance or another [Shortcut].
     */
    override fun resolveTarget(): Entry {
        return get(TypeQualifier(targetType))
    }

    override fun getBasicInfoString(): String {
        return "Entry { ${this.name} ( ${this.formatPathsWithSlashes()} ) }"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Shortcut

        if (targetType != other.targetType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + targetType.hashCode()
        return result
    }

}