package com.zenasoft.awo.basic

/**
 *
 * @author Zenas Chen
 * @version 2022-10-14 14:16
 */
interface IAction {

    fun prepare(ctx: AwoContext)

    fun preExecute(ctx: AwoContext)

    fun execute(ctx: AwoContext)

    fun postExecute(ctx: AwoContext)

}