package com.zenasoft.awo.basic

import kotlin.reflect.KClass

/**
 *
 * @author Zenas Chen
 * @version 2022-10-14 15:06
 */
abstract class AbstractPage(name: String,
                            partialPath: String,
                            baseEntryType: KClass<*>,
                            val pageUrl: String,
                            val shouldCloseImmediately: Boolean = false,
    ) : AbstractAction(name, partialPath, baseEntryType) {

    override val entryType = "Page"

    override fun prepare(ctx: AwoContext) {
        ctx.webDriver.navigate().to(pageUrl)
    }

    override fun preExecute(ctx: AwoContext) {
    }

    override fun postExecute(ctx: AwoContext) {
    }

}