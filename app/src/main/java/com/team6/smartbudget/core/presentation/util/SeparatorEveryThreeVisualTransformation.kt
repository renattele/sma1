package com.team6.smartbudget.core.presentation.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

private const val SeparatorCharacterCount = 3

/**
 * A [VisualTransformation] that inserts a separator every 3 characters starting from the end.
 *
 * For example, given "1234567" it outputs "1 234 567" (if separator is a space).
 *
 * @param separator The string to insert as a separator.
 */
internal class SeparatorEveryThreeVisualTransformation(
    private val separator: String = " ",
) : VisualTransformation {
    companion object {
        const val DEFAULT_GROUP_SIZE = 3
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        if (originalText.isEmpty()) return TransformedText(text, OffsetMapping.Identity)

        val sb = StringBuilder()
        val originalMapping = IntArray(originalText.length)
        var transformedIndex = 0

        val firstGroupLength =
            if (originalText.length % DEFAULT_GROUP_SIZE == 0) {
                DEFAULT_GROUP_SIZE
            } else {
                originalText.length % DEFAULT_GROUP_SIZE
            }

        var index = 0
        for (i in 0 until firstGroupLength) {
            originalMapping[index] = transformedIndex
            sb.append(originalText[index])
            transformedIndex++
            index++
        }

        while (index < originalText.length) {
            sb.append(separator)
            transformedIndex += separator.length

            for (i in 0 until SeparatorCharacterCount) {
                if (index >= originalText.length) break
                originalMapping[index] = transformedIndex
                sb.append(originalText[index])
                transformedIndex++
                index++
            }
        }

        val output = sb.toString()
        val transformedText = AnnotatedString(output)

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = when {
                offset <= 0 -> 0
                offset >= originalText.length -> output.length
                else -> originalMapping[offset]
            }

            override fun transformedToOriginal(offset: Int): Int {
                var result = 0
                for (i in originalMapping.indices) {
                    if (originalMapping[i] < offset) {
                        result = i + 1
                    } else {
                        break
                    }
                }
                return result
            }
        }

        return TransformedText(transformedText, offsetMapping)
    }
}
