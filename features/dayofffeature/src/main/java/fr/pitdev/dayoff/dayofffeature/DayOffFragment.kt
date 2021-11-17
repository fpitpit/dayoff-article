package fr.pitdev.dayoff.dayofffeature

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fr.pitdev.dayoff.common.base.BaseFragment
import fr.pitdev.dayoff.presentation.viewmodels.DayOffsViewModel

@AndroidEntryPoint
class DayOffFragment: BaseFragment() {
    private val dayOffsViewModel: DayOffsViewModel by viewModels()

}