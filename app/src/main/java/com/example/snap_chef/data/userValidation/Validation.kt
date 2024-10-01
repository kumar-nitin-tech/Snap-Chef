package com.example.snap_chef.data.userValidation

import android.util.Patterns
import com.example.snap_chef.domain.model.User

sealed class RegisterValidation() {
    data object Success: RegisterValidation()
    data class Failed(val message: String): RegisterValidation()
}

data class RegisterFailedState(
    val email: RegisterValidation,
    val password: RegisterValidation
)

fun validation(user: User): Boolean{
    val emailValidate = emailValidation(user.email)
    val passwordValidate = validatePassword(user.password)

    return emailValidate is RegisterValidation.Success && passwordValidate is RegisterValidation.Success
}

fun emailValidation(email: String):RegisterValidation{
    if(email.isEmpty()) {
        return RegisterValidation.Failed("Enter the email")
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        return RegisterValidation.Failed("Email format is incorrect")
    }
    return RegisterValidation.Success
}

fun validatePassword(password: String): RegisterValidation {
    if (password.isEmpty())
        return RegisterValidation.Failed("Enter the password")

    if(password.length < 6)
        return RegisterValidation.Failed("Password should have minimum 6 letters")

    return RegisterValidation.Success
}