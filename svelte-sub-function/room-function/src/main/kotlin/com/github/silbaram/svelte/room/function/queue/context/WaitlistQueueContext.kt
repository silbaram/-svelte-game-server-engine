package com.github.silbaram.svelte.room.function.queue.context

import com.github.silbaram.svelte.domain.user.User
import org.springframework.stereotype.Component
import java.util.concurrent.LinkedBlockingQueue

@Component
class WaitlistQueueContext {
    private val standbyUsersQueue = LinkedBlockingQueue<User>()

    fun addStandbyUsers(user: User) {
        standbyUsersQueue.add(user)
    }

    fun getStandbyUsers(): User {
        return standbyUsersQueue.poll()
    }
}