package com.example.catchphrase.viewmodel

import androidx.room.TypeConverter
import com.example.catchphrase.data.Phrase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromPhrase(phrase: Phrase?): String? {
            return Gson().toJson(phrase)
        }

        @TypeConverter
        @JvmStatic
        fun fromString(jsonPhrase : String?): Phrase? {
            return Gson().fromJson(jsonPhrase, Phrase::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun fromPhraseList(phraseList : List<Phrase>?) : String? {
            return Gson().toJson(phraseList)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToPhraseList(jsonPhraseList: String?): List<Phrase>? {
            return Gson().fromJson(jsonPhraseList, object : TypeToken<List<Phrase>>() {}.type)
        }
    }
}