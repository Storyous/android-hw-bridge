package com.storyous.eposapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.storyous.eposapp.databinding.ActivityMainBinding
import com.storyous.hwbridge.connectPrinter
import com.storyous.hwbridge.findAvailablePrinterApps
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        with(_binding) {
            print.setOnClickListener {
                lifecycleScope.launch {
                    val apps = findAvailablePrinterApps(this@MainActivity)

                    runCatching {
                        connectPrinter(
                            this@MainActivity,
                            apps.first().serviceInfo
                        ).use { print(viewToBitmap()) }
                    }.onFailure {
                        it.printStackTrace()
                    }
                    /*
                    Print(UUID.randomUUID().toString(), viewToBitmap())
                        .send(this@MainActivity, "com.storyous.hw.example")
                        
                     */
                }
            }
            pay.setOnClickListener {

            }
        }
    }

    private fun viewToBitmap(): Bitmap {
        return Bitmap.createBitmap(
            _binding.root.measuredWidth,
            _binding.root.measuredHeight,
            Bitmap.Config.RGB_565
        ).apply {
            val canvas = Canvas(this)
            canvas.drawColor(Color.WHITE)
            _binding.root.draw(canvas)
        }
    }
}