package by.g_alex.mobile_iis.data.remote.dto.penalty

data class PenaltyDto(
    val commentary: String,
    val directiveDto: DirectiveDto,
    val id: Int,
    val reason: String
)