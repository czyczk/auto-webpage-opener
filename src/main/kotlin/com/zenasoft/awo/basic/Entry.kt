package com.zenasoft.awo.basic

import com.zenasoft.awo.manager.SubentryManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.qualifier.TypeQualifier
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 *
 * @author Zenas Chen
 * @version 2022-10-14 14:50
 */
open class Entry(
    /**
     * An entry should have a name. If multiple names are needed, create [Shortcut]s for this entry.
     */
    val name: String,

    /**
     * The partial path of this [Entry] instance. The path will get appended to the access path `paths`.
     */
    val partialPath: String,

    /**
     * The type of the entry from which it's extended.
     * If a specific base entry is specified, this instance will be a subentry of `baseEntry`.
     * Specifically, the type of the top level entry is the [VirtualRootEntry].
     */
    val baseEntryType: KClass<*>,
) : KoinComponent, IEntry<Entry> {

    final override val paths: List<String>

    val baseEntryInstance: IEntry<*>

    override val subentryManager: SubentryManager by inject()

    protected open val entryType = "Entry"

    init {
        if (!baseEntryType.isSubclassOf(IEntry::class)) {
            throw IllegalArgumentException("`baseEntryType` \"${baseEntryType.simpleName}\" is not a subclass of `IEntry`")
        }
        baseEntryInstance = get(TypeQualifier(baseEntryType))

        // Append the path based on `baseEntry.paths`
        paths = if (baseEntryType == VirtualRootEntry::class) {
            // If no base entry is provided, this entry is at the top level
            listOf(this.partialPath)
        } else {
            val baseEntryInstance = baseEntryInstance as Entry;
            baseEntryInstance.paths + partialPath
        }

        baseEntryInstance.subentryManager.addSubentry(this)
    }

    /**
     * An [Entry] resolves to itself.
     */
    override fun resolveTarget(): Entry {
        return this
    }

    override fun getBasicInfoString(): String {
        return "${this.entryType} { ${this.name} ( ${this.formatPathsWithSlashes()} ) }"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entry

        if (name != other.name) return false
        if (partialPath != other.partialPath) return false
        if (baseEntryType != other.baseEntryType) return false
        if (paths != other.paths) return false
        if (baseEntryInstance != other.baseEntryInstance) return false
        if (subentryManager != other.subentryManager) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + partialPath.hashCode()
        result = 31 * result + baseEntryType.hashCode()
        result = 31 * result + paths.hashCode()
        result = 31 * result + baseEntryInstance.hashCode()
        result = 31 * result + subentryManager.hashCode()
        return result
    }

}