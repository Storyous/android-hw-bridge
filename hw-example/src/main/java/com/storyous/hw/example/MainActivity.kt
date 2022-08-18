package com.storyous.hw.example

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.storyous.hw.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    companion object {
        private const val ARG_PRINT_URI = "printUri"

        @JvmStatic
        fun start(context: Context, bitmapUri: Uri) {
            val starter = Intent(context, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(ARG_PRINT_URI, bitmapUri)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        savedInstanceState?.getSerializable("")

        intent?.getParcelableExtra<Uri>(ARG_PRINT_URI)?.also {
            _binding.print.setImageBitmap(BitmapFactory.decodeFile(it.path))
        }
    }
}
