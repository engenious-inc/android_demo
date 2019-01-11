package com.github.tarcv.orderme.app.di

import dagger.Module
import dagger.Provides

@Module
class ProdModule {
    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "http://46.101.208.201:8282"
}