package com.github.silbaram.svelte.server.threadpool

import com.github.silbaram.svelte.server.configuration.NettyServerTemplate
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


@EnableAsync
@Configuration
class NettyServerThreadPool(
    private val nettyServerConfigs: Map<String, NettyServerTemplate>
): AsyncConfigurer {

    val logger = LoggerFactory.getLogger(NettyServerThreadPool::class.java)

    @Value("\${svelte-app.termination.wait.time}")
    private val waitTime: Long = 0
    private val executorService: ExecutorService = Executors.newFixedThreadPool(nettyServerConfigs.keys.size)

    override fun getAsyncExecutor(): Executor {
        return executorService
    }

    @PreDestroy
    fun shutdown() {
        executorService.shutdown()
        if (!executorService.awaitTermination(waitTime, TimeUnit.SECONDS)) {
            logger.info("Executor did not terminate in the specified time.")
            val droppedTasks: List<Runnable> = executorService.shutdownNow()
            logger.info("Executor was abruptly shut down. ${droppedTasks.size} tasks will not be executed.")
        }
    }
}