package com.team6.smartbudget.core.presentation.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle

internal class PhoneVisualTransformation(
    private val maskStyle: TextStyle,
    private val maskChar: Char = '_',
) : VisualTransformation {
    companion object {
        // Constants defining phone number format settings.
        const val MAX_PHONE_DIGITS = 10

        // Segment lengths for the phone number format: +7 (xxx) xxx-xx-xx.
        const val AREA_CODE_LENGTH = 3 // "xxx" in the area code.
        const val PREFIX_LENGTH = 3 // "xxx" following the area code.
        const val FIRST_SUFFIX_LENGTH = 2 // first "xx" group.

        // Formatting constants.
        const val COUNTRY_CODE = "+7 " // Country code with trailing space.
        const val OPENING_BRACKET = "(" // Opening bracket for area code.
        const val CLOSING_BRACKET_WITH_SPACE = ") " // Closing bracket followed by a space.
        const val HYPHEN = "-" // Hyphen between number groups.

        // Fixed positions in the formatted text where user digits appear.
        // These indices are based on the full formatted string structure: "+7 (xxx) xxx-xx-xx"
        val originalDigitPositions = listOf(4, 5, 6, 9, 10, 11, 13, 14, 16, 17)
    }

    override fun filter(text: AnnotatedString): TransformedText {
        // Filter out non-digit characters and limit to MAX_PHONE_DIGITS.
        val trimmed = text.text.filter { it.isDigit() }.take(MAX_PHONE_DIGITS)
        // Capture the count of digits entered.
        val digitCount = trimmed.length

        val formattedText = buildAnnotatedString {
            // Build the formatted text by appending each segment.
            append(COUNTRY_CODE)
            append(OPENING_BRACKET)
            for (i in 0 until AREA_CODE_LENGTH) {
                if (i < digitCount) {
                    append(trimmed[i])
                } else {
                    appendMaskChar()
                }
            }
            append(CLOSING_BRACKET_WITH_SPACE)
            for (i in AREA_CODE_LENGTH until AREA_CODE_LENGTH + PREFIX_LENGTH) {
                if (i < digitCount) {
                    append(trimmed[i])
                } else {
                    appendMaskChar()
                }
            }
            append(HYPHEN)
            for (i in AREA_CODE_LENGTH + PREFIX_LENGTH until
                AREA_CODE_LENGTH + PREFIX_LENGTH + FIRST_SUFFIX_LENGTH) {
                if (i < digitCount) {
                    append(trimmed[i])
                } else {
                    appendMaskChar()
                }
            }
            append(HYPHEN)
            val start = AREA_CODE_LENGTH + PREFIX_LENGTH + FIRST_SUFFIX_LENGTH
            for (i in start until MAX_PHONE_DIGITS) {
                if (i < digitCount) {
                    append(trimmed[i])
                } else {
                    appendMaskChar()
                }
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // Clamp the offset using MAX_PHONE_DIGITS.
                return if (offset in 0 until MAX_PHONE_DIGITS) {
                    originalDigitPositions[offset]
                } else {
                    formattedText.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                // If no digits have been entered, all positions map to 0.
                if (digitCount == 0) return 0

                // If offset is before the position of the first inserted digit, return 0.
                if (offset <= originalDigitPositions.first()) return 0

                // For each inserted digit,
                // if the offset is less than or equal to its mapped position,
                // return that index.
                for (i in 0 until digitCount) {
                    if (offset <= originalDigitPositions[i]) return i
                }
                // If offset exceeds the last inserted digit position, return the count of digits.
                return digitCount
            }
        }

        return TransformedText(formattedText, offsetMapping)
    }

    private fun AnnotatedString.Builder.appendMaskChar() {
        withStyle(maskStyle.toSpanStyle()) {
            append(maskChar)
        }
    }
}
