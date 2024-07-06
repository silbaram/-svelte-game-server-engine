package com.github.silbaram.svelte.room.function.service

import com.github.silbaram.svelte.room.function.queue.context.WaitlistQueueContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class MatchmakingService(
    private val waitlistQueueContext: WaitlistQueueContext,
): ApplicationListener<ContextRefreshedEvent> {
    private val logger = LoggerFactory.getLogger(MatchmakingService::class.java)

    @Value("\${svelte-app.max-waitlist-size}")
    val maxWaitlistSize: Int = 2
    @Value("\${svelte-app.max-waitlist-time}")
    val maxWaitlistTime: Long = 2

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        waitlistQueueContext.standbyUsersFlux
            .doOnNext { user ->
                // 데이터가 추가될 때마다 로그로 출력하여 확인
                logger.info("새로운 대기 사용자 추가: ${user}")
            }
            .bufferTimeout(maxWaitlistSize, Duration.ofSeconds(maxWaitlistTime))
            .subscribe { users ->
                if (users.size == maxWaitlistSize) {
                    //TODO Room 객체 싱성 및 매칭 성공 결과 클라이언트로 전송
                    logger.info("매칭이 성사 되었습니다.")
                    users.forEach { user ->
                        user.channel.writeAndFlush("매칭")
                    }

                } else {
                    //TODO 설정된 시간이 지나도 매칭이 안될때 실패 결과 클라이언트로 전송
                    logger.info("매칭이 실패 되었습니다.")
                    users.forEach { user ->
                        user.channel.writeAndFlush("실패")
                    }
                }
            }
    }
}