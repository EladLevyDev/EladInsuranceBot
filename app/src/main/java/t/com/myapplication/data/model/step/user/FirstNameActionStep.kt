package t.com.myapplication.data.model.step.user

import t.com.myapplication.data.model.message.ErrorMessage
import t.com.myapplication.data.model.message.Message

/**
 * Created by eladlevy on 16/02/2018.
 */
class FirstNameActionStep(message: Message, errorMessage: ErrorMessage) : BaseUserActionStep(message, errorMessage) {

    override fun isInputValid(input: Any?): Boolean {
        return validateFirstName(input.toString()) && input.toString().length > 1
    }
    // validate first name
    fun validateFirstName(firstName: String): Boolean {
        return firstName.matches("[A-Z][a-zA-Z]*".toRegex())
    }

}