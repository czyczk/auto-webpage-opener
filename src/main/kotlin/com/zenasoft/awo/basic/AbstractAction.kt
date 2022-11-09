package com.zenasoft.awo.basic

import kotlin.reflect.KClass

/**
 *
 * @author Zenas Chen
 * @version 2022-10-14 14:47
 */
abstract class AbstractAction(
        name: String,
        partialPath: String,
        baseEntryType: KClass<*>
) :
    Entry(name, partialPath, baseEntryType), IAction {

    override val entryType = "Action"

}