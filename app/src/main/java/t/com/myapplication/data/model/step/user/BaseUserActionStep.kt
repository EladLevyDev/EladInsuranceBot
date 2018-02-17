package t.com.myapplication.data.model.step.user

import t.com.myapplication.data.model.message.ErrorMessage
import t.com.myapplication.data.model.message.Message
import t.com.myapplication.data.model.step.base.BaseStep

/**
 * Created by eladlevy on 16/02/2018.
 */
open class BaseUserActionStep(message: Message, private val errorMessage: ErrorMessage) : BaseStep(message) {


    override fun isValidationRequired(): Boolean {
        return true
    }

    public open fun isInputValid(input: Any?): Boolean {
        return input.toString().length > 1
    }

    fun generateError(): ErrorMessage {
        return errorMessage
    }
}