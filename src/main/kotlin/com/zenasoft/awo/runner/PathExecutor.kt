package com.zenasoft.awo.runner

import com.zenasoft.awo.basic.AwoContext
import com.zenasoft.awo.basic.Entry
import com.zenasoft.awo.basic.IAction
import com.zenasoft.awo.basic.IEntry
import com.zenasoft.awo.basic.VirtualRootEntry
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Resolves a path and perform execution for each entry on the path.
 *
 * @author Zenas Chen
 * @version 2022-10-25 18:42
 */

private val logger: Logger = (LoggerFactory.getLogger(PathExecutor::class.java) as ch.qos.logback.classic.Logger)
    .also { it.level = ch.qos.logback.classic.Level.INFO }

class PathExecutor : KoinComponent {

    private val rootEntry: VirtualRootEntry by inject()

    private val ctx: AwoContext by inject()

    fun resolveActionFromPaths(paths: List<String>): IEntry<*> {
        var cursor: IEntry<*> = rootEntry
        paths.forEach {
            if (!cursor.subentryManager.containsSubentry(it)) {
                throw IllegalArgumentException("Path \"${formatPathsWithSlashes(cursor.paths + it)}\" does not exist.")
            }

            cursor = cursor.subentryManager.getSubentry(it)!!.resolveTarget()
        }

        if (cursor !is IAction) {
            // The last entry in the path list must be an `IAction`
            throw IllegalArgumentException("Path \"${formatPathsWithSlashes(cursor.paths)}\" is not an action.")
        }

        return cursor
    }

    /**
     * Execute any `IAction` along the path of the entry.
     */
    fun chainExecuteEntry(entry: IEntry<*>) {
        if (entry is VirtualRootEntry) {
            return
        }

        @Suppress("NAME_SHADOWING")
        val entry = entry as Entry
        chainExecuteEntry(entry.baseEntryInstance)

        if (entry is IAction) {
            logger.info("Executing action of path \"${formatPathsWithSlashes(entry.paths)}\"...")
            val action = entry as IAction
            action.prepare(ctx)
            action.preExecute(ctx)
            action.execute(ctx)
            action.postExecute(ctx)
        }
    }
}

private fun formatPathsWithSlashes(paths: List<String>): String {
    val sb = StringBuilder()
    paths.forEachIndexed { idx, it ->
        sb.append(it)
        if (idx < paths.count() - 1) {
            sb.append('/')
        }
    }

    return sb.toString()
}