package com.github.tarcv.orderme.app.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.blikoon.qrcodescanner.QrCodeActivity
import com.github.tarcv.orderme.app.App
import com.github.tarcv.orderme.app.App.Companion.tryParseTable
import com.github.tarcv.orderme.app.BuildConfig
import com.github.tarcv.orderme.app.Table
import com.github.tarcv.orderme.app.ui.home.PlacesProvider
import com.github.tarcv.orderme.core.data.entity.Place

fun Fragment.startQrCodeActivity() {
    val qrActivity: Class<out Activity> = if (BuildConfig.DEBUG) {
        DebugQrCodeActivity::class.java
    } else {
        QrCodeActivity::class.java
    }
    val i = Intent(this.context, qrActivity)

    startActivityForResult(i, REQUEST_CODE_QR_SCAN)
}

fun saveOrErrorQrCode(
    context: Context,
    provider: PlacesProvider,
    resultCode: Int,
    data: Intent?
): Pair<Place?, Table>?
{
    if (resultCode == Activity.RESULT_OK) {
        val result = data?.getStringExtra(QR_RESULT_KEY)
        val parsedTable = result?.let { tryParseTable(it) }
        if (parsedTable != null) {
            val placeObject = provider.tryGetKnownPlace(parsedTable.place)
            if (placeObject != null) {
                App.sharedPreferences.edit().putString(App.TABLE_ID, result).apply()
                return Pair(placeObject, parsedTable)
            } else {
                alertWrongQrCode(context, provider.unknownPlaceErrorMessage)
            }
        } else {
            alertWrongQrCode(context, "QR Code is not compatible with OrderMe (not a place code?)")
        }
    } else {
        val result = data?.getStringExtra(QR_RESULT_ERROR_KEY)
        result?.let {
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.apply {
                setTitle("Scan Error")
                setMessage("QR Code could not be scanned")
                setButton(AlertDialog.BUTTON_NEUTRAL, "OK", { dialog, _ -> dialog.dismiss() })
                show()
            }
        }
    }
    return null
}

fun saveOrErrorQrCodeForTable(
    context: Context,
    place: Place,
    resultCode: Int,
    data: Intent?
): Table? {
    val provider = object : PlacesProvider {
        override val unknownPlaceErrorMessage =
                "Wrong QR code or a code from a different place"

        override fun tryGetKnownPlace(id: Int): Place? {
            if (id == place.id) {
                return place
            } else {
                return null
            }
        }
    }
    return saveOrErrorQrCode(context, provider, resultCode, data)?.second
}

fun alertWrongQrCode(context: Context, msg: String) {
    AlertDialog.Builder(context).create().run({
        setTitle("Scan Error")
        setMessage(msg)
        setButton(AlertDialog.BUTTON_NEUTRAL, "OK", { dialog, _ -> dialog.dismiss() })
        show()
    })
}

const val REQUEST_CODE_QR_SCAN = 101
const val QR_RESULT_KEY = "com.blikoon.qrcodescanner.got_qr_scan_relult"
const val QR_RESULT_ERROR_KEY = "com.blikoon.qrcodescanner.error_decoding_image"
