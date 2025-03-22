package com.task.cybersapiant.ui.task.list.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.style.TextDecoration
import com.task.cybersapiant.data.model.DataTask
import com.task.cybersapiant.ui.theme.AppTypography
import com.task.cybersapiant.ui.theme.Dimens
import org.burnoutcrew.reorderable.ReorderableLazyListState
import org.burnoutcrew.reorderable.detectReorderAfterLongPress

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskItem(
    task: DataTask,
    onDelete: (DataTask) -> Unit,
    onComplete: (DataTask) -> Unit,
    onClick: (DataTask) -> Unit,
    hapticFeedback: HapticFeedback,
    reorderState: ReorderableLazyListState,
) {
    val dismissState = rememberDismissState()
    val colors = MaterialTheme.colorScheme
    val typography = AppTypography

    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        onDelete(task)
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    } else if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
        onComplete(task)
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    val textColor = if (task.isCompleted) colors.onSurface.copy(alpha = 0.6f) else colors.onSurface
    val taskIcon = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.Notifications

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(0.5f) },
        background = {
            val color = when (dismissState.dismissDirection) {
                DismissDirection.StartToEnd -> Color.Green
                DismissDirection.EndToStart -> colors.error
                else -> Color.Transparent
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(Dimens.ItemSpacing),
                contentAlignment = Alignment.CenterStart,
            ) {
                Icon(
                    imageVector =
                    if (dismissState.dismissDirection == DismissDirection.StartToEnd)
                        Icons.Default.Done else Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(task) }
                    .detectReorderAfterLongPress(reorderState)
                    .widthIn(max = Dimens.MaxCardWidth),
                elevation = CardDefaults.cardElevation(Dimens.CardElevation)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.ItemSpacing)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = taskIcon,
                            contentDescription = if (task.isCompleted) "Completed" else "Pending",
                            tint = if (task.isCompleted) colors.primary else colors.secondary,
                            modifier = Modifier.padding(end = Dimens.IconPadding)
                        )
                        Text(
                            text = task.title,
                            style = typography.bodyLarge,
                            color = textColor,
                            textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                        )
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "View Details",
                            modifier = Modifier
                                .padding(Dimens.IconPadding)
                                .size(Dimens.IconSize)
                        )
                    }
                    Spacer(modifier = Modifier.height(Dimens.ItemSpacing))
                    Text(
                        text = task.description ?: "No description",
                        style = typography.bodyMedium,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.height(Dimens.ItemSpacing / 2))
                    Text(
                        text = "Priority: ${task.priority}",
                        style = typography.bodySmall,
                        color = colors.onSurfaceVariant
                    )
                    Text(
                        text = "Due: ${task.dueDate}",
                        style = typography.bodySmall,
                        color = colors.onSurfaceVariant
                    )
                }
            }
        }
    )
}




