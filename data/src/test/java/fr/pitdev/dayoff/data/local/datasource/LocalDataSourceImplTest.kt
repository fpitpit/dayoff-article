package fr.pitdev.dayoff.data.local.datasource

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import fr.pitdev.dayoff.data.di.DatabaseModule
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import java.time.LocalDate
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class, sdk = [30], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
@UninstallModules(DatabaseModule::class)
class LocalDataSourceImplTest {
    private val dayOff11NovMet = DayOff(
        zone = Zone.METROPOLE,
        date = LocalDate.parse("2021-11-11"),
        name = "Armistice"
    )
    private val dayOff11NovAlsMos = DayOff(
        zone = Zone.ALSACE_MOSELLE,
        date = LocalDate.parse("2021-11-11"),
        name = "Armistice"
    )
    private val dayOff11NovAlsMos2020 = DayOff(
        zone = Zone.ALSACE_MOSELLE,
        date = LocalDate.parse("2020-11-11"),
        name = "Armistice"
    )
    private val dayOff = listOf(
        dayOff11NovMet,
        dayOff11NovAlsMos,
        dayOff11NovAlsMos2020
    )

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setUp() {
        hiltRule.inject()
    }


    @Inject
    lateinit var localDataSourceImpl: LocalDataSource


    @Test
    fun `GIVEN day off WHEN get all dayoff then return the expected dayoff`() = runBlocking {
        localDataSourceImpl.saveDayOffs(listOf(dayOff11NovMet))
        val result = localDataSourceImpl.getAllDayOff().first()
        assertEquals(dayOff11NovMet, result[0])

    }

    @Test
    fun `GIVEN day off WHEN get all dayoff by zone  then return the expected dayoff`() =
        runBlocking {
            localDataSourceImpl.saveDayOffs(dayOff)
            val result = localDataSourceImpl.getAllDayOff(zone = Zone.ALSACE_MOSELLE).first()
            assertEquals(dayOff11NovAlsMos, result[1])

        }

    @Test
    fun `GIVEN day off WHEN get all dayoff by zone and year then return the expected dayoff`() =
        runBlocking {
            localDataSourceImpl.saveDayOffs(dayOff)
            val result =
                localDataSourceImpl.getAllDayOff(zone = Zone.ALSACE_MOSELLE, year = 2020).first()
            assertEquals(dayOff11NovAlsMos2020, result[0])
        }

    @Test
    fun `GIVEN dayoff WHEN save all THEN must be saved into database`() = runBlockingTest {

        localDataSourceImpl.saveDayOffs(dayOff)
    }

}