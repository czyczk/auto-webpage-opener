package com.zenasoft.awo.model.stored

import org.openqa.selenium.Cookie

/**
 *
 * @author Zenas Chen
 * @version 2022-10-13 17:58
 */
open class StoredCookieSet() {
    lateinit var cookieSet: Set<StoredCookie>

    fun toSeleniumCookieSet(): Set<Cookie> {
        return this.cookieSet
            .map { it.toSeleniumCookie() }
            .toSet()
    }

    companion object {
        fun fromSeleniumCookieSet(cookies: Set<Cookie>): StoredCookieSet {
            val ret = StoredCookieSet()
            ret.cookieSet = cookies.map { StoredCookie.fromSeleniumCookie(it) }.toSet()

            return ret
        }
    }
}