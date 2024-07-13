package com.github.silbaram.svelte.room.function.service

import com.github.silbaram.svelte.domain.domain_service.room.broadcastMessage
import com.github.silbaram.svelte.domain.domain_service.room.toRoom
import com.github.silbaram.svelte.room.function.queue.context.WaitlistQueueContext
import com.github.silbaram.svelte.room.function.room.context.SvelteRoomContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class MatchmakingService(
    private val waitlistQueueContext: WaitlistQueueContext,
    private val svelteRoomContext: SvelteRoomContext
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
                logger.info("새로운 대기 사용자 추가: $user")
            }
            .bufferTimeout(maxWaitlistSize, Duration.ofSeconds(maxWaitlistTime))
            .subscribe { users ->
                if (users.size == maxWaitlistSize) {
                    logger.info("매칭 되었습니다. $users")

                    val room = users.toRoom()
                    svelteRoomContext.addRoom(room.roomId, room)
                    //TODO 매칭 된 결과를 코드화 해야함
                    room.broadcastMessage("매칭")
                } else {
                    //TODO 설정된 시간이 지나도 매칭이 안될때 실패 결과 클라이언트로 전송
                    logger.info("매칭이 실패 되었습니다.")
                    //TODO 매칭 실패 된 결과를 코드화 해야함
                    users.forEach { user ->
                        user.channel.writeAndFlush("실패")
                    }
                }
            }
    }
}