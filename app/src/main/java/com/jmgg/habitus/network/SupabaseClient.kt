package com.jmgg.habitus.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SupabaseClient{

    private const val BASE_URl = "https://zgunsowtbtynmeffqati.supabase.co/rest/v1/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}