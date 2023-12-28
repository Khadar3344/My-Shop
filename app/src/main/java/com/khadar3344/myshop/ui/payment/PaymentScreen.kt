package com.khadar3344.myshop.ui.payment

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khadar3344.myshop.components.CustomAppBar
import com.khadar3344.myshop.components.CustomDefaultBtn

@Composable
fun PaymentScreen(onBackBtnClick: () -> Unit) {
    var cardHolder by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expireMonth by remember { mutableStateOf("") }
    var expireYear by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val cardHolderState = remember { mutableStateOf(false) }
    val cardNumberState = remember { mutableStateOf(false) }
    val expireMonthState = remember { mutableStateOf(false) }
    val expireYearState = remember { mutableStateOf(false) }
    val cvcState = remember { mutableStateOf(false) }
    val addressState = remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
    ) {
        CustomAppBar(onBackBtnClick = onBackBtnClick, appBarTitle = "Payment")
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Payment Credit Card",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        OutlinedTextField(
            value = cardHolder,
            onValueChange = { cardHolder = it },
            label = { Text(text = "Cardholder Name") },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            isError = cardHolderState.value
        )

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text(text = "Credit Card Number") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            isError = cardNumberState.value
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        ) {
            OutlinedTextField(
                value = expireMonth,
                onValueChange = { expireMonth = it },
                label = { Text(text = "Month") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                isError = expireMonthState.value
            )

            OutlinedTextField(
                value = expireYear,
                onValueChange = { expireYear = it },
                label = { Text(text = "Year") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                isError = expireYearState.value
            )
        }

        OutlinedTextField(
            value = cvc,
            onValueChange = { cvc = it },
            label = { Text(text = "CVC Code") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            isError = cvcState.value
        )

        Text(
            text = "Address",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text(text = "Address") },
            maxLines = 4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            isError = addressState.value
        )

        CustomDefaultBtn(shapeSize = 50f, btnText = "Pay Now") {
            val isCardHolderValid = cardHolder.isNotEmpty()
            val isCardNumberValid = cardNumber.isNotEmpty()
            val isExpireMonthValid = expireMonth.isNotEmpty()
            val isExpireYearValid = expireYear.isNotEmpty()
            val isCvcValid = cvc.isNotEmpty()
            val isAddressValid = address.isNotEmpty()

            cardHolderState.value = !isCardHolderValid
            cardNumberState.value = !isCardNumberValid
            expireMonthState.value = !isExpireMonthValid
            expireYearState.value = !isExpireYearValid
            cvcState.value = !isCvcValid
            addressState.value = !isAddressValid
            if (
                isCardHolderValid && isCardNumberValid && isExpireMonthValid &&
                isExpireYearValid && isCvcValid && isAddressValid
            ) {
                Toast.makeText(context, "Payment Successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentScreen() {
    PaymentScreen {  }
}