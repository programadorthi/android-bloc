package br.com.programadorthi.infinitelist

import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("posts")
    suspend fun posts(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<Post>
}
