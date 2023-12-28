package com.khadar3344.myshop.ui.auth.screens

import android.util.Patterns
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import com.khadar3344.myshop.components.ErrorSuggestion
import com.khadar3344.myshop.navigation.ForgotPass
import com.khadar3344.myshop.navigation.Home
import com.khadar3344.myshop.navigation.SignUp
import com.khadar3344.myshop.ui.auth.AuthViewModel
import com.khadar3344.myshop.ui.home.component.Error
import com.khadar3344.myshop.util.Resource

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var checkBox by remember {
        mutableStateOf(false)
    }
    val emailErrorState = remember {
        mutableStateOf(false)
    }
    val passwordErrorState = remember {
        mutableStateOf(false)
    }

    val currentProgress by remember {
        mutableFloatStateOf(0f)
    }

    val loginFlow by viewModel.loginFlow.collectAsState()

    when(loginFlow) {
        is Resource.Success -> {
            viewModel.saveUserIdToSharedPref(viewModel.currentUser!!.uid)
            navController.navigate(Home.route)
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = currentProgress
            )
        }

        is Resource.Failure<*> -> {
            Error(message = (loginFlow as Resource.Failure<*>).exception.toString())
        }

        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sign In",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Welcome Back",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Sign in with your email and password",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(50.dp))
        CustomTextField(
            trailingIcon = R.drawable.mail,
            label = "Email",
            keyboardType = KeyboardType.Email,
            visualTransformation = VisualTransformation.None,
            errorState = emailErrorState,
            onChange = { newEmail -> email = newEmail }
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            trailingIcon = R.drawable.lock,
            label = "Password",
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            errorState = passwordErrorState,
            onChange = { newPass -> password = newPass }
        )
        Spacer(modifier = Modifier.height(10.dp))
        when {
            emailErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid email address.")
            }
            passwordErrorState.value -> {
                Row {
                    ErrorSuggestion(message = "Please enter valid password")
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checkBox,
                    onCheckedChange = {
                        checkBox = it
                    }
                )
                Text(
                    text = "Remember me",
                    fontSize = 14.sp
                )
            }
            Text(
                text = "Forgot Password ?",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier.clickable {
                    navController.navigate(ForgotPass.route)
                }
            )
        }
        CustomDefaultBtn(shapeSize = 50f, btnText = "Sign In") {
            //email pattern
            val pattern = Patterns.EMAIL_ADDRESS
            val isEmailValid = pattern.matcher(email.text).matches()
            val isPassValid = password.text.length >= 8
            emailErrorState.value = !isEmailValid
            passwordErrorState.value = !isPassValid
            if (isEmailValid && isPassValid) {
                viewModel.login(email = email.text, password = password.text)
            }
        }
        Column(
            modifier = Modifier
                .padding(bottom = 50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
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

}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(
        navController = navController
    )
}