package t.com.myapplication.data.model.message

import t.com.myapplication.data.model.BaseUser
import t.com.myapplication.injection.ConfigPersistent
import java.util.*
import javax.inject.Inject

/**
 * Created by eladlevy on 16/02/2018.
 */

@ConfigPersistent
class MessagesMockData @Inject
constructor() {
    fun getMessageObject(index: Int): Message {
        val messageText = getMessage(index)
        return getBaseMessageObject(messageText)
    }

    fun getErrorObject(error: String, errorShouldFinishAction: Boolean): ErrorMessage {
        return getBaseErrorObject(error, errorShouldFinishAction)
    }

    private fun getMessage(index: Int): String {
        return messages[index]
    }

    private fun getBaseErrorObject(text: String, errorShouldFinishAction: Boolean): ErrorMessage {
        val calendar = Calendar.getInstance()
        val errorMessage = ErrorMessage(getRandomId(), getBotUser(), text, Date(), errorShouldFinishAction)
        errorMessage.setCreatedAt(calendar.time)
        return errorMessage
    }


    private fun getBaseMessageObject(text: String): Message {
        val calendar = Calendar.getInstance()
        val message = Message(getRandomId(), getBotUser(), text)
        message.setCreatedAt(calendar.time)
        return message
    }

    private val messages: ArrayList<String> = object : ArrayList<String>() {
        init {
            add("Hey! I'm Maya. I'll get you an awesome price in seconds. Ready to go?")
            add("let's start with your name")
            add("Great to meet you %% :)")
            add("What's your phone number?")
            add("Do you agree to our terms of service?")
            add("Thanks! This is the last step!")
            add("What do you want to do now?")
        }
    }

    companion object MockData {

        object Selection {
            val TERMS_POSITIVE_BUTTON = "YES"
            val TERMS_NEGATIVE_BUTTON = "NO"

            val FINISH_POSITIVE_BUTTON = "RESTART"
            val FINISH_NEGATIVE_BUTTON = "EXIT"
        }

        object Errors {
            val FIRST_NAME = "Name must be one word and longer then 2 letters!"
            val PHONE = "Invalid Phone number (Current support for Israel only)"
            val SELECTION_FINISH_ERROR = "Oki Bye Bye! i'm out!"
        }

    }


    private fun getRandomId(): String {
        return UUID.randomUUID().toString()
    }

    private val botAvatar = "https://www.lemonade.com/assets/avatars/Maya.jpg"
    private fun getBotUser(): BaseUser {
        return BaseUser(
                "1",
                "Insurance Bot",
                botAvatar)
    }

}
