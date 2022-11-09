package com.zenasoft.awo.manager

import com.fasterxml.jackson.databind.ObjectMapper
import com.zenasoft.awo.model.stored.StoredCookieSet
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.openqa.selenium.Cookie
import org.openqa.selenium.WebDriver
import java.io.File

/**
 *
 * @author Zenas Chen
 * @version 2022-10-20 19:09
 */
class CookieManager(val paths: List<String>) : KoinComponent {

    private val objectMapper: ObjectMapper by inject()

    private var loadedCookieSet = setOf<Cookie>()

    fun loadCookies() {
        // TODO: use paths
        val file = File("./cookies.yaml")
        if (!file.exists()) {
            this.loadedCookieSet = setOf()
            return
        }

        val objStored = this.objectMapper.readValue(file, StoredCookieSet::class.java)

        this.loadedCookieSet = objStored.toSeleniumCookieSet()
    }

    fun saveCookiesFromWebDriver(driver: WebDriver) {
        this.loadedCookieSet = driver.manage().cookies
        saveLoadedCookies()
    }

    fun saveLoadedCookies() {
        if (this.loadedCookieSet.isEmpty()) {
            return
        }

        // TODO: use paths
        val file = File("./cookies.yaml")

        val objStored = StoredCookieSet.fromSeleniumCookieSet(this.loadedCookieSet)
        this.objectMapper.writeValue(file, objStored)
    }

    fun applyCookies(driver: WebDriver) {
        this.loadedCookieSet.forEach {
            driver.manage().addCookie(it)
        }
    }

}