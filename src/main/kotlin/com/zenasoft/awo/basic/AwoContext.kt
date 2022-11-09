package com.zenasoft.awo.basic

import org.openqa.selenium.WebDriver

/**
 *
 * @author Zenas Chen
 * @version 2022-10-14 14:17
 */
open class AwoContext(var webDriver: WebDriver) {

    var shouldCloseImmediately = true

}