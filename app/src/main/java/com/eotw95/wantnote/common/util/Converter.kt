package com.eotw95.wantnote.common.util

import androidx.room.TypeConverter
import com.eotw95.wantnote.room.WantItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converter {
    @TypeConverter
    fun fromString(value: String?): List<WantItem> {
        val listType: Type = object : TypeToken<List<WantItem?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<WantItem?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}