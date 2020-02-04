package com.graphqlq

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class MyApolloClient {

    companion object {
        val apolloClient = ApolloClient.builder()
                .serverUrl("https://api.graph.cool/simple/v1/cjsjynwwo0fv001765jvkwy1m")
                .okHttpClient(
                        OkHttpClient.Builder()
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(30, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS)
                                .build()
                )
                .build()

    }
    /*companion object {

        var baseUrl : String = "https://api.graph.cool/simple/v1/cjsjynwwo0fv001765jvkwy1m"
        var apolloClient : ApolloClient? = null
        fun getApolloClients() : ApolloClient?{

            var httpLoggingInterceptor : HttpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            var okHttpClient : OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

            apolloClient = ApolloClient.builder()
                    .serverUrl(baseUrl)
                    .okHttpClient(okHttpClient)
                    .build()

            return apolloClient
        }

    }*/

}