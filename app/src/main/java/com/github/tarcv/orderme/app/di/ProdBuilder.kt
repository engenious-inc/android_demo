package com.github.tarcv.orderme.app.di

class ProdBuilder : ComponentBuilder {
    override fun build(appModule: AppModule): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(appModule)
            .build()
    }
}
