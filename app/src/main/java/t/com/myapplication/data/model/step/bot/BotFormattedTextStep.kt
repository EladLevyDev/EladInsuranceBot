package t.com.myapplication.data.model.step.bot

import t.com.myapplication.data.model.message.Message

/**
 * Created by eladlevy on 16/02/2018.
 */
class BotFormattedTextStep(message: Message, textToChange: String) : BaseBotStep(message) {

    init {
        message.text?.replace("%%", textToChange)?.let { message.setText(it) }
    }

}