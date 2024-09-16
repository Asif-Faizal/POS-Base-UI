import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cgs_pos.Screen

@Composable
fun PasscodeScreen(navController: NavHostController) {
    // State to hold the values of the text fields
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var text3 by remember { mutableStateOf("") }
    var text4 by remember { mutableStateOf("") }

    // FocusNodes for each text field
    val focusNode1 = remember { FocusRequester() }
    val focusNode2 = remember { FocusRequester() }
    val focusNode3 = remember { FocusRequester() }
    val focusNode4 = remember { FocusRequester() }

    // LocalFocusManager to manage focus clearing
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Heading
            Text(
                text = "Enter Passcode",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 28.sp, fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 30.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PasscodeTextField(
                    value = text1,
                    onValueChange = {
                        if (it.length <= 1) {
                            text1 = it
                            if (it.isNotEmpty()) focusNode2.requestFocus()
                        }
                    },
                    focusRequester = focusNode1,
                    nextFocusRequester = focusNode2,
                    previousFocusRequester = focusNode1
                )

                Spacer(modifier = Modifier.width(16.dp))

                PasscodeTextField(
                    value = text2,
                    onValueChange = {
                        if (it.length <= 1) {
                            text2 = it
                            if (it.isNotEmpty()) focusNode3.requestFocus()
                        }
                    },
                    focusRequester = focusNode2,
                    nextFocusRequester = focusNode3,
                    previousFocusRequester = focusNode1
                )

                Spacer(modifier = Modifier.width(16.dp))

                PasscodeTextField(
                    value = text3,
                    onValueChange = {
                        if (it.length <= 1) {
                            text3 = it
                            if (it.isNotEmpty()) focusNode4.requestFocus()
                        }
                    },
                    focusRequester = focusNode3,
                    nextFocusRequester = focusNode4,
                    previousFocusRequester = focusNode2
                )

                Spacer(modifier = Modifier.width(16.dp))

                PasscodeTextField(
                    value = text4,
                    onValueChange = {
                        if (it.length <= 1) {
                            text4 = it
                        }
                    },
                    focusRequester = focusNode4,
                    nextFocusRequester = null,

                    isLastField = true,
                    focusManager = focusManager,
                    previousFocusRequester = focusNode3
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
        ElevatedButton(
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 20.dp, pressedElevation = (-20).dp),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                navController.navigate(Screen.Dashboard.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 30.dp, vertical = 30.dp),
            colors = ButtonColors(
                containerColor = Color.Blue,
                contentColor = Color.White,
                disabledContainerColor = Color.Blue.copy(alpha = 0.1f),
                disabledContentColor = Color.White.copy(alpha = 0.1f)
            )
        ) {
            Text("Login", fontSize = 20.sp)
        }
    }
}@Composable
fun PasscodeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    previousFocusRequester: FocusRequester?,
    nextFocusRequester: FocusRequester?,
    isLastField: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current
) {
    val isFocused = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .border(
                width = if (isFocused.value) 3.dp else 2.dp,
                color = Color.Blue,
                shape = RoundedCornerShape(10.dp)
            )
            .width(60.dp)
            .height(70.dp)
            .padding(20.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.isEmpty()) {
                    previousFocusRequester?.requestFocus()
                }
                onValueChange(newValue)
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged { focus ->
                    isFocused.value = focus.isFocused
                },
            textStyle = MaterialTheme.typography.headlineMedium.copy(
                textAlign = TextAlign.Center,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = if (isLastField) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = if (isLastField) {
                KeyboardActions(onDone = { focusManager.clearFocus(force = true) })
            } else {
                KeyboardActions(onNext = { nextFocusRequester?.requestFocus() })
            },
            visualTransformation = PasswordVisualTransformation()
        )
    }
}
