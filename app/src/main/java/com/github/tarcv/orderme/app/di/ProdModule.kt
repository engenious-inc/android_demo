package com.github.tarcv.orderme.app.di

import dagger.Module
import dagger.Provides

@Module
class ProdModule {
    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "http://18.118.12.123:3000/"
}