package fr.pitdev.dayoff.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fr.pitdev.dayoff.common.base.BaseFragment
import fr.pitdev.dayoff.presentation.R
import fr.pitdev.dayoff.presentation.databinding.FragmentDayoffsBinding
import fr.pitdev.dayoff.presentation.viewmodels.DayOffsViewModel
import fr.pitdev.dayoff.presentation.viewmodels.DayfOffsState
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DayOffsFragment : BaseFragment(R.layout.fragment_dayoffs) {
    private val dayOffsViewModel: DayOffsViewModel by viewModels()
    private var _binding: FragmentDayoffsBinding? = null
    private val binding get() = _binding

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        dayOffsViewModel.refresh()
        binding?.swiperefresh?.isRefreshing = false
    }
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
            binding?.swiperefresh?.isRefreshing = false
            binding?.swiperefresh?.setOnRefreshListener(swipeRefreshListener)
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                swipeRefreshListener.onRefresh()
                dayOffsViewModel.uiStateAsFlow.collect {
                    bindView(it)
                }

            }
        }

    }

    private fun bindView(state: DayfOffsState) {
        when (state) {
            is DayfOffsState.Loading -> {
                binding?.loading?.visibility = View.VISIBLE
            }

            is DayfOffsState.Loaded -> {
                binding?.loading?.visibility = View.GONE
                binding?.dayoffList?.setData(state.dayOffs)

            }
            is DayfOffsState.Error -> view?.let {
                binding?.loading?.visibility = View.GONE
                Snackbar.make(
                    it,
                    state.message ?: state.throwable?.message ?: "",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding?.swiperefresh?.setOnRefreshListener(null)
        _binding = null
    }


}