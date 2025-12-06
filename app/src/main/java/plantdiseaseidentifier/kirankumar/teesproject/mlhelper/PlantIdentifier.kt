package plantdiseaseidentifier.kirankumar.teesproject.mlhelper


import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class PlantIdentifier(context: Context) {
    private val interpreter: Interpreter
    private val labels: List<String>

    init {
        val modelBuffer = loadModelFile(context, "plant_identification_model.tflite")
        interpreter = Interpreter(modelBuffer)

        labels = context.assets.open("labels_plants.txt")
            .bufferedReader()
            .useLines { it.toList() }
    }

    fun classify(bitmap: Bitmap): Pair<String, Float> {
        val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val inputImage = TensorImage.fromBitmap(resized)

        val output = TensorBuffer.createFixedSize(intArrayOf(1, labels.size), org.tensorflow.lite.DataType.FLOAT32)
        interpreter.run(inputImage.buffer, output.buffer.rewind())

        val scores = output.floatArray
        val maxIndex = scores.indices.maxByOrNull { scores[it] } ?: 0
        val confidence = scores[maxIndex]

        return labels[maxIndex] to confidence
    }

    // reuse same loadModelFile helper (copy it here or move to a shared util)
    private fun loadModelFile(context: Context, modelPath: String): MappedByteBuffer {
        val afd = context.assets.openFd(modelPath)
        FileInputStream(afd.fileDescriptor).use { input ->
            val channel: FileChannel = input.channel
            val startOffset = afd.startOffset
            val declaredLength = afd.declaredLength
            return channel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        }
    }
}

