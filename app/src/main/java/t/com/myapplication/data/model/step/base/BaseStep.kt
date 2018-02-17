package t.com.myapplication.data.model.step.base

import t.com.myapplication.data.model.message.Message


/**
 * Created by eladlevy on 16/02/2018.
 */
abstract class BaseStep(var message: Message) {

    abstract fun isValidationRequired(): Boolean
}