package plantdiseaseidentifier.kirankumar.teesproject.mlhelper

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException

class PlantDiseaseClassifier(
    context: Context
) {
    private val imageSize = 200
    private var interpreter: Interpreter? = null
    private val labels: List<String> = try {
        FileUtil.loadLabels(context, "labels.txt")
    } catch (e: IOException) {
        Log.e("Classifier", "Error loading labels from assets.", e)
        emptyList()
    }

    val isInitialized: Boolean
        get() = interpreter != null

    init {
        interpreter = try {
            val model = FileUtil.loadMappedFile(context, "model.tflite")
            Interpreter(model, Interpreter.Options())
        } catch (e: Exception) {
            Log.e("Classifier", "Error initializing TensorFlow Lite interpreter.", e)
            null
        }

        if (interpreter == null || labels.isEmpty()) {
            Log.e("Classifier", "Classifier failed to initialize.")
        } else {
            Log.d("Classifier", "Classifier initialized successfully.")
        }
    }

    fun classify(bitmap: Bitmap): Pair<String, Float> {
        if (!isInitialized) {
            return "Model Error - Uninitialized" to 0.0f
        }

        // 1. Pre-process the image
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(imageSize, imageSize, ResizeOp.ResizeMethod.BILINEAR))
            // This normalization converts pixel values from [0, 255] to the [-1, 1] range.
            .add(NormalizeOp(127.5f, 127.5f))
            .build()

        var tensorImage = TensorImage.fromBitmap(bitmap)
        tensorImage = imageProcessor.process(tensorImage)

        // 2. Prepare the output buffer
        val probabilityBuffer = TensorBuffer.createFixedSize(intArrayOf(1, labels.size), org.tensorflow.lite.DataType.FLOAT32)

        // 3. Run inference
        try {
            interpreter?.run(tensorImage.buffer, probabilityBuffer.buffer.rewind())
        } catch (e: Exception) {
            Log.e("Classifier", "Error running model inference.", e)
            return "Inference Error" to 0.0f
        }


        // 4. Post-process the result
        val tensorLabel = TensorLabel(labels, probabilityBuffer)
        val result = tensorLabel.mapWithFloatValue
        val topResult = result.entries.maxByOrNull { it.value }

        return if (topResult != null) {
            topResult.key to topResult.value
        } else {
            "No Detection" to 0.0f
        }
    }
}