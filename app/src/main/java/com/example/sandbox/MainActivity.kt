package com.example.sandbox

import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        turn()
        val isFlashAvailable = applicationContext.packageManager
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
        if (!isFlashAvailable) {
            showNoFlashError()
        }
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = cameraManager.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun turn() {
        val img_id = findViewById<ImageView>(R.id.bulb_off)
        val bg_id = findViewById<ConstraintLayout>(R.id.clayout)
        val img_id2 = findViewById<ImageView>(R.id.bulb_on)
        img_id.setOnClickListener {
                var mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.lightswitch)
                mediaPlayer.start()
                bg_id.setBackgroundColor(Color.WHITE)
                img_id.visibility = View.INVISIBLE
                img_id2.visibility = View.VISIBLE
                switchFlashLight(status = true)
             //   mediaPlayer.release()
        }
        img_id2.setOnClickListener {
            var mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.lightswitch)
            mediaPlayer.start()
            bg_id.setBackgroundColor(Color.BLACK)
            img_id2.visibility = View.INVISIBLE
            img_id.visibility = View.VISIBLE
            switchFlashLight(status = false)
           // mediaPlayer.release()
        }

    }
    private fun showNoFlashError() {
        val alert = AlertDialog.Builder(this).create()
        alert.setTitle("Oops!")
        alert.setMessage("Flash not available in this device...")
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { _, _ -> finish() }
        alert.show()
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun switchFlashLight(status: Boolean) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, status)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}




