package com.example.footballstandings.core

import com.example.footballstandings.core.models.Competition
import com.example.footballstandings.core.network.CompetitionsNetworkDataSource
import javax.inject.Inject

class FsRepository @Inject constructor(
    private val leaguesNetworkDataSource: CompetitionsNetworkDataSource
) {
    suspend fun getCompetitions(): List<Competition> {
        return leaguesNetworkDataSource.service.getCompetitions().response
    }
}