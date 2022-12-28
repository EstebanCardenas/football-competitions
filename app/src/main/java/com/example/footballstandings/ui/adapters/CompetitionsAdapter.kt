package com.example.footballstandings.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.footballstandings.core.models.Competition
import com.example.footballstandings.databinding.LeagueItemBinding

class CompetitionsAdapter: ListAdapter<Competition, CompetitionsAdapter.LeaguesViewHolder>(DiffCallback) {
    class LeaguesViewHolder(
        private val binding: LeagueItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(competition: Competition) {
            Glide
                .with(binding.root)
                .load(competition.league.logo)
                .into(binding.logoImageView)
            binding.nameTextView.text = competition.league.name
            binding.typeTextView.text = competition.league.type
            val country = competition.country.name
            binding.flagImageView.visibility = View.GONE
            if (country != null) {
                binding.countryTextView.text = country
            } else {
                binding.countryTextView.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaguesViewHolder {
        val binding = LeagueItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LeaguesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaguesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Competition>() {
            override fun areItemsTheSame(oldItem: Competition, newItem: Competition): Boolean {
                return oldItem.league.id == newItem.league.id
            }

            override fun areContentsTheSame(oldItem: Competition, newItem: Competition): Boolean {
                return oldItem == newItem
            }
        }
    }
}