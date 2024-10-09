package com.example.snap_chef.common

import com.example.snap_chef.BuildConfig


object Constant{
    const val WEB_CLIENT_ID = BuildConfig.webClient
    const val SIGN_IN_GOOGLE = "signInWithGoogle"
    const val SIGN_IN = "SignIn"
    const val SIGN_IN_STATE = "SignInSuccess"
    const val COLLECTION = "userData"
    const val PROMPT = "Accurately identify the food in the image and provide an appropriate recipe consistent with your analysis.\n" +
            "Response should be in json Schema as following:\n" +
            "{\n" +
            "\"status code\": 400\n" +
            "\"Food Name\": \"\",\n" +
            "\"Cooking Time\": \"\",\n" +
            "\"Preparation Time\": \"\",\n" +
            "\"Ingredient\": [],\n" +
            "\"Instruction\":[]\n" +
            "}\n" +
            "If the image is not a food response with appropiate response of invalid request in json format:\n" +
            "{\n" +
            "\"status code\": 402\n" +
            "\"response\" : \"\"\n" +
            "}\n" +
            "All Response should be in one line and in json format and avoid \\n."
}