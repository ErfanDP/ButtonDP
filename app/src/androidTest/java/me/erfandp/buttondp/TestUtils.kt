package me.erfandp.buttondp

import me.erfandp.buttondp.data.model.User
import java.util.*

object TestUtils {
	fun createTestUser(username:String = "testUsername",password:String = "testPassword",
					   fullName:String= "testFullName" , id:UUID = UUID.randomUUID()):User{
		return User(fullName,username,password,id)
	}

}