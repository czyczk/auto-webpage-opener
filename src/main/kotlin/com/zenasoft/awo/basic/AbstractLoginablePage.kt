package com.zenasoft.awo.basic

import com.zenasoft.awo.manager.CookieManager
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

/**
 *
 * @author Zenas Chen
 * @version 2022-10-14 14:46
 */
abstract class AbstractLoginablePage(
    name: String,
    partialPath: String,
    baseEntryType: KClass<*>,
    pageUrl: String,
    shouldCloseImmediately: Boolean = false,
) : AbstractPage(name, partialPath, baseEntryType, pageUrl, shouldCloseImmediately), ILoginablePage {

    private val cookieManager: CookieManager by inject { parametersOf(this.paths) }

    override val entryType = "LoginablePage"

    override fun prepare(ctx: AwoContext) {
        super.prepare(ctx)
        // Cookies must be applied after navigating to the page,
        // otherwise an `InvalidCookieDomainException` would be thrown
        // since the current domain would be "data;" (Selenium startup URL)
        loadCookies(ctx)
    }

    override fun preExecute(ctx: AwoContext) {
        super.preExecute(ctx)
        ensureLogin(ctx)
        saveCookies(ctx)
    }

    override fun loadCookies(ctx: AwoContext) {
        this.cookieManager.loadCookies()
        this.cookieManager.applyCookies(ctx.webDriver)
    }

    override fun saveCookies(ctx: AwoContext) {
        this.cookieManager.saveCookiesFromWebDriver(ctx.webDriver)
    }

}