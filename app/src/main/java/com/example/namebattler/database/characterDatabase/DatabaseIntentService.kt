package com.example.namebattler.database.characterDatabase

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent


@SuppressLint("Registered")
class DatabaseIntentService : IntentService("DatabaseIntentService") {
    override fun onHandleIntent(intent: Intent?) { //重い処理を行う。

    }
}

