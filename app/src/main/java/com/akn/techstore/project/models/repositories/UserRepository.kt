package com.akn.techstore.project.models.repositories

import androidx.lifecycle.LiveData
import com.akn.techstore.project.database.TechStoreDao
import com.akn.techstore.project.models.dataModels.Setting
import com.akn.techstore.project.models.dataModels.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await


class UserRepository(private val userDao: TechStoreDao) {

    private val auth: FirebaseAuth = Firebase.auth
    val user : LiveData<User?> = userDao.getUserProfile()

    fun getErrorMessage(e: Exception): String {
        return when (e) {
            is FirebaseAuthException -> when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> "Format d'email invalide."
                "ERROR_WRONG_PASSWORD" -> "Mot de passe incorrect."
                "ERROR_USER_NOT_FOUND" -> "Aucun compte trouvé avec cet email."
                "ERROR_EMAIL_ALREADY_IN_USE" -> "Cet email est déjà utilisé."
                "ERROR_WEAK_PASSWORD" -> "Le mot de passe doit contenir au moins 6 caractères."
                else -> "Erreur Firebase : ${e.localizedMessage}"
            }
            else -> "Une erreur inattendue s'est produite."
        }
    }

    private suspend fun saveUserLocally(uid: String, name: String?, email: String?) {
        val user = User(
            uid = uid,
            name = name ?: "Utilisateur",
            email = email ?: ""
        )
        userDao.insertUser(user)
    }

    suspend fun login(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            val user = auth.currentUser
            if (user != null) {
                saveUserLocally(user.uid, user.displayName, user.email)
            }
            true
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun register(name: String, email: String, password: String): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
                user.updateProfile(profileUpdates).await()
                saveUserLocally(user.uid, name, email)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            throw e
        }
    }
    suspend fun getLoggedInUser(): User? = userDao.getLoggedInUser()
    suspend fun clearUser() = userDao.clearUser()
    suspend fun getIsStartedStatus() = userDao.getIsStartedStatus()
    suspend fun insertSettings(settings: Setting) = userDao.insertSettings(settings)
    suspend fun getSettings() = userDao.getSettings()
}