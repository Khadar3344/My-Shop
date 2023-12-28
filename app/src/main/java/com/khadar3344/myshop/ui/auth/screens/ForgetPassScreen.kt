package com.khadar3344.myshop.ui.auth.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.khadar3344.myshop.R
import com.khadar3344.myshop.components.CustomDefaultBtn
import com.khadar3344.myshop.components.CustomTextField
import com.khadar3344.myshop.components.DefaultBackArrow
import com.khadar3344.myshop.navigation.SignIn
import com.khadar3344.myshop.navigation.SignUp
import com.khadar3344.myshop.ui.auth.AuthViewModel
import com.khadar3344.myshop.util.Resource

@Composable
fun ForgotPasswordScreen(
    viewModel: AuthViewModel? = hiltViewModel(),
    navController: NavController
) {
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val emailErrorState = remember {
        mutableStateOf(false)
    }

    val currentProgress by remember {
        mutableFloatStateOf(0f)
    }

    val forgetPassFlow = viewModel?.forgetPassFlow?.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(0.3f)) {
                DefaultBackArrow {
                    navController.popBackStack()
                }
            }
            Box(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = "Forgot Password",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Forget Password",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Please enter your email and we will send\nyou a link to return your account",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(150.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CustomTextField(
                trailingIcon = R.drawable.mail,
                label = "Email",
                keyboardType = KeyboardType.Email,
                visualTransformation = VisualTransformation.None,
                errorState = emailErrorState,
                onChange = { newEmail ->
                    email = newEmail
                }
            )
            CustomDefaultBtn(shapeSize = 50f, btnText = "Send") {
                // email pattern
                val pattern = Patterns.EMAIL_ADDRESS
                val isEmailValid = pattern.matcher(email.text).matches()
                emailErrorState.value = !isEmailValid
                if (isEmailValid) {
                    viewModel?.forgetPass(email.text)
                    navController.popBackStack()
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 30.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Don't have an account? ")
                Text(
                    text = "Sign Up",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable {
                        navController.navigate(SignUp.route)
                    }
                )
            }
        }
    }
    forgetPassFlow?.value.let {
        when (it) {
            is Resource.Failure<*> -> {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_LONG).show()
            }

            Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = currentProgress
                )
            }

            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate(SignIn.route) {
                        popUpTo(SignIn.route) { inclusive = true }
                    }
                }
                Toast.makeText(context, "Send email successfully", Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgetPasswordScreenPreview() {
    val navController = rememberNavController()

    ForgotPasswordScreen(
        viewModel = null,
        navController = navController
    )
}