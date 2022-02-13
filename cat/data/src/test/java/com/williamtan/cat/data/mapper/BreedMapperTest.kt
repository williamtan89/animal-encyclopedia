package com.williamtan.cat.data.mapper

import com.williamtan.cat.data.model.BreedImageModel
import com.williamtan.cat.data.model.BreedModel
import com.williamtan.common.enumtype.AnimalType
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BreedMapperTest {
    private lateinit var mapper: BreedMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        mapper = BreedMapperImpl(AnimalType.Cat)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `Given single model, mapTo should return correctly mapped BreedEntity object`() {
        val expectedId = "breed id"
        val expectedName = "breed name"
        val expectedImageUrl = "breed image url"
        val expectedTemperament = listOf("temperament 1", "temperament 2")
        val expectedWikipediaUrl = "wikipedia url"
        val expectedEnergyLevel = 3
        val expectedDescription = "breed description"

        val model = BreedModel(
            id = expectedId,
            name = expectedName,
            image = BreedImageModel(expectedImageUrl),
            referenceImageId = null,
            temperament = "temperament 1, temperament 2",
            wikipediaUrl = expectedWikipediaUrl,
            energyLevel = expectedEnergyLevel,
            description = expectedDescription
        )

        val result = mapper.mapTo(model)

        assertEquals(expectedId, result.id)
        assertEquals(expectedName, result.name)
        assertEquals(expectedImageUrl, result.imageUrl)
        assertEquals(expectedTemperament, result.temperamentList)
        assertEquals(expectedWikipediaUrl, result.wikipediaUrl)
        assertEquals(expectedEnergyLevel, result.energyLevel)
        assertEquals(expectedDescription, result.description)
        assertFalse(result.isFavorite)
    }

    @Test
    fun `Given imageUrl is null, Should return object with alternative url referenceImageId`() {
        val referenceImageId = "reference image id"
        val expectedImageUrl = BreedMapperImpl.ALTERNATIVE_IMAGE_URL.format(referenceImageId)

        val model = BreedModel(
            id = "breed id",
            name = "breed name",
            image = null,
            referenceImageId = referenceImageId,
            temperament = "temperament 1, temperament 2",
            wikipediaUrl = "wikipedia url",
            energyLevel = 3,
            description = "breed description"
        )

        val result = mapper.mapTo(model)

        assertEquals(expectedImageUrl, result.imageUrl)
    }

    @Test
    fun `Given imageUrl and referenceImageId is null, Should return object with null imageUrl`() {
        val referenceImageId = "reference image id"
        val expectedImageUrl = BreedMapperImpl.ALTERNATIVE_IMAGE_URL.format(referenceImageId)

        val model = BreedModel(
            id = "breed id",
            name = "breed name",
            image = null,
            referenceImageId = null,
            temperament = "temperament 1, temperament 2",
            wikipediaUrl = "wikipedia url",
            energyLevel = 3,
            description = "breed description"
        )

        val result = mapper.mapTo(model)

        assertNull(expectedImageUrl, result.imageUrl)
    }

    @Test
    fun `Given list of model, mapTo should return list of correctly mapped BreedEntity object`() {
        val expectedId = "breed id"
        val expectedName = "breed name"
        val expectedImageUrl = "breed image url"
        val expectedTemperament = listOf("temperament 1", "temperament 2")
        val expectedWikipediaUrl = "wikipedia url"
        val expectedEnergyLevel = 3
        val expectedDescription = "breed description"

        val model = BreedModel(
            id = expectedId,
            name = expectedName,
            image = BreedImageModel(expectedImageUrl),
            referenceImageId = null,
            temperament = "temperament 1, temperament 2",
            wikipediaUrl = expectedWikipediaUrl,
            energyLevel = expectedEnergyLevel,
            description = expectedDescription
        )

        val result = mapper.mapTo(listOf(model))

        assertTrue(result.size == 1)
        assertEquals(expectedId, result[0].id)
        assertEquals(expectedName, result[0].name)
        assertEquals(expectedImageUrl, result[0].imageUrl)
        assertEquals(expectedTemperament, result[0].temperamentList)
        assertEquals(expectedWikipediaUrl, result[0].wikipediaUrl)
        assertEquals(expectedEnergyLevel, result[0].energyLevel)
        assertEquals(expectedDescription, result[0].description)
        assertFalse(result[0].isFavorite)
    }
}