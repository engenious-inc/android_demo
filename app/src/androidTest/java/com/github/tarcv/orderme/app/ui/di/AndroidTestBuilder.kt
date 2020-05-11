package com.github.tarcv.orderme.app.ui.di

import com.github.tarcv.orderme.app.di.AppComponent
import com.github.tarcv.orderme.app.di.AppModule
import com.github.tarcv.orderme.app.di.ComponentBuilder

class AndroidTestBuilder : ComponentBuilder {
    override fun build(appModule: AppModule): AppComponent {
        return DaggerAndroidTestAppComponent.builder()
                .appModule(appModule)
                .build()
    }
}
