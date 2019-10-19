package com.ovlesser.sampletest.viewModel

import com.ovlesser.sampletest.model.Photos
import org.junit.Test

import org.junit.Assert.*

class PhotoViewModelTest {
    val photos = Photos()

    @Test
    fun equal() {
        val photo = photos.photos[0]
        assertEquals(photo, photos.photos[0])
        assertNotEquals(photo, photos.photos[1])
        assertNotEquals(photo, photos.photos[2])
        assertNotEquals(photo, photos.photos[3])
        assertNotEquals(photo, photos.photos[4])
    }
}