package com.github.tarcv.orderme.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.facebook.drawee.backends.pipeline.Fresco
import com.github.tarcv.orderme.app.di.AppComponent
import com.github.tarcv.orderme.app.di.AppModule
import com.github.tarcv.orderme.app.di.ComponentBuilder
import com.github.tarcv.orderme.app.di.ProdBuilder
import timber.log.Timber

open class App : Application() {
    companion object {
        fun tryGetTable(): Table? {
            val tableId = App.sharedPreferences.getString(App.TABLE_ID, "")
            return tryParseTable(tableId)
        }

        fun tryParseTable(tableId: String): Table? {
            if (tableId == "") {
                return null
            } else {
                return placeRegex.matchEntire(tableId)
                        ?.destructured
                        ?.let {
                            val (placeStr, tableStr) = it
                            try {
                                Table(placeStr.toInt(), tableStr.toInt())
                            } catch (e: NumberFormatException) {
                                Timber.w(e)
                                null
                            }
                        }
            }
        }

        lateinit var component: AppComponent
        lateinit var sharedPreferences: SharedPreferences
        val LOGIN_TOKEN = "token"
        val LOGIN_NAME = "login_name"
        val LOGIN_ID = "login_id"
        val LOGIN_USER_ID = "login_user_id"
        val FACEBOOK_IS_LOGGED = "facebook_login_key"

        /**
         * Value format: "<placeId>_<tableId>"
         */
        const val TABLE_ID = "table_id"
        val placeRegex = Regex("""(\d+)_(\d+)""")
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        buildComponent()

        Fresco.initialize(this)
        // TODO: clean Bitmap cache when the app is backgrounded

        sharedPreferences =
            applicationContext.getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)

//        Timber.plant(Timber.DebugTree())
    }

    fun buildComponent() {
        val componentBuilder = getComponentBuilder()
        component = componentBuilder.build(
            appModule = AppModule(this)
        )
    }
}

data class Table(
    val place: Int,
    val tableNumber: Int
)

private fun getComponentBuilder(): ComponentBuilder {
    return try {
        Class
            .forName("com.github.tarcv.orderme.app.ui.di.AndroidTestBuilder")
            .newInstance() as ComponentBuilder
    } catch (e: Exception) {
        return ProdBuilder()
    }
}