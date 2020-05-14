package com.github.tarcv.orderme.app.ui.di

import com.github.tarcv.orderme.app.di.AppComponent
import com.github.tarcv.orderme.app.di.AppModule
import com.github.tarcv.orderme.app.ui.tests.BaseTest
import dagger.Component
import javax.inject.Singleton

@Component(
        modules = [AppModule::class, AndroidTestModule::class]
)
@Singleton
interface AndroidTestAppComponent : AppComponent {
    fun injectBaseTest(test: BaseTest)
}
