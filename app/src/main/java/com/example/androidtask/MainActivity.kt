package com.example.androidtask

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.*

//import org.junit.runner.RunWith

class MainActivity : AppCompatActivity(), MultiplePermissionsListener {
    private lateinit var inputImage: InputImage
    private val cameraRequest: Int=10
    private lateinit var fab: FloatingActionButton
    private  lateinit var bottom_nav:BottomNavigationView
    private lateinit var navController: NavController
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
                setOf(
                        R.id.fragment_1,
                        R.id.fragment_2,
                        R.id.fragment_3,
                        R.id.fragment_4,


                )
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        bottom_nav=findViewById<BottomNavigationView>(R.id.bottom_navigation)
        fab=findViewById<FloatingActionButton>(R.id.camera_button)
        fab.setOnClickListener(View.OnClickListener {

                Dexter.withActivity(this)
                        .withPermissions(listOf(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE))
                        .withListener(this)
                        .check()

        })
// 2
        navController = Navigation.findNavController(this, R.id.container)

// 3

        bottom_nav.setupWithNavController(navController)

// 4
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest) {
            val photo: Bitmap =MediaStore.Images.Media.getBitmap(this.getContentResolver(), data?.data);
            inputImage=InputImage.fromBitmap(photo,0)
           GlobalScope.launch(Dispatchers.IO) {  recognizeText(inputImage)}
//            imageView.setImageBitmap(photo)
        }
    }

   suspend private fun recognizeText(inputImage: InputImage) {


        val recognizer = TextRecognition.getClient()
    return withContext(Dispatchers.IO)
    {

        val result = recognizer.process(inputImage)
                .addOnSuccessListener { visionText ->
                    val resultText = visionText.text.toString()
                    Toast.makeText(applicationContext, "Success" + resultText, Toast.LENGTH_LONG).show()
                    Log.d("Result Text", resultText)
                    // Task completed successfully
//                    visionText.textBlocks
//                    val resultText = visionText.text
//                    Log.d("Result Text",resultText)
//                    for (block in visionText.textBlocks) {
//                        val blockText = block.text
//                        Log.d("Block text",blockText)
//                        val blockCornerPoints = block.cornerPoints
//                        val blockFrame = block.boundingBox
//                        for (line in block.lines) {
//                            val lineText = line.text
//                            Log.d("Line text",lineText)
//                            val lineCornerPoints = line.cornerPoints
//                            val lineFrame = line.boundingBox
//                            for (element in line.elements) {
//                                val elementText = element.text
//                                Log.d("Element text",elementText)
//                                val elementCornerPoints = element.cornerPoints
//                                val elementFrame = element.boundingBox
//                            }
//                        }
//                    }
//                    // ...
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                    Log.d("Exception", e.localizedMessage)
                }

    }
}

    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        if(report?.areAllPermissionsGranted() as Boolean) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, cameraRequest)
        }
        else if(report?.isAnyPermissionPermanentlyDenied)
        {
            Snackbar.make(findViewById(R.id.layout),"${ report.deniedPermissionResponses.toString() } } permission is needed to scan doubts",3000)
        }

    }

    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
        token?.continuePermissionRequest()
        Snackbar.make(findViewById(R.id.layout),"Camera permission is needed to scan doubts",3000)
    }

//        for (block in result.result?.textBlocks) {
//            val blockText = block.text
//            val blockCornerPoints = block.cornerPoints
//            val blockFrame = block.boundingBox
//            for (line in block.lines) {
//                val lineText = line.text
//                val lineCornerPoints = line.cornerPoints
//                val lineFrame = line.boundingBox
//                for (element in line.elements) {
//                    val elementText = element.text
//                    val elementCornerPoints = element.cornerPoints
//                    val elementFrame = element.boundingBox
//                }
//            }



    }
