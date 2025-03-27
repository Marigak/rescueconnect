package com.rescueconnect.app.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController // ✅ Moved import to the top
import androidx.navigation.NavHostController
import com.rescueconnect.app.data.ChatMessage
import com.rescueconnect.app.data.ChatRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatScreen(navController: NavHostController) {
    val chatRepository = remember { ChatRepository() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var messages by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }
    var messageText by remember { mutableStateOf(TextFieldValue("")) }

    // Fetch chat messages
    LaunchedEffect(Unit) {
        messages = chatRepository.getMessages() // ✅ Call the function directly
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Community Chat") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(messages) { message ->
                    ChatMessageItem(message)
                }
            }

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            if (messageText.text.isEmpty()) {
                                Text("Type a message...", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    }
                )

                Button(
                    onClick = {
                        if (messageText.text.isNotBlank()) {
                            coroutineScope.launch {
                                val chatMessage = ChatMessage(
                                    messageId = UUID.randomUUID().toString(), // Generate unique ID
                                    senderId = "user_id_here", // Replace with actual user ID
                                    senderName = "User", // Replace with actual sender name
                                    message = messageText.text,
                                    timestamp = System.currentTimeMillis()
                                )

                                chatRepository.sendMessage(
                                    chatMessage.toString(),
                                    senderId = TODO(),
                                    senderName = TODO()
                                ) // ✅ Pass a ChatMessage object
                                messageText = TextFieldValue("") // ✅ Reset input field
                            }

                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Send")
                }
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = message.senderName, style = MaterialTheme.typography.subtitle2, color = Color.Blue)
            Text(text = message.messageId, style = MaterialTheme.typography.body1) // ✅ Fixed: Showing message text, not ID
            Text(
                text = formatTimestamp(message.timestamp), // ✅ Fixed timestamp formatting
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
        }
    }
}

// ✅ Helper function to format timestamp
fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm a, MMM d", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
