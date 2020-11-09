package eu.remilapointe.draw_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
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
                Clock(
                    modifier = Modifier
                        .aspectRatio(1.0f)
                        .padding(64.dp),
                    animationStart
                )
                StartStopButton(modifier = Modifier) {
                    animationStart.value = it
                }
                Drawing()
            }
        }
    }

    @Composable
    fun greeting(name: String) {
        Text(text = "Hello $name!")
    }

//    @Preview
    @Composable
    fun previewGreeting() {
        greeting("Android good")
    }

}
