package com.jofranpduran.dogshelter.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.core.net.toUri
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.type.content
import com.jofranpduran.dogshelter.domain.repository.ImageAnalysisRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ImageAnalysisRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel,
    @param:ApplicationContext private val context: Context
) : ImageAnalysisRepository {
    override suspend fun getDogBreedFromImage(imageUri: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val image = loadBitmap(imageUri)
                val prompt = content {
                    image(image)
                    text(
                        "What is the dog breed in this image? Return only " +
                                "the name of the breed or if it is a mix " +
                                "return the possible breeds mixed. If there no " +
                                "dog in the image return Not a Dog."
                    )
                }

                val response = generativeModel.generateContent(prompt)
                Result.success(response.text ?: "Null response")
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun loadBitmap(uriString: String): Bitmap {
        val uri = uriString.toUri()
        val source = if (uri.scheme == "content") {
            ImageDecoder.createSource(context.contentResolver, uri)
        } else {
            // Handle raw file paths or file:// URIs
            val filePath = uri.path ?: uriString
            ImageDecoder.createSource(File(filePath))
        }
        return ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            decoder.isMutableRequired = true
        }
    }
}