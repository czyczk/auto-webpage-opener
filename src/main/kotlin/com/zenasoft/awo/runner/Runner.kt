package com.zenasoft.awo.runner

import com.zenasoft.awo.basic.AwoContext
import com.zenasoft.awo.basic.IEntry
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.openqa.selenium.NoSuchWindowException
import org.openqa.selenium.WebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author Zenas Chen
 * @version 2022-10-12 17:44
 */

private val logger: Logger = (LoggerFactory.getLogger(Runner::class.java) as ch.qos.logback.classic.Logger)
    .also { it.level = ch.qos.logback.classic.Level.INFO }

class Runner {
    companion object : KoinComponent {

        private val pathExecutor: PathExecutor by inject()

        private val ctx: AwoContext by inject()

        fun run(args: Array<String>) {
            val entry: IEntry<*>
            try {
                entry = pathExecutor.resolveActionFromPaths(args.toList())
            } catch (ex: IllegalArgumentException) {
                logger.error(ex.message)
                return
            }

            pathExecutor.chainExecuteEntry(entry)

            if (!ctx.shouldCloseImmediately) {
                waitForManualClosing(ctx.webDriver)
            }
        }

    }

}

private fun waitForManualClosing(driver: WebDriver) {
    var isClosed = false

    while (!isClosed) {
        try {
            driver.title
            Thread.sleep(200)
        } catch (ex: NoSuchWindowException) {
            // Window closed or inactive for longer than the timeout.
            // Should quit now.
            isClosed = true
        }
    }
}

