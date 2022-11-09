package com.zenasoft.awo.basic

import com.zenasoft.awo.manager.SubentryManager

/**
 *
 * @author Zenas Chen
 * @version 2022-10-14 14:58
 */
interface IEntry<out ResolvedTargetType : IEntry<ResolvedTargetType>> {

    val paths: List<String>

    val subentryManager: SubentryManager

    /**
     * Resolves the target that the entry is pointed to.
     * Useful for shortcuts to resolve to the actual target entry.
     */
    fun resolveTarget(): ResolvedTargetType

    fun getBasicInfoString(): String

    fun formatPathsWithSlashes(): String {
        val sb = StringBuilder()
        this.paths.forEachIndexed { idx, it ->
            sb.append(it)
            if (idx < paths.count() - 1) {
                sb.append('/')
            }
        }

        return sb.toString()
    }

}