package com.kevin.hanakotoba

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.hanakotoba.databinding.FragmentMyFlowersBinding
import com.kevin.hanakotoba.adapters.MyFlowerAdapter
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.viewmodels.MyFlowerViewModel
import dagger.hilt.android.AndroidEntryPoint

//TODO: Enable watering button only when it's time
@AndroidEntryPoint
class MyFlowersFragment : Fragment() {

    private lateinit var myFlowerViewModel : MyFlowerViewModel

    private lateinit var binding : FragmentMyFlowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_flowers,container,false)

        myFlowerViewModel = ViewModelProvider(this).get(MyFlowerViewModel::class.java)

        binding = FragmentMyFlowersBinding.bind(view)
        val adapter = MyFlowerAdapter()
        binding.rvMyFlowers.adapter = adapter
        binding.rvMyFlowers.layoutManager = LinearLayoutManager(requireContext())

        myFlowerViewModel.flowerAndGarden.observe(viewLifecycleOwner, Observer { flowerAndGardens ->
            val onlyFlower = mutableListOf<Flower>()
            flowerAndGardens.map { flowerAndGarden -> onlyFlower.add(flowerAndGarden.flower) }
            adapter.setFlower(onlyFlower)
        })


        binding.addFlower.setOnClickListener{
            val testFragment = ResearchFlowersFragment()
            val activity = requireActivity()

            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,testFragment)
                .addToBackStack(null).commit()
        }
        return view
    }

}