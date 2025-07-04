package com.jmgg.habitus.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.jmgg.habitus.HabitusApp
import com.jmgg.habitus.models.User
import com.jmgg.habitus.ui.auth.Components.CustomOutlinedTextField

@Composable
fun RegisterScreen(
    onRegistrationSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val viewModel = HabitusApp.authViewModel
    val error by viewModel.authError.collectAsState()
    var isPremium by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showValidationError by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()


    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 90.dp)

        ) {
            Text(
                "SIGN UP",
                style = MaterialTheme.typography.displaySmall,
                color = Color(0xFF6366F1),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            CustomOutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = "User Name",
                leadingIcon = Icons.Default.Person,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))


            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                leadingIcon = Icons.Default.Email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            CustomOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                leadingIcon = Icons.Default.Lock,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { showPassword = !showPassword }
                    ) {
                        Icon(
                            imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = Color(0xFFF8FAFC)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            CustomOutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                leadingIcon = Icons.Default.Lock,
                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showConfirmPassword = !showConfirmPassword
                        }
                    ) {
                        Icon(
                            imageVector = if (showConfirmPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = Color(0xFFF8FAFC)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Checkbox(
                    checked = isPremium,
                    onCheckedChange = { isPremium = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.White,
                        uncheckedColor = Color.White,
                        checkmarkColor = Color(0xFF0F172A)
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Sign up as Premium", color = Color.White)
            }

            Spacer(Modifier.height(16.dp))

            if (showValidationError) {
                Text(
                    "All fields are required and passwords must match",
                    color = Color(0xFFEF4444),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    val valid = name.isNotBlank()
                            && email.isNotBlank()
                            && password.isNotBlank()
                            && password == confirmPassword
                    if (valid) {
                        viewModel.register(
                            User(
                                name = name.trim(),
                                email = email.trim(),
                                password = password.trim(),
                                isPremium = isPremium
                            )
                        )
                        onRegistrationSuccess()
                    } else {
                        showValidationError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6366F1),
                    contentColor = Color(0xFF0F172A)
                ),
                shape = RoundedCornerShape(30),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(40.dp)
            ) {
                Text("Sign up",
                    color = Color(0xFFF8FAFC),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = onNavigateToLogin) {
                Text(
                    "i already have an account!",
                    color = Color(0xFF10B981),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            if (error != null) {
                Spacer(Modifier.height(8.dp))
                Text(error!!,
                    color = Color(0xFFEF4444)
                )
            }
        }
    }
}
