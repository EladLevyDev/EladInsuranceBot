package t.com.myapplication.data.model.message

import t.com.myapplication.data.model.BaseUser
import java.util.*

/**
 * Created by eladlevy on 17/02/2018.
 */
open class ErrorMessage(id: String, user: BaseUser, text: String?, createdAt: Date? = Date(), var shouldFinish: Boolean) : Message(id, user, text, createdAt) {

    fun isErrorShouldFinish() : Boolean {
        return  shouldFinish
    }
}