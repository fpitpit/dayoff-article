package fr.pitdev.dayoff.dayofffeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fr.pitdev.dayoff.common.base.BaseFragment
import fr.pitdev.dayoff.presentation.R
import fr.pitdev.dayoff.presentation.viewmodels.DayOffsViewModel

@AndroidEntryPoint
class DayOffFragment: BaseFragment(R.layout.fragment_dayoffs) {
    private val dayOffsViewModel: DayOffsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


}