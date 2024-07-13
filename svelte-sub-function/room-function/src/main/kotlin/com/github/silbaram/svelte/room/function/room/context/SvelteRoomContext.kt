package com.github.silbaram.svelte.room.function.room.context

import com.github.silbaram.svelte.domain.room.Room
import org.springframework.stereotype.Component
import java.util.*

@Component
class SvelteRoomContext {
    private val roomMap: MutableMap<UUID, Room> = mutableMapOf()

    fun addRoom(roomId: UUID, room: Room) {
        roomMap[roomId] = room
    }

    fun getRoom(roomId: UUID): Room? {
        return roomMap[roomId]
    }

    fun removeRoom(roomId: UUID) {
        roomMap.remove(roomId)
    }
}