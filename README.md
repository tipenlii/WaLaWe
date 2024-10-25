# Aplikasi UTS LEC

## Gambaran Umum

Aplikasi UTS LEC adalah aplikasi pelacakan kebugaran dan latihan yang dibangun menggunakan Kotlin dan Jetpack Compose. Aplikasi ini terintegrasi dengan Firebase untuk autentikasi dan Firestore untuk penyimpanan data. Aplikasi ini memungkinkan pengguna untuk memperbarui profil mereka, melacak kemajuan latihan mereka, dan melihat konten motivasi.

## Fitur

- **Autentikasi Pengguna**: Masuk dan daftar menggunakan Firebase Authentication.
- **Manajemen Profil**: Memperbarui informasi profil termasuk nama lengkap, nomor ponsel, tanggal lahir, berat badan, dan tinggi badan.
- **Pelacakan Latihan**: Melacak kemajuan latihan dengan indikator visual dan konten motivasi.
- **Navigasi**: Navigasi mulus antara berbagai layar menggunakan Jetpack Navigation.

## Layar

- **StartUpScreen**: Layar awal yang ditampilkan kepada pengguna.
- **OnboardingScreen**: Proses onboarding untuk pengguna baru.
- **SignInScreen**: Layar masuk pengguna.
- **SignUpScreen**: Layar daftar pengguna.
- **HomeScreen**: Layar utama yang menampilkan kemajuan latihan dan konten motivasi.
- **ProfileScreen**: Layar profil pengguna yang menampilkan informasi pribadi.
- **UpdateProfileScreen**: Layar untuk memperbarui informasi profil pengguna.
- **SettingsScreen**: Layar pengaturan aplikasi.
- **PasswordScreen**: Layar untuk mengubah kata sandi pengguna.
- **WorkoutCategoryScreen**: Layar yang menampilkan berbagai kategori latihan.
- **WorkoutDetailScreen**: Tampilan detail dari latihan tertentu.
- **WorkoutDaysScreen**: Layar yang menampilkan hari-hari latihan untuk kategori tertentu.
- **TutorialScreen**: Tutorial untuk latihan.
- **TransitionWorkoutScreen**: Layar transisi antara latihan.
- **WorkoutScreen**: Layar yang menampilkan detail latihan.
- **ConfirmDoneScreen**: Layar yang mengonfirmasi penyelesaian latihan.

## Navigasi

Aplikasi ini menggunakan Jetpack Navigation untuk menangani navigasi antara berbagai layar. Fungsi `SetupNavGraph` mendefinisikan grafik navigasi.

## Integrasi Firebase

Aplikasi ini menggunakan Firebase untuk autentikasi dan Firestore untuk menyimpan data pengguna. Pastikan Anda telah mengonfigurasi Firebase di proyek Anda dengan dependensi yang diperlukan dan file `google-services.json`.

## Dependensi

- **Jetpack Compose**: Untuk membangun UI.
- **Firebase Authentication**: Untuk autentikasi pengguna.
- **Firebase Firestore**: Untuk menyimpan data pengguna.
- **Navigation Component**: Untuk menangani navigasi antara layar.

## Pengaturan

1. Clone repositori.
2. Buka proyek di Android Studio.
3. Tambahkan file `google-services.json` Anda ke direktori `app`.
4. Sinkronkan proyek dengan file Gradle.
5. Jalankan aplikasi di emulator atau perangkat fisik.

## Kontribusi

Kontribusi sangat diterima! Silakan fork repositori dan kirim pull request untuk perubahan apa pun.

## Lisensi

Proyek ini dilisensikan di bawah Lisensi MIT.
