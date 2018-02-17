package t.com.myapplication.feature.chat

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.stfalcon.chatkit.messages.MessagesListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import t.com.myapplication.R
import t.com.myapplication.base.BaseActivity
import t.com.myapplication.data.model.message.ErrorMessage
import t.com.myapplication.data.model.message.Message
import t.com.myapplication.data.model.step.base.BaseStep
import t.com.myapplication.data.model.step.user.BaseUserActionStep
import t.com.myapplication.util.visible
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var mainPresenter: MainPresenter

    lateinit var mainAdapter: MessagesListAdapter<Message>

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        mainPresenter.attachView(this)
        mainPresenter.initAdapter(this)
        input.setInputListener(mainPresenter)
        setSupportActionBar(mainToolbar)
    }


    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }

    override fun setAdapter(messageAdapter: MessagesListAdapter<Message>) {
        mainAdapter = messageAdapter
        messagesList?.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mainAdapter)
        }
        messagesList?.visible()
    }

    override fun showNewStepData(step: BaseStep) {
        mainAdapter.addToStart(step.message, true)
        if (step.isValidationRequired()) {
            input.initNewStepActionValues(step as BaseUserActionStep)
        }
    }

    override fun displayUserInputMessage(message: Message) {
        mainAdapter.addToStart(message, true)
    }

    override fun setBotTypingStatus(isTyping: Boolean) {
        if (isTyping) {
            typingText.animateText(getString(R.string.typing))
        } else {
            typingText.animateText("")
        }
    }

    override fun showError(errorMessage: ErrorMessage) {
        mainAdapter.addToStart(errorMessage, true)
        if (errorMessage.isErrorShouldFinish()) {
            input.blockInput()
        } else {
            input.errorTryAgain();
        }
    }

    override fun restartBot() {
        mainPresenter.initAdapter(this)
    }

}
