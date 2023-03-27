package com.bashirli.artbooknextlvl.model

data class RetrofitData
    (
        val total:Int,
        val totalHits:Int,
        val hits:List<HitList>
            )