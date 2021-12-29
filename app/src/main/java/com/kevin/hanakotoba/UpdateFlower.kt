package com.kevin.hanakotoba

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.databinding.UpdateFlowerFragmentBinding
import com.kevin.hanakotoba.viewmodels.UpdateFlowerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap

import android.graphics.drawable.BitmapDrawable
import java.io.FileOutputStream


@AndroidEntryPoint
class UpdateFlower : Fragment() {

    private lateinit var viewModel: UpdateFlowerViewModel
    private lateinit var binding: UpdateFlowerFragmentBinding
    private lateinit var imageFlower: String
    private lateinit var flower: Flower

    private val selectPictureLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.imageView.setImageURI(it)

        val draw = binding.imageView.drawable as BitmapDrawable
        val bitmap = draw.bitmap

        var outStream: FileOutputStream? = null
        val sdCard = this.requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val dir = File(sdCard!!.absolutePath)
        dir.mkdirs()
        val fileName = String.format("%d.jpg", System.currentTimeMillis())
        val outFile = File(dir, fileName)
        outStream = FileOutputStream(outFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        outStream.flush()
        outStream.close()
        imageFlower = outFile.absolutePath
    }

    private var tempImageUri: Uri? = null
    private var tempImageFilePath = ""
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){ success ->
        if(success){
            binding.imageView.setImageURI(tempImageUri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.update_flower_fragment, container, false)
        binding = UpdateFlowerFragmentBinding.bind(view)

        val bundle = arguments
        flower = bundle!!.getSerializable("flower") as Flower

        //FILL FORM
        binding.name.setText(flower.name)
        binding.latinName.setText(flower.latinName)
        binding.description.setText(flower.description)
        binding.wateringInterval.setText(flower.wateringInterval.toString())

        imageFlower = flower.imageUrl

        val imgFile = File(flower.imageUrl)
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            binding.imageView.setImageBitmap(myBitmap)
        }

        viewModel = ViewModelProvider(this).get(UpdateFlowerViewModel::class.java)

        binding.updateFlowerBtn.setOnClickListener {
            try {
                val idFlower = flower.flower_id
                var name = ""
                var latinName = ""
                var description = ""
                var wateringInterval = ""
                var imageFlower = ""
                if(binding.name.text.toString() != "")
                    name = binding.name.text.toString()
                if(binding.latinName.text.toString() != "")
                    latinName = binding.latinName.text.toString()
                if(binding.description.text.toString() != "")
                    description = binding.description.text.toString()
                if(binding.wateringInterval.text.toString() != "")
                    wateringInterval = binding.wateringInterval.text.toString()
                if(imageFlower != "")
                    imageFlower = this.imageFlower

                val flowerToUpdate = Flower(
                    flower_id = idFlower,
                    name = name,
                    latinName = latinName,
                    description = description,
                    wateringInterval = wateringInterval.toInt(),
                    imageUrl = imageFlower
                )
                viewModel.updateFlower(flowerToUpdate)
                Toast.makeText(
                    context,
                    "Flower with id: $idFlower has been updated ",
                    Toast.LENGTH_SHORT
                ).show()
            }catch (e: Exception){
                Toast.makeText(
                    context,
                    "Field aren't completed correctly, please verify informations",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.takePictureBtn.setOnClickListener {
            tempImageUri = FileProvider.getUriForFile(this.requireContext(), "com.kevin.hanakotoba.provider", createImageFile())

            imageFlower = tempImageFilePath

            cameraLauncher.launch(tempImageUri)
        }

        binding.choosePictureBtn.setOnClickListener {

            selectPictureLauncher.launch("image/*")
        }

        return view
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = this.requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "tempImage_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            tempImageFilePath = absolutePath
        }
    }
}