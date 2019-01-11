package com.github.tarcv.orderme.app.di

interface ComponentBuilder {
    fun build(appModule: AppModule): AppComponent
}