package com.khadar3344.myshop.ui.home.screens.profile_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.khadar3344.myshop.R
import com.khadar3344.myshop.components.CustomAppBar
import com.khadar3344.myshop.components.CustomDefaultBtn
import com.khadar3344.myshop.model.User
import com.khadar3344.myshop.ui.home.component.Error
import com.khadar3344.myshop.ui.home.component.Loading
import com.khadar3344.myshop.util.Resource

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    logout: () -> Unit,
    onBackBtnClick: () -> Unit
) {
    val profileState by viewModel.userInfo.collectAsState()
    val context = LocalContext.current
    ProfileScreenContent(
        profileState = profileState,
        logout = {
            viewModel.logout()
            logout()
        },
        updateData = { user ->
            viewModel.updateUserInfoFirebase(user = user)
            Toast.makeText(context, "Update user data successfully", Toast.LENGTH_SHORT).show()
        },
        onBackBtnClick = onBackBtnClick
    )
}

@Composable
fun ProfileScreenContent(
    profileState: Resource<User>?,
    logout: () -> Unit,
    updateData: (User) -> Unit,
    onBackBtnClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (profileState) {
            is Resource.Success -> {
                SuccessScreen(
                    profileState = profileState.data,
                    logout = logout,
                    updateData = updateData,
                    onBackBtnClick = onBackBtnClick
                )
            }

            is Resource.Failure<*> -> {
                Error(message = profileState.exception.toString())
            }

            is Resource.Loading -> {
                Loading()
            }

            else -> {}
        }
    }
}

@Composable
fun SuccessScreen(
    profileState: User,
    logout: () -> Unit,
    updateData: (User) -> Unit,
    onBackBtnClick: () -> Unit
) {
    var name: String by remember { mutableStateOf(profileState.name) }
    var phone: String by remember { mutableStateOf(profileState.phone) }
    var address: String by remember { mutableStateOf(profileState.address) }
    val nameErrorState = remember { mutableStateOf(false) }
    val phoneNumberErrorState = remember { mutableStateOf(false) }
    val addressErrorState = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        CustomAppBar(
            onBackBtnClick = onBackBtnClick,
            appBarTitle = "Profile"
        )
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = profileState.email,
                modifier = Modifier.padding(5.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = { Text(text = "Name") },
            shape = RoundedCornerShape(1.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Name"
                )
            },
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            isError = nameErrorState.value,
            keyboardActions = KeyboardActions(
                onNext = {}
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
            },
            label = { Text(text = "Phone Number") },
            shape = RoundedCornerShape(1.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.phone),
                    contentDescription = "Phone"
                )
            },
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            isError = phoneNumberErrorState.value,
            keyboardActions = KeyboardActions(
                onNext = {}
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = address,
            onValueChange = {
                address = it
            },
            label = { Text(text = "Address") },
            shape = RoundedCornerShape(1.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.location_point),
                    contentDescription = "Address"
                )
            },
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            isError = addressErrorState.value,
            keyboardActions = KeyboardActions(
                onNext = {}
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))
        CustomDefaultBtn(shapeSize = 50f, btnText = "Update") {
            val isNameValid = name.isEmpty() || name.length < 3
            val isPhoneValid = phone.isEmpty() || phone.length < 4
            val isAddressValid = address.isEmpty() || address.length < 5
            nameErrorState.value = isNameValid
            phoneNumberErrorState.value = isPhoneValid
            addressErrorState.value = isAddressValid
            if (!isNameValid && !isPhoneValid && !isAddressValid) {
                val user = User(
                    name = name,
                    phone = phone,
                    address = address,
                    email = profileState.email
                )
                updateData(user)
            }

        }
        CustomDefaultBtn(shapeSize = 50f, btnText = "Logout") { logout() }
    }
}