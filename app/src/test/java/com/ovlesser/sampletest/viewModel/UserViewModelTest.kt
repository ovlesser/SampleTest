package com.ovlesser.sampletest.viewModel

import com.ovlesser.sampletest.model.Users
import org.junit.Test

import org.junit.Assert.*

class UserViewModelTest {
    val users = Users()

    @Test
    fun equal() {
        val user = users.users[0]
        assertEquals(user, users.users[0])
        assertNotEquals(user, users.users[1])
    }
}