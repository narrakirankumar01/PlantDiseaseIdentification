# 🌱 Plant Disease Identifier App

The **Plant Disease Identifier App** is an Android application that helps users identify plant diseases by capturing images of plant leaves and provides treatment recommendations. The app is designed to be simple, farmer-friendly, and usable even without continuous internet access.

---

## 📱 Features

- 📷 **Plant Leaf Scanning**
    - Capture plant leaf images using the device camera
    - Guided scanning interface with visual overlay

- 🤖 **Machine Learning Disease Detection**
    - On-device plant disease classification using TensorFlow Lite
    - Fast and offline inference

- 🧪 **Disease Details & Remedies**
    - Symptoms explanation
    - General management practices
    - Chemical treatment recommendations
    - Organic treatment recommendations

- 💾 **Save Scan Reports**
    - Save detected disease reports locally
    - Stores plant name, disease, confidence score, image, and date
    - View and delete saved reports anytime

- 🌍 **Multi-Language Support**
    - English
    - Telugu
    - Hindi

- 🌗 **Light & Dark Mode**
    - User-controlled theme switching

- 👤 **User Profile**
    - View and edit profile details
    - Firebase-based profile storage
    - Logout functionality

- 📚 **Prevention Tips & Articles**
    - Articles for plant care and disease prevention

---

## 🏗️ Tech Stack

### Android
- **Kotlin**
- **Jetpack Compose**
- **Material Design 3**
- **Jetpack Navigation**

### Machine Learning
- **TensorFlow Lite**
- Pre-trained Plant Disease Classification Model

### Data Storage
- **Room Database** – for saved reports
- **Firebase Realtime Database** – for user profile & authentication
- **SharedPreferences** – for theme & language settings

### Other Libraries
- **CameraX** – camera handling
- **Coil** – image loading
- **Firebase SDK**

---



## 🚀 How It Works

1. User opens the app and logs in
2. Captures a plant leaf image using the camera
3. ML model processes the image on-device
4. Disease name and confidence score are displayed
5. User can view remedies or save the report
6. Saved reports are available offline

---

## 🔐 Permissions Used

- `CAMERA` – to capture plant images
- `INTERNET` – for Firebase operations (login & profile)

---

## 🌐 Supported Languages

- English
- Telugu
- Hindi

Language can be changed from the profile/settings section.

---

## 👨‍💻 Developed By

- **Student Number:** S3468594
- **Email:** narrakirankumar01@gmail.com

---



