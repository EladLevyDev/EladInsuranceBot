package t.com.myapplication.data.model.step.user

import t.com.myapplication.data.model.message.ErrorMessage
import t.com.myapplication.data.model.message.Message

/**
 * Created by eladlevy on 17/02/2018.
 */
open class BaseSelectionActionStep(message: Message, errorMessage: ErrorMessage, var btnPositiveText: String, var btnNegativeText: String) : BaseUserActionStep(message, errorMessage) {

    override fun isInputValid(input: Any?): Boolean {
        return input == true
    }

}