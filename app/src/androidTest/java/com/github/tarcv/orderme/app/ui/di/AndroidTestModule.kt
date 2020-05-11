package com.github.tarcv.orderme.app.ui.di

import com.github.tarcv.orderme.app.di.BaseUrl
import dagger.Module
import dagger.Provides
import io.fabric8.mockwebserver.DefaultMockServer
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Singleton

@Module
class AndroidTestModule {
    @BaseUrl
    @Provides
    fun provideBaseUrl(mockWebServerAndHost: MockWebServerAndHost) = mockWebServerAndHost.baseUrl

    @Singleton
    @Provides
    fun provideMockWebServer(
        mockWebServerAndHost: MockWebServerAndHost
    ) = mockWebServerAndHost.mockServer

    @Singleton
    @Provides
    fun provideMockWebServerAndHost(): MockWebServerAndHost {
        val mockServer = DefaultMockServer()
        val baseUrlHolder = AtomicReference<String>()
        val startThread = Thread(Runnable {
            mockServer.start()

            // hostName and port cannot be called on main thread, so call them in a separate thread
            baseUrlHolder.set("http://${mockServer.hostName}:${mockServer.port}")
        }, "MockWebServer starter")
        with(startThread) {
            isDaemon = true
            start()
            join()
        }
        return MockWebServerAndHost(
                mockServer,
                baseUrlHolder.get()
        )
    }
}

data class MockWebServerAndHost(
    val mockServer: DefaultMockServer,
    val baseUrl: String
)
