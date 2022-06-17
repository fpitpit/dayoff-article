package fr.pitdev.dayoff.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fr.pitdev.dayoff.common.base.BaseFragment
import fr.pitdev.dayoff.presentation.R
import fr.pitdev.dayoff.presentation.databinding.FragmentDayoffsBinding
import fr.pitdev.dayoff.presentation.viewmodels.DayOffsViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DayOffsFragment : BaseFragment(R.layout.fragment_dayoffs) {
    private val dayOffsViewModel: DayOffsViewModel by viewModels()
    private var _binding: FragmentDayoffsBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayoffsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dayOffsViewModel.uiState.collect {
                    Log.d(TAG, "onViewCreated: $it")
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}