package com.zenasoft.awo.basic

/**
 *
 * @author Zenas Chen
 * @version 2022-10-14 14:12
 */
interface ILoginablePage {

    fun loadCookies(ctx: AwoContext)

    fun ensureLogin(ctx: AwoContext)

    fun saveCookies(ctx: AwoContext)
}