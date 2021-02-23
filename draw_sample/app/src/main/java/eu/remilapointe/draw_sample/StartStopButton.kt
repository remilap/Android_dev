package eu.remilapointe.draw_sample

import androidx.compose.material.AmbientTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun startStopButton(modifier: Modifier = Modifier,
                    onToggle: (Boolean) -> Unit) {
    val toggleState: MutableState<Boolean> = mutableStateOf(false)
    TextButton(modifier = modifier, onClick = {
        toggleState.value = !toggleState.value
        onToggle(toggleState.value)
    }) {
        Text(
            if (toggleState.value) "Stop" else "Start",
            Modifier,
            Color.Unspecified,
            32.sp,
            null,
            null,
            null,
            TextUnit.Unspecified,
            null,
            null,
            TextUnit.Unspecified,
            TextOverflow.Clip,
            true,
            Int.MAX_VALUE,
            {},
            AmbientTextStyle.current
        )
    }
}