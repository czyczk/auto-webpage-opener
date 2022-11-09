package com.zenasoft.awo.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.zenasoft.awo.basic.AwoContext
import com.zenasoft.awo.basic.IEntry
import com.zenasoft.awo.basic.VirtualRootEntry
import com.zenasoft.awo.manager.CookieManager
import com.zenasoft.awo.manager.SubentryManager
import com.zenasoft.awo.runner.PathExecutor
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.onClose
import org.openqa.selenium.WebDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 * @author Zenas Chen
 * @version 2022-10-14 15:28
 */

private val logger: Logger = (LoggerFactory.getLogger(CommonInstanceConfig::class.java) as ch.qos.logback.classic.Logger)
    .also { it.level = ch.qos.logback.classic.Level.INFO }

class CommonInstanceConfig {
    companion object {
        fun prepareInstanceModule() : org.koin.core.module.Module {

            // ObjectMapper
            val objectMapper = ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))

            // VirtualRootEntry
            val virtualRootEntry = VirtualRootEntry()

            // Construct a module
            val instanceModule = module {
                single<WebDriver> {
                    // TODO: different drivers according to config
                    val options = EdgeOptions()
                    options.setBinary("""/Applications/Microsoft Edge Beta.app/Contents/MacOS/Microsoft Edge Beta""")
                    val prefs = mapOf("intl.accept_languages" to "lang=zh-CN,zh;q=0.9,zh-TW;q=0.8,en-US,en;q=0.7")
                    options.setExperimentalOption("prefs", prefs)
                    val driver = EdgeDriver(options)
                    // Don't set the log level here since it will override logback preferences.
                    // (driver as RemoteWebDriver).setLogLevel(Level.INFO)
                    driver.manage().window().maximize()
                    driver
                }.onClose {
                    logger.info("Exiting...")
                    it?.quit()
                }
                single {
                    // AwoContext
                    AwoContext(get())
                }
                single { objectMapper }
                single { virtualRootEntry }
                single(named<VirtualRootEntry>()) { virtualRootEntry } bind IEntry::class

                factory { SubentryManager() }
                factory { (paths: List<String>) -> CookieManager(paths) }
                factory { PathExecutor() }
            }

            return instanceModule
        }
    }
}