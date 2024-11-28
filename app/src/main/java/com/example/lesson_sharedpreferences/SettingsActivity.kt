package com.example.lesson_sharedpreferences

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson_sharedpreferences.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var settingsBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(settingsBinding.root)

        // Получение текущих значений из SharedPreferences
        val sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val currentUsername = sharedPreferences.getString("username", "")
        val currentDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        // Установка текущих значений в UI
        settingsBinding.editTextUsername.setText(currentUsername)
        settingsBinding.switchDarkMode.isChecked = currentDarkMode

        // Сохранение настроек при нажатии на кнопку
        settingsBinding.buttonSaveSettings.setOnClickListener {
            val newUsername = settingsBinding.editTextUsername.text.toString()
            val isDarkMode = settingsBinding.switchDarkMode.isChecked

            // Сохранение новых данных в SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("username", newUsername)
            editor.putBoolean("dark_mode", isDarkMode)
            editor.apply() // Применяем изменения

            // Закрытие активности и возврат результата в MainActivity
            setResult(RESULT_OK)
            finish()
        }
    }
}