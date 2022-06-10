package fr.pitdev.dayoff

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import fr.pitdev.dayoff.common.base.BaseFragment.Companion.TAG
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.presentation.viewmodels.DayOffsViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val mainViewModel: MainViewModel by viewModels()
    private val dayOffsViewModel: DayOffsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Toast.makeText(
                    requireContext(),
                    mainViewModel.getHello("Fabrice"),
                    Toast.LENGTH_LONG
                )
                    .show()

                dayOffsViewModel.getDayOffs().collect {
                    when (it) {
                        is NetworkStatus.Success -> Log.d(TAG, "onViewCreated: $it")
                        is NetworkStatus.Loading -> Log.d(TAG, "Loading")
                        is NetworkStatus.Error -> Log.w(TAG, "${it.errorMessage}")
                    }

                }
            }


        }

    }
}