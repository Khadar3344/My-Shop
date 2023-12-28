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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.khadar3344.myshop.components.DefaultBackArrow
import com.khadar3344.myshop.components.ErrorSuggestion
import com.khadar3344.myshop.model.User
import com.khadar3344.myshop.navigation.SignIn
import com.khadar3344.myshop.ui.auth.AuthViewModel

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPass by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val conPasswordErrorState = remember { mutableStateOf(false) }
    val nameErrorState = remember { mutableStateOf(false) }
    val phoneNumberErrorState = remember { mutableStateOf(false) }
    val addressErrorState = remember { mutableStateOf(false) }
    val animate = remember { mutableStateOf(true) }

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
            Box(modifier = Modifier.weight(0.7f)) {
                DefaultBackArrow {
                    navController.popBackStack()
                    animate.value = !animate.value
                }
            }
            Box(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = "Sign Up",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Register Account",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Create an account to continue\nwith your shopping.",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        CustomTextField(
            trailingIcon = R.drawable.user,
            label = "Name",
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None,
            errorState = nameErrorState,
            onChange = { newText ->
                name = newText
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        CustomTextField(
            trailingIcon = R.drawable.phone,
            label = "Phone Number",
            keyboardType = KeyboardType.Phone,
            visualTransformation = VisualTransformation.None,
            errorState = phoneNumberErrorState,
            onChange = { newNumber ->
                phoneNumber = newNumber
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        CustomTextField(
            trailingIcon = R.drawable.location_point,
            label = "Address",
            keyboardType = KeyboardType.Password,
            visualTransformation = VisualTransformation.None,
            errorState = addressErrorState,
            onChange = { newText ->
                address = newText
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
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

        Spacer(modifier = Modifier.height(10.dp))
        CustomTextField(
            trailingIcon = R.drawable.lock,
            label = "Password",
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            errorState = passwordErrorState,
            onChange = { newPass ->
                password = newPass
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        CustomTextField(
            trailingIcon = R.drawable.lock,
            label = "Confirm Password",
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            errorState = conPasswordErrorState,
            onChange = { newPass ->
                confirmPass = newPass
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        when {
            nameErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid name.")
            }
            phoneNumberErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid number.")
            }
            addressErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid address.")
            }
            emailErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid email address.")
            }
            passwordErrorState.value -> {
                ErrorSuggestion(message = "Please enter valid password.")
            }
            conPasswordErrorState.value -> {
                ErrorSuggestion(message = "Confirm Password miss matched.")
            }
        }

        CustomDefaultBtn(shapeSize = 50f, btnText = "Sign Up") {
            val isNameValid = name.text.isEmpty() || name.text.length < 3
            val isPhoneValid = phoneNumber.text.isEmpty() || phoneNumber.text.length < 4
            val isAddressValid = address.text.isEmpty() || address.text.length < 5
            //email pattern
            val pattern = Patterns.EMAIL_ADDRESS
            val isEmailValid = pattern.matcher(email.text).matches()
            val isPassValid = password.text.length >= 8
            val conPassMatch = password == confirmPass
            nameErrorState.value = isNameValid
            phoneNumberErrorState.value = isPhoneValid
            addressErrorState.value = isAddressValid
            emailErrorState.value = !isEmailValid
            passwordErrorState.value = !isPassValid
            conPasswordErrorState.value = !conPassMatch
            if (
                !isNameValid && !isPhoneValid && !isAddressValid &&
                isEmailValid && isPassValid && conPassMatch
            ) {
                val user = User(
                    name = name.text,
                    phone = phoneNumber.text,
                    address = address.text,
                    email = email.text
                )
                viewModel.signup(user = user, password = password.text)
                Toast.makeText(context, "Sign up Successfully", Toast.LENGTH_LONG).show()
                navController.navigate(SignIn.route)
            }
        }
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Have an Account? ")
                Text(
                    text = "Sign In",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable {
                        navController.navigate(SignIn.route)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    val navController = rememberNavController()
    SignUpScreen(
        navController = navController
    )
}