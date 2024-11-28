package com.example.lesson_sharedpreferences

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding

    // Регистрация для получения результата из SettingsActivity
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Обновление UI при получении ответа от SettingsActivity
                updateUI()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Обновление UI при старте активности
        updateUI()

        // Переход в SettingsActivity для изменения настроек
        mainBinding.buttonGoToSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startForResult.launch(intent) // Запуск активности с результатом
        }
    }

    // Функция для обновления UI
    private fun updateUI() {
        val sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "Guest")
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        mainBinding.textViewUsername.text = "Username: $username"
        mainBinding.textViewDarkMode.text =
            "Dark Mode: ${if (isDarkMode) "Enabled" else "Disabled"}"
    }
}