package me.erfandp.buttondp

import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import me.erfandp.buttondp.data.repositories.UserRepository
import org.junit.After
import org.junit.BeforeClass
import org.junit.Test

class UserRepositoryTest {
	
	
	@Test
	fun writeUserAndReadInList() = runBlocking {
		val user = TestUtils.createTestUser("erfan")
		UserRepository.signUpUser(user.fullName, user.userName, user.password, user.id)
		val byName = UserRepository.getUserByUsername("erfan")
		assertThat(byName).isEqualTo(user)
	}
	
	@Test
	fun usernameAlreadyExistSignUp() = runBlocking {
		val user1 = TestUtils.createTestUser("erfandp")
		val user2 = TestUtils.createTestUser("erfandp")
		val firstUserAnswer =
			UserRepository.signUpUser(user1.fullName, user1.userName, user1.password, user1.id)
		val secondUserAnswer =
			UserRepository.signUpUser(user2.fullName, user2.userName, user2.password, user2.id)
		assertThat(firstUserAnswer).isTrue()
		assertThat(secondUserAnswer).isFalse()
	}
	
	@After
	fun tearDown() = runBlocking {
		UserRepository.clearDataBase()
	}
	
	companion object {
		@JvmStatic
		@BeforeClass
		fun initRep() {
			UserRepository.initializeRepository(ApplicationProvider.getApplicationContext())
		}
		
		
	}
	
}
