package com.revenuecat.purchases.kmp.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.ui.revenuecatui.CustomVariableValue

private enum class VariableType { String, Number, Boolean }

private fun CustomVariableValue.typeLabel(): kotlin.String = when (this) {
    is CustomVariableValue.StringValue -> "String"
    is CustomVariableValue.NumberValue -> "Number"
    is CustomVariableValue.BooleanValue -> "Boolean"
}

private fun CustomVariableValue.displayValue(): kotlin.String = when (this) {
    is CustomVariableValue.StringValue -> value
    is CustomVariableValue.NumberValue -> if (value % 1.0 == 0.0) value.toLong().toString() else value.toString()
    is CustomVariableValue.BooleanValue -> value.toString()
}

private val typeColors = mapOf(
    "String" to Color(0xFF1565C0),
    "Number" to Color(0xFF2E7D32),
    "Boolean" to Color(0xFF6A1B9A),
)

@Composable
internal fun CustomVariablesEditorScreen(
    variables: Map<kotlin.String, CustomVariableValue>,
    onVariablesChanged: (Map<kotlin.String, CustomVariableValue>) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var editingEntry by remember { mutableStateOf<Map.Entry<kotlin.String, CustomVariableValue>?>(null) }

    val sortedEntries = remember(variables) { variables.entries.sortedBy { it.key } }

    Column(modifier = modifier.statusBarsPadding()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(onClick = onBack) { Text("Back") }
            Text(
                text = "Custom Variables",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
            )
            IconButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add variable")
            }
        }
        Divider()

        if (sortedEntries.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No custom variables",
                        style = MaterialTheme.typography.h6,
                        color = Color.Gray,
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Tap + to add a variable",
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray,
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Use {{ custom.variable_name }} in paywalls",
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray,
                        fontFamily = FontFamily.Monospace,
                    )
                }
            }
        } else {
            LazyColumn {
                itemsIndexed(sortedEntries, key = { _, e -> e.key }) { index, entry ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = entry.key,
                                style = MaterialTheme.typography.body1,
                                fontFamily = FontFamily.Monospace,
                            )
                            Text(
                                text = entry.value.displayValue(),
                                style = MaterialTheme.typography.body2,
                                color = Color.Gray,
                                maxLines = 1,
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        TypeChip(label = entry.value.typeLabel())
                        IconButton(onClick = { editingEntry = entry }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(20.dp))
                        }
                        IconButton(onClick = {
                            onVariablesChanged(variables - entry.key)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(20.dp))
                        }
                    }
                    if (index < sortedEntries.lastIndex) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        VariableDialog(
            title = "Add Variable",
            initialKey = "",
            initialValue = CustomVariableValue.string(""),
            existingKeys = variables.keys,
            onConfirm = { key, value ->
                onVariablesChanged(variables + (key to value))
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false },
        )
    }

    editingEntry?.let { entry ->
        VariableDialog(
            title = "Edit Variable",
            initialKey = entry.key,
            initialValue = entry.value,
            existingKeys = variables.keys - entry.key,
            onConfirm = { key, value ->
                val updated = (variables - entry.key) + (key to value)
                onVariablesChanged(updated)
                editingEntry = null
            },
            onDismiss = { editingEntry = null },
        )
    }
}

@Composable
private fun TypeChip(label: kotlin.String) {
    val color = typeColors[label] ?: Color.Gray
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = color.copy(alpha = 0.1f),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.caption,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

@Composable
private fun VariableDialog(
    title: kotlin.String,
    initialKey: kotlin.String,
    initialValue: CustomVariableValue,
    existingKeys: Set<kotlin.String>,
    onConfirm: (kotlin.String, CustomVariableValue) -> Unit,
    onDismiss: () -> Unit,
) {
    var key by remember { mutableStateOf(initialKey) }
    var keyError by remember { mutableStateOf<kotlin.String?>(null) }
    var selectedType by remember {
        mutableStateOf(
            when (initialValue) {
                is CustomVariableValue.StringValue -> VariableType.String
                is CustomVariableValue.NumberValue -> VariableType.Number
                is CustomVariableValue.BooleanValue -> VariableType.Boolean
            }
        )
    }
    var valueText by remember { mutableStateOf(initialValue.displayValue()) }

    fun validate(): Boolean {
        if (key.isBlank()) {
            keyError = "Variable name is required"
            return false
        }
        if (!key.matches(Regex("^[a-zA-Z][a-zA-Z0-9_]*$"))) {
            keyError = "Must start with a letter, only alphanumeric and underscores"
            return false
        }
        if (key in existingKeys) {
            keyError = "Variable already exists"
            return false
        }
        return true
    }

    fun buildValue(): CustomVariableValue? = when (selectedType) {
        VariableType.String -> CustomVariableValue.string(valueText)
        VariableType.Number -> valueText.toDoubleOrNull()?.let { CustomVariableValue.number(it) }
        VariableType.Boolean -> when (valueText.lowercase()) {
            "true" -> CustomVariableValue.boolean(true)
            "false" -> CustomVariableValue.boolean(false)
            else -> null
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                TextField(
                    value = key,
                    onValueChange = { key = it; keyError = null },
                    label = { Text("Variable Name") },
                    placeholder = { Text("e.g. player_name") },
                    isError = keyError != null,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                keyError?.let { Text(it, color = MaterialTheme.colors.error, style = MaterialTheme.typography.caption) }

                Text("Type", style = MaterialTheme.typography.body2)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    VariableType.entries.forEach { type ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedType == type,
                                onClick = {
                                    selectedType = type
                                    valueText = when (type) {
                                        VariableType.Boolean -> "true"
                                        else -> ""
                                    }
                                },
                            )
                            Text(type.name, style = MaterialTheme.typography.body2)
                        }
                    }
                }

                when (selectedType) {
                    VariableType.String -> TextField(
                        value = valueText,
                        onValueChange = { valueText = it },
                        label = { Text("Value") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    VariableType.Number -> TextField(
                        value = valueText,
                        onValueChange = { valueText = it },
                        label = { Text("Value") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    VariableType.Boolean -> Row(verticalAlignment = Alignment.CenterVertically) {
                        listOf("true", "false").forEach { boolVal ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = valueText == boolVal,
                                    onClick = { valueText = boolVal },
                                )
                                Text(boolVal, style = MaterialTheme.typography.body2)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (validate()) {
                    val value = buildValue()
                    if (value != null) onConfirm(key.trim(), value)
                }
            }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
    )
}
