package com.maeiapp.data.responce

data class LoginResponse(
    val abouts: Any,
    val created_at: String,
    val dob: String,
    val email: String,
    val email_verified_at: Any,
    val fcm_token: String,
    val gender: String,
    val id: Int,
    val mobile: String,
    val name: String,
    val notification: String,
    val phone: Any,
    val popular: String,
    val position: String,
    val profileimage: String,
    val roledata: String,
    val roles: List<Role>,
    val speciality_id: String,
    val status: String,
    val token: String,
    val updated_at: String,
    val is_verified: Int
)

data class Role(
    val created_at: Any,
    val description: Any,
    val display_name: String,
    val id: Int,
    val name: String,
    val pivot: Pivot,
    val updated_at: Any
)

data class Pivot(
    val role_id: String,
    val user_id: String
)