package fr.pitdev.dayoff.data.local.datasource

import dagger.hilt.android.testing.HiltAndroidTest
import fr.pitdev.dayoff.data.HiltTest
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
class LocalDataSourceImplTest : HiltTest() {


    private val dayOff11NovMet = DayOff(
        id = 1,
        zone = Zone.METROPOLE,
        date = LocalDate.parse("2021-11-11"),
        name = "Armistice"
    )
    private val dayOff11NovAlsMos = DayOff(
        id = 2,
        zone = Zone.ALSACE_MOSELLE,
        date = LocalDate.parse("2021-11-11"),
        name = "Armistice"
    )
    private val dayOff11NovAlsMos2020 = DayOff(
        id = 3,
        zone = Zone.ALSACE_MOSELLE,
        date = LocalDate.parse("2020-11-11"),
        name = "Armistice"
    )
    private val dayOff = listOf(
        dayOff11NovMet,
        dayOff11NovAlsMos,
        dayOff11NovAlsMos2020
    )


    @Inject
    lateinit var localDataSourceImpl: LocalDataSource


    @Test
    fun `GIVEN day off WHEN get all dayoff then return the expected dayoff`() = runTest {
        localDataSourceImpl.saveDayOffs(listOf(dayOff11NovMet))
        val result = localDataSourceImpl.getAllDayOff().first()
        assertEquals(dayOff11NovMet, result[0])

    }

    @Test
    fun `GIVEN day off WHEN get all dayoff by zone  then return the expected dayoff`() =
        runTest {
            localDataSourceImpl.saveDayOffs(dayOff)
            val result = localDataSourceImpl.getAllDayOff(zone = Zone.ALSACE_MOSELLE).first()
            assertEquals(dayOff11NovAlsMos, result[1])

        }

    @Test
    fun `GIVEN day off WHEN get all dayoff by zone and year then return the expected dayoff`() =
        runTest {
            localDataSourceImpl.saveDayOffs(dayOff)
            val result =
                localDataSourceImpl.getAllDayOff(zone = Zone.ALSACE_MOSELLE, year = 2020).first()
            assertEquals(dayOff11NovAlsMos2020, result[0])
        }

    @Test
    fun `GIVEN dayoff WHEN save all THEN must be saved into database`() = runBlocking {

        localDataSourceImpl.saveDayOffs(dayOff)
    }

}