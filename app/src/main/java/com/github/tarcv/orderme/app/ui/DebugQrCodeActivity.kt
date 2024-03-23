package com.github.tarcv.orderme.app.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.tarcv.orderme.app.databinding.DebugQrCodeBinding

class DebugQrCodeActivity : AppCompatActivity() {

    private lateinit var binding: DebugQrCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DebugQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra(QR_RESULT_KEY,
                    binding.qrCodeText.text.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.errorButton.setOnClickListener {
            val resultIntent = Intent()
            val errorText = binding.qrCodeText.text.let {
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
