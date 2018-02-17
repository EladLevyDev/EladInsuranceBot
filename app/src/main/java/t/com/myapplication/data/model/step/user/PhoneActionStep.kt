package t.com.myapplication.data.model.step.user

import t.com.myapplication.data.model.message.ErrorMessage
import t.com.myapplication.data.model.message.Message

/**
 * Created by eladlevy on 16/02/2018.
 */
class PhoneActionStep(message: Message, errorMessage: ErrorMessage) : BaseUserActionStep(message, errorMessage) {

    override fun isInputValid(input: Any?): Boolean {
        return validatePhoneNumber(input.toString()) && input.toString().length == 10
    }
    // validate first name
    fun validatePhoneNumber(value: String): Boolean {
        return value.matches(Regex("\\d+(?:\\.\\d+)?"))
    }

}