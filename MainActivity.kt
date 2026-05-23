package com.mobprog.jobsheet5season2

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// PASTIKAN NAMA CLASS ADALAH "MainActivity"
class MainActivity : AppCompatActivity() {

    // Deklarasikan view di sini agar bisa diakses di semua fungsi
    private lateinit var inputNilai: EditText
    private lateinit var btnCek: Button
    private lateinit var btnReset: Button
    private lateinit var txtStatus: TextView
    private lateinit var txtGrade: TextView
    private lateinit var txtKeterangan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitiy_main)

        // Panggil fungsi untuk inisialisasi view dan listener
        setupViews()
        setupListeners()
    }

    // Fungsi untuk inisialisasi semua view
    private fun setupViews() {
        inputNilai = findViewById(R.id.inputNilai)
        btnCek = findViewById(R.id.btnCek)
        btnReset = findViewById(R.id.btnReset)
        txtStatus = findViewById(R.id.txtStatus)
        txtGrade = findViewById(R.id.txtGrade)
        txtKeterangan = findViewById(R.id.txtKeterangan)
    }

    // Fungsi untuk mengatur semua event click
    private fun setupListeners() {
        btnCek.setOnClickListener {
            prosesNilai()
        }

        btnReset.setOnClickListener {
            resetForm()
        }
    }

    // Logika utama untuk memproses nilai
    private fun prosesNilai() {
        val nilaiStr = inputNilai.text.toString()

        if (nilaiStr.isEmpty()) {
            Toast.makeText(this, "Masukkan nilai terlebih dahulu!", Toast.LENGTH_SHORT).show()
            return
        }

        // PERBAIKAN 2: Gunakan .toIntOrNull() untuk mencegah crash jika input tidak valid
        val nilai = nilaiStr.toIntOrNull()

        if (nilai == null || nilai !in 0..100) {
            resetTampilanHasil(isError = true, pesan = "Nilai Tidak Valid (0-100)")
            Toast.makeText(this, "Input hanya angka 0 sampai 100!", Toast.LENGTH_SHORT).show()
            return
        }

        // Logika Status
        if (nilai >= 75) {
            txtStatus.text = "Status: LULUS"
            txtStatus.setTextColor(Color.parseColor("#2E7D32")) // Warna hijau
        } else {
            txtStatus.text = "Status: TIDAK LULUS"
            txtStatus.setTextColor(Color.RED)
        }

        // Logika Grade & Keterangan
        val (grade, ket) = when (nilai) {
            in 90..100 -> "A" to "Sangat Baik"
            in 80..89 -> "B" to "Baik"
            in 70..79 -> "C" to "Cukup"
            in 60..69 -> "D" to "Kurang"
            else -> "E" to "Sangat Kurang" // Menambahkan grade E
        }
        txtGrade.text = "Grade: $grade"
        txtKeterangan.text = "Keterangan: $ket"
    }

    // Fungsi untuk mereset semua input dan hasil
    private fun resetForm() {
        inputNilai.text.clear()
        resetTampilanHasil() // Panggil fungsi reset tampilan
        inputNilai.requestFocus()
        Toast.makeText(this, "Data direset", Toast.LENGTH_SHORT).show()
    }

    // Fungsi untuk mereset tampilan status, grade, dan keterangan
    private fun resetTampilanHasil(isError: Boolean = false, pesan: String = "-") {
        txtStatus.text = "Status: -"
        txtStatus.setTextColor(Color.BLACK)
        txtGrade.text = "Grade: -"

        if (isError) {
            txtKeterangan.text = "Keterangan: $pesan"
            txtKeterangan.setTextColor(Color.RED)
        } else {
            txtKeterangan.text = "Keterangan: -"
            txtKeterangan.setTextColor(Color.parseColor("#777777"))
        }
    }
}
