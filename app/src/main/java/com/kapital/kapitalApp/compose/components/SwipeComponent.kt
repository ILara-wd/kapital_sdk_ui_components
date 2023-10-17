package com.kapital.kapitalApp.compose.components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.Kapital.KapitalApp.R
import kotlinx.coroutines.delay



fun SwipeComponent() {

}

/**
 * Composable representing an email item with swipe-to-dismiss functionality.
 *
 * @param emailMessage The email message to display.
 * @param onRemove Callback invoked when the email item is dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailItem(
    emailMessage: EmailMessage,
    onRemove: (EmailMessage) -> Unit
) {
    val context = LocalContext.current
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(emailMessage)
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                show = false
                true
            } else false
        }, positionalThreshold = { 150.dp.toPx() }
    )
    AnimatedVisibility(
        show,exit = fadeOut(spring())
    ) {
        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier,
            background = {
                DismissBackground(dismissState)
            },
            dismissContent = {
                EmailMessageCard(emailMessage)
            }
        )
    }

    LaunchedEffect(show) {
        if (!show) {
            delay(800)
            onRemove(currentItem)
            Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val color = when (dismissState.dismissDirection) {
        DismissDirection.StartToEnd -> Color(0xFFFF1744)
        DismissDirection.EndToStart -> Color(0xFF1DE9B6)
        null -> Color.Transparent
    }
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (direction == DismissDirection.StartToEnd) Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart) Icon(
            // make sure add baseline_archive_24 resource to drawable folder
            painter = painterResource(R.drawable.baseline_archive_24),
            contentDescription = "Archive"
        )
    }
}

/**
 * Composable that represents a single email message card.
 *
 * @param emailMessage The email message to display.
 */
@Composable
fun EmailMessageCard(emailMessage: EmailMessage) {
    ListItem(
        modifier = Modifier.clip(MaterialTheme.shapes.small),
        headlineContent = {
            Text(
                emailMessage.sender,
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Text(
                emailMessage.message,
                style = MaterialTheme.typography.bodySmall
            )
        },
        leadingContent = {
            Icon(
                Icons.Filled.Person,
                contentDescription = "person icon",
                Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(10.dp)
            )
        }
    )
}

data class EmailMessage(
    val sender: String,
    val message: String
)

class EmailViewModel : ViewModel() {

    // StateFlow to hold the list of email messages
    private val _messagesState = MutableStateFlow(emptyList<EmailMessage>())
    val messagesState: StateFlow<List<EmailMessage>> = _messagesState.asStateFlow()

    init {
        // Initialize the list of email messages
        // when the ViewModel is created
        _messagesState.update { sampleMessages() }
    }

    /**
     * Refreshes the list of email messages.
     */
    fun refresh() {
        _messagesState.update {
            sampleMessages()
        }
    }

    /**
     * Removes an email message from the list.
     * @param currentItem The email message to be removed.
     */
    fun removeItem(currentItem: EmailMessage) {
        _messagesState.update {
            val mutableList = it.toMutableList()
            mutableList.remove(currentItem)
            mutableList
        }
    }

    /**
     * Generates a list of sample email messages.
     * @return The list of sample email messages.
     */
    private fun sampleMessages() = listOf(
        EmailMessage("John Doe", "Hello"),
        EmailMessage("Alice", "Hey there! How's it going?"),
        EmailMessage("Bob", "I just discovered a cool new programming language!"),
        EmailMessage("Geek", "Have you seen the latest tech news? It's fascinating!"),
        EmailMessage("Mark", "Let's grab a coffee and talk about coding!"),
        EmailMessage("Cyan", "I need help with a coding problem. Can you assist me?"),
    )
}

