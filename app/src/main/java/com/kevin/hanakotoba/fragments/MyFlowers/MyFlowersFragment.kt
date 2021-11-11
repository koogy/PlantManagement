package com.kevin.hanakotoba.fragments.MyFlowers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.hanakotoba.R
import com.kevin.hanakotoba.data.FlowerViewModel
import com.kevin.hanakotoba.databinding.FragmentMyFlowersBinding
import com.kevin.hanakotoba.fragments.ResearchFlowers.ResearchFlowersFragment

class MyFlowersFragment : Fragment() {

    private lateinit var mFlowerViewModel : FlowerViewModel
    private lateinit var binding : FragmentMyFlowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_flowers,container,false)
        mFlowerViewModel = ViewModelProvider(this).get(FlowerViewModel::class.java)

        binding = FragmentMyFlowersBinding.bind(view)
        val adapter = MyFlowersAdapter()
        binding.rvMyFlowers.adapter = adapter
        binding.rvMyFlowers.layoutManager = LinearLayoutManager(requireContext())

        mFlowerViewModel.getAllFlowers().observe(viewLifecycleOwner, Observer { flower ->
            adapter.setFlower(flower)

        })


        binding.addFlower.setOnClickListener{
            val testFragment = ResearchFlowersFragment()
            val activity = requireActivity()

            activity.supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,testFragment)
                .addToBackStack(null).commit()
        }
        return view
    }

}