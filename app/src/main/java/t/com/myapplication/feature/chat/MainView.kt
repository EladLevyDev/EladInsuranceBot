package t.com.myapplication.feature.chat

import com.stfalcon.chatkit.messages.MessagesListAdapter
import t.com.myapplication.base.BaseView
import t.com.myapplication.data.model.message.ErrorMessage
import t.com.myapplication.data.model.message.Message
import t.com.myapplication.data.model.step.base.BaseStep

/**
 * Created by eladlevy on 13/02/2018.
 */
interface MainView : BaseView {
    fun setAdapter(messageAdapter: MessagesListAdapter<Message>)
    fun showNewStepData(step: BaseStep)
    fun displayUserInputMessage(message: Message)
    fun setBotTypingStatus(isTyping: Boolean)
    fun restartBot()
    fun showError(errorMessage: ErrorMessage)
}