package com.example.universal_scanner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.universal_scanner.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.mlkitBtn.setOnClickListener {
            val intent = Intent(this, MLKitActivity::class.java)
            startActivity(intent)
        }

        mainBinding.zxingBtn.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES) // Set this to scan 1D barcodes like Code39
            integrator.setPrompt("Scan a barcode")
            integrator.setCameraId(0) // Use the primary camera
            integrator.setBeepEnabled(false)
            integrator.setBarcodeImageEnabled(true)
            integrator.initiateScan()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                Log.d(
                    "Code39: ",
                    "onActivityResult() called with: requestCode = $requestCode, resultCode = $resultCode, data = ${result.contents}"
                )
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
