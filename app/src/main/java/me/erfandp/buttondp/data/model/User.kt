package me.erfandp.buttondp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "userTable")
data class User(
	@ColumnInfo(name = "full_name") val fullName: String,
	@ColumnInfo(name = "username")val userName: String,
	@ColumnInfo(name = "password")val password: String,
	@PrimaryKey val id: UUID = UUID.randomUUID()
)

class UUIDConverter {
	@TypeConverter
	fun fromString(uuid: String?): UUID {
		return UUID.fromString(uuid)
	}
	
	@TypeConverter
	fun fromUUID(uuid: UUID): String {
		return uuid.toString()
	}
}
