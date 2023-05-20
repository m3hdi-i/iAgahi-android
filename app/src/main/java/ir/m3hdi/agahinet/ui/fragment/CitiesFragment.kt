package ir.m3hdi.agahinet.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ir.m3hdi.agahinet.R
import ir.m3hdi.agahinet.databinding.FragmentCitiesBinding
import ir.m3hdi.agahinet.ui.adapter.CitiesAdapter
import ir.m3hdi.agahinet.ui.viewmodel.HomeViewModel

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewSelectedProvince.text=viewModel.getSelectedProvince()

        val goBack={ findNavController().navigate(R.id.action_cities_to_filters) }
        binding.topAppBar.setNavigationOnClickListener{
            goBack()
        }
        binding.fabOk.setOnClickListener {
            goBack()
        }

        binding.buttonSelectProvince.setOnClickListener {
            val modalBottomSheet = ProvinceSelectModal().apply {
                onProvinceSelectedListener={
                    Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
                }
            }
            modalBottomSheet.show(childFragmentManager, ProvinceSelectModal.TAG)
        }

        val citiesAdapter=CitiesAdapter(listOf("شهر 1","شهر 2","شهر 3","شهر 4"))
        binding.recyclerViewCities.adapter=citiesAdapter



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}