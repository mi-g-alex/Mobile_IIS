package by.g_alex.mobile_iis.data.remote.dto.profile

import by.g_alex.mobile_iis.domain.model.profile.Reference

data class ReferenceDto(
    val id: Int, // 4371881
    val name: String, // TG
    val reference: String // t.me/mi_g_alex
) {
    fun toReference() : Reference {
        return Reference(
            id = id,
            name = name,
            reference = reference
        )
    }
}