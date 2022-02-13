package com.williamtan.cat.data.repository

import com.williamtan.cat.data.CatApi
import com.williamtan.cat.data.mapper.BreedMapper
import com.williamtan.cat.data.model.BreedModel
import com.williamtan.common.entity.BreedEntity
import com.williamtan.domain.repository.CatRepository
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CatRepositoryTest {
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var repository: CatRepository

    @MockK
    private lateinit var api: CatApi

    @MockK
    private lateinit var mapper: BreedMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)

        repository = CatRepositoryImpl(
            api,
            mapper
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `Given empty result, getCatBreedList should return empty list`() {
        runTest {
            coEvery { api.searchByBreed(any(), any(), any()) } returns emptyList()

            val result = repository.getCatBreedList(null, 10, 0).first()

            assertTrue(result.isEmpty())
            verify { mapper wasNot Called }
        }
    }

    @Test
    fun `Given single result, getCatBreedList should return list with single data`() {
        runTest {
            val breedModel = mockk<BreedModel>()
            val expected = mockk<BreedEntity>()

            coEvery { api.searchByBreed(any(), any(), any()) } returns listOf(breedModel)
            every { mapper.mapTo(breedModel) } returns expected

            val result = repository.getCatBreedList(null, 10, 0).first()

            assertTrue(result.size == 1)
            assertEquals(expected, result[0])
            verify(exactly = 1) { mapper.mapTo(breedModel) }
        }
    }

    @Test
    fun `Given multiple result, getCatBreedList should return list with multiple data`() {
        runTest {
            val breedModel1 = mockk<BreedModel>()
            val expected1 = mockk<BreedEntity>()
            val breedModel2 = mockk<BreedModel>()
            val expected2 = mockk<BreedEntity>()

            coEvery { api.searchByBreed(any(), any(), any()) } returns listOf(
                breedModel1,
                breedModel2
            )
            every { mapper.mapTo(breedModel1) } returns expected1
            every { mapper.mapTo(breedModel2) } returns expected2

            val result = repository.getCatBreedList(null, 10, 0).first()

            assertTrue(result.size == 2)
            assertEquals(expected1, result[0])
            assertEquals(expected2, result[1])
            verify(exactly = 2) { mapper.mapTo(any<BreedModel>()) }
        }
    }

    @Test
    fun `Given empty result, searchByName should return empty list`() {
        runTest {
            coEvery { api.searchByName(any()) } returns emptyList()

            val result = repository.searchByName("breed name").first()

            assertTrue(result.isEmpty())
            verify { mapper wasNot Called }
        }
    }

    @Test
    fun `Given single result, searchByName should return list with single data`() {
        runTest {
            val breedModel = mockk<BreedModel>()
            val expected = mockk<BreedEntity>()

            coEvery { api.searchByName(any()) } returns listOf(breedModel)
            every { mapper.mapTo(breedModel) } returns expected

            val result = repository.searchByName("breed name").first()

            assertTrue(result.size == 1)
            assertEquals(expected, result[0])
            verify(exactly = 1) { mapper.mapTo(breedModel) }
        }
    }

    @Test
    fun `Given multiple result, searchByName should return list with multiple data`() {
        runTest {
            val breedModel1 = mockk<BreedModel>()
            val expected1 = mockk<BreedEntity>()
            val breedModel2 = mockk<BreedModel>()
            val expected2 = mockk<BreedEntity>()

            coEvery { api.searchByName(any()) } returns listOf(
                breedModel1,
                breedModel2
            )
            every { mapper.mapTo(breedModel1) } returns expected1
            every { mapper.mapTo(breedModel2) } returns expected2

            val result = repository.searchByName("breed name").first()

            assertTrue(result.size == 2)
            assertEquals(expected1, result[0])
            assertEquals(expected2, result[1])
            verify(exactly = 2) { mapper.mapTo(any<BreedModel>()) }
        }
    }

    @Test
    fun `Given no result, getCatBreedById should return null`() {
        runTest {
            coEvery { api.getBreedById(any()) } returns null

            val result = repository.getCatBreedById("breed id").first()

            assertNull(result)
            verify { mapper wasNot Called }
        }
    }

    @Test
    fun `Given data result, getCatBreedById should return correct breed entity`() {
        runTest {
            val breedModel = mockk<BreedModel>()
            val expected = mockk<BreedEntity>()

            coEvery { api.getBreedById(any()) } returns breedModel
            coEvery { mapper.mapTo(breedModel) } returns expected

            val result = repository.getCatBreedById("breed id").first()

            assertEquals(expected, result)
            verify(exactly = 1) { mapper.mapTo(breedModel) }
        }
    }
}