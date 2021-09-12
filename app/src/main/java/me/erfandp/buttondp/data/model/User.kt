package me.erfandp.buttondp.data.model

import java.util.*

data class User(
	val fullName: String,
	val userName: String,
	val password: String,
	val id: UUID = UUID.randomUUID()
)
