package com.example.snap_chef.di

import android.content.Context
import com.example.snap_chef.data.repository.RecipeRepositoryImpl
import com.example.snap_chef.presentation.auth.signinPageScreen.signInEmailScreen.SignInState
import com.example.snap_chef.remote.gemini.GeminiService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFireStore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideSignInState(@ApplicationContext context: Context) = SignInState(context)

    @Provides
    @Singleton
    fun provideGeminiServices() = GeminiService()

    @Provides
    @Singleton
    fun provideRecipeRepository(geminiService: GeminiService,firestore: FirebaseFirestore, auth: FirebaseAuth) = RecipeRepositoryImpl(geminiService,firestore,auth)

}