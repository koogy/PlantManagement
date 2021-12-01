package com.kevin.hanakotoba.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.kevin.hanakotoba.R
import com.kevin.hanakotoba.UserFlowerDescriptionFragment
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.ItemLayoutBinding
import java.util.*


class MyFlowerAdapter : RecyclerView.Adapter<MyFlowerAdapter.VH>() {

    private var flowerList = emptyList<Flower>()

    inner class VH (val binding : ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentItem = flowerList[position]
        holder.binding.userFlowerName.text = currentItem.name


        if(currentItem.shouldBeWatered()) {
            holder.binding.waterButton.setOnClickListener {
                Toast.makeText(
                    holder.itemView.context,
                    "Watered : ${currentItem.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, currentItem.name, Toast.LENGTH_SHORT).show();
        }

    }

    override fun getItemCount(): Int {
        return flowerList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFlower(flower :List<Flower> ){
        this.flowerList = flower
        notifyDataSetChanged()
    }
}