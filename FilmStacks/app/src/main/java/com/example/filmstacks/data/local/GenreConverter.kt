package com.example.filmstacks.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreConverter {
    @TypeConverter
    fun fromString(value: String?): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Int>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromGenres(genres: List<GenreEntity>?): String? {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenres(genreJson: String?): List<GenreEntity>? {
        val listType = object : TypeToken<List<GenreEntity>>() {}.type
        return Gson().fromJson(genreJson, listType)
    }

    @TypeConverter
    fun fromCompanies(production_companies: List<CompaniesEntity>?): String? {
        return Gson().toJson(production_companies)
    }

    @TypeConverter
    fun toCompanies(companiesJson: String?): List<CompaniesEntity>? {
        val listType = object : TypeToken<List<CompaniesEntity>>() {}.type
        return Gson().fromJson(companiesJson, listType)
    }
}