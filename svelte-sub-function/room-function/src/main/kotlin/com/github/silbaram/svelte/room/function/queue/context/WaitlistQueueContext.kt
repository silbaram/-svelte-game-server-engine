package com.github.silbaram.svelte.room.function.queue.context

import com.github.silbaram.svelte.domain.user.User
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@Component
class WaitlistQueueContext {
    private val sink = Sinks.many().multicast().onBackpressureBuffer<User>()
    val standbyUsersFlux: Flux<User> = sink.asFlux()

    fun addStandbyUser(user: User) {
        sink.tryEmitNext(user)
    }
}