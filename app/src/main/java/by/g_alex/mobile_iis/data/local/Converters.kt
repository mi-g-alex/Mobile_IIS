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
    fun toSkillJson(meaning: List<Skill>) : String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<ArrayList<Skill>>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromMeaningsJson(json: String): List<Skill>{
        return jsonParser.fromJson<ArrayList<Skill>>(
            json,
            object: TypeToken<ArrayList<Skill>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toRefJson(meaning: List<Reference>) : String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<ArrayList<Skill>>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromRefJson(json: String): List<Reference>{
        return jsonParser.fromJson<ArrayList<Reference>>(
            json,
            object: TypeToken<ArrayList<Reference>>(){}.type
        ) ?: emptyList()
    }
}