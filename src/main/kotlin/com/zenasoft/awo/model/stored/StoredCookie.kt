package com.zenasoft.awo.model.stored

import org.openqa.selenium.Cookie
import java.util.*

/**
 * Deserialization needs an empty constructor to work.
 * This class provides an empty constructor and acts as an adaptor of {@link Cookie}.
 *
 * @author Zenas Chen
 * @version 2022-10-14 09:47
 */
open class StoredCookie() {
    lateinit var name: String

    lateinit var value: String

    var path: String? = null

    var domain: String? = null

    var expiry: Date? = null

    var isSecure: Boolean = false

    var isHttpOnly: Boolean = false

    var sameSite: String? = null

    fun toSeleniumCookie(): Cookie {
        return Cookie(name, value, domain, path, expiry, isSecure, isHttpOnly, sameSite)
    }

    companion object {
        fun fromSeleniumCookie(cookie: Cookie): StoredCookie {
            val ret = StoredCookie()
            ret.name = cookie.name
            ret.value = cookie.value
            ret.domain = cookie.domain
            ret.path = cookie.path
            ret.expiry = cookie.expiry
            ret.isSecure = cookie.isSecure
            ret.isHttpOnly = cookie.isHttpOnly
            ret.sameSite = cookie.sameSite

            return ret
        }
    }
}