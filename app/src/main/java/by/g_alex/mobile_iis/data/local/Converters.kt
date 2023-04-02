package by.g_alex.mobile_iis.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import by.g_alex.mobile_iis.data.util.JsonParser
import by.g_alex.mobile_iis.domain.model.profile.Reference
import by.g_alex.mobile_iis.domain.model.profile.Skill
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun listSkillToJson(meaning: List<Skill>): String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<ArrayList<Skill>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun listSkillFromJson(json: String): List<Skill> {
        return jsonParser.fromJson<ArrayList<Skill>>(
            json,
            object : TypeToken<ArrayList<Skill>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun listReferenceToJson(meaning: List<Reference>): String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<ArrayList<Reference>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun listReferenceFromJson(json: String): List<Reference> {
        return jsonParser.fromJson<ArrayList<Reference>>(
            json,
            object : TypeToken<ArrayList<Reference>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun listIntToString(list: List<Int>?): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<Int>?>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun listIntFromString(json: String): List<Int>? {
        return jsonParser.fromJson<ArrayList<Int>?>(
            json,
            object : TypeToken<ArrayList<Int>?>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun listStringToString(list: List<String>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<String>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun listStringFromString(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>() {}.type
        ) ?: emptyList()
    }
}