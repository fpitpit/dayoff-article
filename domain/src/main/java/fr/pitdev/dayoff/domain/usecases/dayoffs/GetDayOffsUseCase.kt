package fr.pitdev.dayoff.domain.usecases.dayoffs

import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import fr.pitdev.dayoff.domain.repository.DayOffRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetDayOffsUseCase @Inject constructor(private val dayOffRepository: DayOffRepository) {

    operator fun invoke(
        zone: Zone? = Zone.METROPOLE,
        year: Int? = LocalDate.now().year,
        refresh: Boolean = false
    ): Flow<NetworkStatus<List<DayOff>>> {
        return dayOffRepository.getDayOffs(zone ?: Zone.METROPOLE, year ?: LocalDate.now().year, refresh = refresh)
    }
}