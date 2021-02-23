package eu.remilapointe.draw_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.AmbientTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {

    private val animationStart: MutableState<Boolean> = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        setContent {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                previewGreeting()
                clock(
                    modifier = Modifier
                        .aspectRatio(1.0f)
                        .padding(64.dp),
                    animationStart
                )
                startStopButton(modifier = Modifier) {
                    animationStart.value = it
                }
                drawing()
            }
        }
    }

    @Composable
    fun greeting(name: String) {
        Text(
            "Hello $name!",
            Modifier,
            Color.Unspecified,
            TextUnit.Unspecified,
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

//    @Preview
    @Composable
    fun previewGreeting() {
        greeting("Android good")
    }

}
