package com.github.xs93.retrofit.simple

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.xs93.retrofit.EasyRetrofit
import com.github.xs93.retrofit.cookie.toByteArray
import com.github.xs93.retrofit.cookie.toHexString
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EasyRetrofit.init(this, "https://www.wanandroid.com/", null, BuildConfig.DEBUG)
        val login: Button = findViewById(R.id.login)



        login.setOnClickListener {
            lifecycleScope.launch {
                val repository = LoginRepository()
                repository.login("466911254@qq.com", "haoyuexun123").collect {
                    Log.d("Simple", it.toString())
                }
            }

        }
    }
}