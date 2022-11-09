import com.zenasoft.awo.config.CommonInstanceConfig
import com.zenasoft.awo.runner.Runner
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

fun main(args: Array<String>) {
    try {
        val instanceModule = CommonInstanceConfig.prepareInstanceModule()

        startKoin {
            modules(instanceModule)
        }

        Runner.run(args)
    } finally {
        stopKoin()
    }
}
