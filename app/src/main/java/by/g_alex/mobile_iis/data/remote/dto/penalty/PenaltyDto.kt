package by.g_alex.mobile_iis.data.remote.dto.penalty

import by.g_alex.mobile_iis.domain.model.profile.penalty_model.PenaltyModel

data class PenaltyDto(
    val commentary: String,
    val directiveDto: DirectiveDto,
    val id: Int,
    val reason: String
)
fun PenaltyDto.toPenltyModel():PenaltyModel{
    return PenaltyModel(
        penaltyDate = directiveDto.date,
        penaltyType = directiveDto.typeName,
        reason = commentary
    )
}