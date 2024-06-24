package com.sam.swiftsend.data.repository

import android.content.Context
import androidx.credentials.GetCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.sam.swiftsend.data.model.AuthenticatedUser
import kotlinx.coroutines.tasks.await


interface IAuthRepository {

    suspend fun signInWithGoogle(isAuthorized: Boolean = true): AuthenticatedUser?
    suspend fun createAccountWithEmailAndPassword(email: String, password: String): AuthenticatedUser?
    suspend fun  signInWithEmailAndPassword(email: String, password: String): AuthenticatedUser?
    fun isSignedIn(): Boolean
}

class AuthRepository(
    private val context: Context
): IAuthRepository {
    private val credentialManager = CredentialManager.create(context)

    //create firebase auth object
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    //reference current signed in user
    private var user: AuthenticatedUser? = null

    override suspend fun signInWithGoogle(isAuthorized: Boolean): AuthenticatedUser? {

        try{
            //create a GoogleIdOption
            val googleIdOption = buildGoogleId(isAuthorized)

            //create sign in request
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            //process sign in results
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            //get GoogleID token => JWT-like string that has your Google info.
            val tokenCredential = GoogleIdTokenCredential
                .createFrom(result.credential.data)

            //show token
            println("Token: ${tokenCredential.data}")
        }catch (e:Exception){

            println("errors occurred: ${e.message}")
            if (e is NoCredentialException){
                signInWithGoogle(isAuthorized = false)
            }

            //show error
            println("Token error: ${e.message}")
        }

        return null
    }

    override suspend fun createAccountWithEmailAndPassword(
        email: String,
        password: String
    ): AuthenticatedUser? {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ }
            .await()
        val currentSignedInUser = auth.currentUser
        currentSignedInUser?.let { firebaseUser ->
            val userEmail = firebaseUser.email
            val uid = firebaseUser.uid
            user = AuthenticatedUser(uid, userEmail ?: email)
        }
        return user
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): AuthenticatedUser? {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{}
            .await()
        val currentSignedInUser = auth.currentUser
        currentSignedInUser?.let { firebaseUser ->
            val userEmail = firebaseUser.email
            val uid = firebaseUser.uid
            user = AuthenticatedUser(uid, userEmail ?: email)
        }
        return user
    }

    override fun isSignedIn(): Boolean {
        TODO("Not yet implemented")
    }

    private fun buildGoogleId(isAuthorized: Boolean = true): GetGoogleIdOption =
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(isAuthorized)
            .setServerClientId("177864576363-4103frintmf61ekbc4mtdlvbghk19138.apps.googleusercontent.com")
            .setAutoSelectEnabled(true)
            .build()
}