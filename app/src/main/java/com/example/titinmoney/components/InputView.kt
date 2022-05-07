package com.example.titinmoney.components

import android.content.Context
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.example.titinmoney.R
import com.example.titinmoney.databinding.ViewInputBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.DecimalFormat


class InputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private val label: TextInputLayout
    private val input: TextInputEditText
    private val labelText: String?
    private val inputType: String?
    private val maxLength: String?
    private val required: Boolean

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.InputView)
        labelText = attributes.getString(R.styleable.InputView_label)
        inputType = attributes.getString(R.styleable.InputView_android_inputType)
        maxLength = attributes.getString(R.styleable.InputView_android_maxLength)
        required = attributes.getString(R.styleable.InputView_android_required).toBoolean()
        attributes.recycle()

        val view = LayoutInflater.from(context).inflate(R.layout.view_input, this, true)
        val binding = ViewInputBinding.bind(view)

        label = binding.box
        input = binding.input

        setAttributes()

        input.doOnTextChanged { _, _, _, _ ->
            isValid()
        }
    }

    private fun setAttributes() {
        label.hint = labelText

        if (!maxLength.isNullOrBlank())
            input.filters = arrayOf<InputFilter>(LengthFilter(maxLength.toInt()))

        if (!inputType.isNullOrBlank())
            input.inputType = Integer.decode(inputType)
    }

    fun getValue(): String {
        return input.text.toString()
    }

    fun isValid(): Boolean {
        if (input.text.isNullOrEmpty() && required) {
            label.error = context.getString(R.string.field_required)
            return false
        }

        label.error = null
        return true
    }

    fun clear() {
        input.text?.clear()
        label.error = null
    }
}