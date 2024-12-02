package com.example.lesson_sharedpreferences

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
                // Обновление картинки после выбора
                mainBinding.imageView.setImageURI(result.data?.data)
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

        // Переход на веб-сайт
        mainBinding.button.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
            startActivity(webIntent)
        }

        // Отправка сообщения через другие приложения
        mainBinding.button2.setOnClickListener {
            val intentMessage = Intent(Intent.ACTION_SEND).apply {
                type = "text/*"
                putExtra(
                    Intent.EXTRA_TEXT,
                    getSharedPreferences(
                        "user_preferences",
                        Context.MODE_PRIVATE
                    ).getString("username", "Guest")
                )
            }
            // Выбор приложения, через котрое отправим сообщение
            startActivity(Intent.createChooser(intentMessage, "Отправить через:"))
        }

        // Выбор изображения из галереи
        mainBinding.button3.setOnClickListener {
            val intentImage =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startForResult.launch(intentImage)
        }
    }

    // Функция для обновления UI
    private fun updateUI() {
        // Создание хранилища SharedPreferences с названием user_preferences и закрытым уровнем доступа
        val sharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        // Обращение к элементу хранилища username
        val username = sharedPreferences.getString("username", "Guest")
        // Обращение к элементу хранилища dark_mode
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        mainBinding.textViewUsername.text = "Username: $username"
        mainBinding.textViewDarkMode.text =
            "Dark Mode: ${if (isDarkMode) "Enabled" else "Disabled"}"
    }
}