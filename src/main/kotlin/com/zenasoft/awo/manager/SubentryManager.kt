package com.zenasoft.awo.manager

import com.zenasoft.awo.basic.Entry

/**
 *
 * @author Zenas Chen
 * @version 2022-10-16 20:34
 */
open class SubentryManager {

    protected val subentryMap: MutableMap<String, Entry> = mutableMapOf()

    fun addSubentry(entry: Entry) {
        if (this.containsSubentry(entry)) {
            throw IllegalArgumentException("Path \"${entry.paths}\" already exists.")
        }

        subentryMap[entry.partialPath] = entry
    }

    fun containsSubentry(entry: Entry): Boolean {
        return this.containsSubentry(entry.partialPath)
    }

    fun containsSubentry(partialPath: String): Boolean {
        return this.subentryMap.contains(partialPath)
    }

    fun getSubentry(partialPath: String): Entry? {
        return this.subentryMap.get(partialPath)
    }

}