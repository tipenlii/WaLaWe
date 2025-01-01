# Aplikasi Walawe
## Anggota Kelompok

Kelompok 3 WaLaWe:
Owen Christian Cahyadi (00000067055)
Steven Lie (00000069236)
Wency Yvonney Wijaya (00000071833)

## Gambaran Umum

Aplikasi Walawe adalah aplikasi yang bergerak dibidang olahraga untuk membantu user memudahkan hari hari workout dengan tampilan yang mudah. Pelacakan kebugaran dan latihan yang dibangun menggunakan Kotlin dan Jetpack Compose. Aplikasi ini terintegrasi dengan Firebase untuk autentikasi dan Firestore untuk penyimpanan data seperti daftar workout, profile user, . Aplikasi ini memungkinkan pengguna untuk memperbarui profil mereka, melacak kemajuan latihan mereka, dan melihat konten motivasi. 

## Fitur

- **Autentikasi Pengguna**: Masuk dan daftar menggunakan Firebase Authentication.
- **Manajemen Profil**: Memperbarui informasi profil termasuk nama lengkap, nomor ponsel, tanggal lahir, berat badan, dan tinggi badan.
- **Pelacakan Latihan**: Melacak kemajuan latihan dengan indikator visual, seperti kalori, waktu, jumlah exercise, bar chart kalori per hari.
- **Navigasi**: Navigasi mulus antara berbagai layar menggunakan Jetpack Navigation.
- **List Workout**: Menjalankan workout dari berbagai kategori yang sudah terintegrasi dari firebase, setelah selesai melakukan semua workout pada hari itu, maka hari berikutnya baru terunlock.
- **History**: Menampilkan progres workout yang sudah dilakukan oleh user.
- **Native**: Menggunakan camera dan galery untuk mengambil gambar sebagai profile user dan terintegrasi dengand firebase.

## Navigasi

Aplikasi ini menggunakan Jetpack Navigation untuk menangani navigasi antara berbagai layar. Fungsi `SetupNavGraph` mendefinisikan grafik navigasi.

## Integrasi Firebase

Aplikasi ini menggunakan Firebase untuk autentikasi dan Firestore untuk menyimpan data pengguna. Pastikan Anda telah mengonfigurasi Firebase di proyek Anda dengan dependensi yang diperlukan dan file `google-services.json`.

## Dependensi

- **Jetpack Compose**: Untuk membangun UI.
- **Firebase Authentication**: Untuk autentikasi pengguna.
- **Firebase Firestore**: Untuk menyimpan data pengguna.
- **Navigation Component**: Untuk menangani navigasi antara layar.
