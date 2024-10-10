# Snap-Chef

Snap-Chef is an image-based recipe generation Android application. With Snap-Chef, users can upload images of food from their gallery or camera, and the app will generate recipes based on the image using AI-powered recognition. Users can also view past searches and manage their accounts through Firebase authentication.

## Features

- **Image-based recipe generation**: Upload images from your gallery or capture them using the camera, and receive accurate recipes based on the image content.
- **Firebase Authentication**: Secure sign-in with email and Google accounts.
- **Search history**: Easily access previous recipe searches.
- **Seamless user experience**: Optimized for quick image processing and fast recipe retrieval.

## Tech Stack

- **Kotlin**: The primary language for Android app development.
- **Jetpack Compose**: Modern Android UI toolkit for building native interfaces.
- **Firebase**:
  - Firebase Authentication for secure login (Email and Google Sign-In).
  - Firebase Firestore for search history and user data storage.
- **Gemini SDK**: AI-based image recognition to generate recipes from food images.
- **CameraX**: For capturing photos directly from the app.
- **Clean Architecture**: Ensuring scalability and maintainability of the codebase.

## APK Download

Download the latest version of the Snap-Chef APK [here](https://github.com/kumar-nitin-tech/Snap-Chef/releases/download/v1.0.0/app-release.apk).  


## How to Install

1. **Download the APK**: 
   - Download the latest APK from the link provided above.
   
2. **Install the APK**:
   - If you're installing from an external source, make sure to enable installation from unknown sources in your device's security settings.
   
   Go to **Settings** -> **Security** -> Enable **Unknown sources**.

3. **Run the App**:
   - Open the app and sign in using your Google or Email account.
   - Upload or capture a food image to generate recipes and start exploring!

## How to Use

1. **Sign In**: 
   - Sign in using Google or Email.

2. **Upload or Capture Image**: 
   - Select an image from your gallery or capture one using the camera.

3. **View Recipe**: 
   - The app will process the image and provide a detailed recipe based on the food item in the image.

4. **Access Past Searches**: 
   - View your search history to revisit previously generated recipes.

## Installation for Development

If you'd like to run this project locally for development purposes, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/nitin-kumar/snap-chef.git
   cd snap-chef
