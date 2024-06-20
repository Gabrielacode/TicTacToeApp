package com.allstars.tictactoe

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.allstars.tictactoe.databinding.EachBoxBinding

class TTTAdapater( val onClickListener: OnClickListener) : RecyclerView.Adapter<TTTAdapater.Box>() {
lateinit var binding : EachBoxBinding
     inner class Box( val binding:EachBoxBinding) :ViewHolder(binding.root){
        fun bindButton( ){

            binding.root.setOnClickListener {
                //First we check if all the view are not empty
                onClickListener.onClick(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Box {
        binding  = EachBoxBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  Box(binding)
    }

    override fun getItemCount(): Int {
        return 9
    }

    override fun onBindViewHolder(holder: Box, position: Int) {
        holder.bindButton()
    }
}