package com.example.snap_chef.presentation.auth.signinPageScreen.googleSignIn

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.snap_chef.common.Constant
import com.google.android.libraries.identity.googleid.GetGoogleIdOption

class GoogleAuthClient() {
    suspend fun googleClient(context : Context): Result<Credential>{
        val googleIdOption = GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false).setServerClientId(
            Constant.WEB_CLIENT_ID).build()
        val credentialRequest = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()
        val credentialManager = CredentialManager.create(context)
        return try {
            val result = credentialManager.getCredential(
                request = credentialRequest,
                context = context
            )
            Result.success(result.credential)
        }catch (e: GetCredentialException){
            Log.e("Credential Error", e.message.orEmpty())
            Result.failure(e)
        }
    }
}