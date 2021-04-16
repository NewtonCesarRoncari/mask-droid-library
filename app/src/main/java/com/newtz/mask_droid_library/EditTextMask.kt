package com.newtz.mask_droid_library

import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged

class InputMask(private val editText: AppCompatEditText) {

    fun moneyMask() {
        editText.doOnTextChanged { text, _, _, _ ->
            formatFieldForMoneyMask(text)
        }
    }

    private fun formatFieldForMoneyMask(text: CharSequence?) {
        if (!text.toString().matches("^\\$(\\d{1,3}(,\\d{3})*|(\\d+))(\\.\\d{2})?$".toRegex())) {
            val originalCursorPosition: Int = editText.selectionStart
            var cursorOffset = 0
            val cursorAtEnd = originalCursorPosition == text.toString().length
            val userInput = "" + text.toString().replace("[^\\d]".toRegex(), "")
            val cashAmountBuilder = StringBuilder(userInput)
            while (cashAmountBuilder.length > 3 && cashAmountBuilder[0] == '0') {
                cashAmountBuilder.deleteCharAt(0)
                cursorOffset--
            }
            while (cashAmountBuilder.length < 3) {
                cashAmountBuilder.insert(0, '0')
                cursorOffset++
            }
            cashAmountBuilder.insert(cashAmountBuilder.length - 2, '.')
            cashAmountBuilder.insert(0, '$')
            editText.setText(cashAmountBuilder.toString())
            editText.setSelection(
                if (cursorAtEnd) editText.text.toString()
                    .length else originalCursorPosition + cursorOffset
            )
        }
    }

}
