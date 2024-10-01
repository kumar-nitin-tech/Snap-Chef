package com.example.snap_chef.presentation.auth.signinPageScreen.signInEmailScreen

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.snap_chef.common.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

data class SignInStateModel(
    val isLoggedIn: Boolean
)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constant.SIGN_IN)


class SignInState(context: Context){
    private val dataStore = context.dataStore

    private object PreferenceKey{
        val signInState = booleanPreferencesKey(Constant.SIGN_IN_STATE)
    }

    suspend fun saveLoginState(signInState:Boolean){
        dataStore.edit {
            it[PreferenceKey.signInState] = signInState
        }
    }

    suspend fun readLoginState(): Flow<Boolean> {
        return dataStore.data
            .catch {exception->
                if(exception is IOException){
                    emit(emptyPreferences())
                }else{
                    throw exception
                }
            }.map {
                val loginState = it[PreferenceKey.signInState] ?: false
                loginState
            }
    }

}




