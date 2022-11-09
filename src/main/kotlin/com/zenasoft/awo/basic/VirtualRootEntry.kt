package com.zenasoft.awo.basic

import com.zenasoft.awo.manager.SubentryManager

/**
 *
 * @author Zenas Chen
 * @version 2022-10-16 23:50
 */
open class VirtualRootEntry : IEntry<VirtualRootEntry> {

        override val paths: List<String> = listOf()

        override val subentryManager = SubentryManager()

        override fun resolveTarget(): VirtualRootEntry {
            return this
        }

    override fun getBasicInfoString(): String {
        return "Root"
    }

}