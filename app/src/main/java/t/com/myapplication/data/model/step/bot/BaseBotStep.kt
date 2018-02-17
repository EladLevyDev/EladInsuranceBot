package t.com.myapplication.data.model.step.bot

import t.com.myapplication.data.model.message.Message
import t.com.myapplication.data.model.step.base.BaseStep

/**
 * Created by eladlevy on 16/02/2018.
 */
open class BaseBotStep(message: Message) : BaseStep(message) {

    override fun isValidationRequired(): Boolean {
        return false
    }

}