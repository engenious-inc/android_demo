package com.github.tarcv.orderme.app.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.tarcv.orderme.app.R
import kotlinx.android.synthetic.main.debug_qr_code.*

class DebugQrCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.debug_qr_code)

        submitButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra(QR_RESULT_KEY,
                    qrCodeText.text.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        errorButton.setOnClickListener {
            val resultIntent = Intent()
            val errorText = qrCodeText.text.let {
                if (it.isNullOrEmpty()) {
                    "Simulated error"
                } else {
                    it
                }
            }
            resultIntent.putExtra(QR_RESULT_ERROR_KEY,
                    errorText.toString())
            setResult(Activity.RESULT_CANCELED, resultIntent)
            finish()
        }
    }
}
