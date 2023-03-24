package by.g_alex.mobile_iis.data.remote.dto.profile

import by.g_alex.mobile_iis.domain.model.profile.Skill

data class SkillDto(
    val id: Int, // 154
    val name: String // C/C++
) {
    fun toSkill(): Skill {
        return Skill(
            id = id,
            name = name
        )
    }
}