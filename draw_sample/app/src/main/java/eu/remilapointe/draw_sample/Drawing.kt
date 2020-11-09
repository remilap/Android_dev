package eu.remilapointe.draw_sample

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter

@Composable
fun Drawing() {

    val action: MutableState<Any?> = mutableStateOf(null)
    val path = Path()

    Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    action.value = it
                    path.moveTo(it.x, it.y)
                }
                MotionEvent.ACTION_MOVE -> {
                    action.value = it
                    path.lineTo(it.x, it.y)
                }
//                MotionEvent.ACTION_UP -> { }
                else -> false
            }
            true
        }
    ) {
        action.value?.let {
            drawPath(
                path = path,
                color = Color.Green,
                alpha = 1f,
                style = Stroke(10f)
            )
        }
    }

}
