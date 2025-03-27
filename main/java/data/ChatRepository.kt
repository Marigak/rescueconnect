package com.rescueconnect.app.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class ChatMessage(
    val messageId: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

class ChatRepository {
    private val db = FirebaseFirestore.getInstance()
    private val messagesCollection = db.collection("messages")

    suspend fun sendMessage(text: String, senderId: String, senderName: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val newMessage = ChatMessage(
                messageId = messagesCollection.document().id, // ✅ Generate unique ID
                senderId = senderId,
                senderName = senderName,
                message = text,
                timestamp = System.currentTimeMillis()
            )

            messagesCollection.document(newMessage.messageId).set(newMessage).await()
            sendPushNotification(newMessage)
            true
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun sendPushNotification(message: ChatMessage) {
        val notificationData = mapOf(
            "title" to "New Message from ${message.senderName}",
            "message" to message.message
        )
        FirebaseFirestore.getInstance().collection("notifications").add(notificationData).await()
    }

    suspend fun getMessages(): List<ChatMessage> = withContext(Dispatchers.IO) {
        return@withContext try {
            val snapshot = messagesCollection.orderBy("timestamp").get().await()
            snapshot.documents.mapNotNull { it.toObject(ChatMessage::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
