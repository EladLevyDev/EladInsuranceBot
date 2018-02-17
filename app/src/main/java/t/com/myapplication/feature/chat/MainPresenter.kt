package t.com.myapplication.feature.chat

import android.content.Context
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.messages.MessagesListAdapter
import t.com.myapplication.R
import t.com.myapplication.base.BasePresenter
import t.com.myapplication.data.model.State
import t.com.myapplication.data.model.message.Message
import t.com.myapplication.data.model.step.StepProvider
import t.com.myapplication.data.model.step.base.BaseStep
import t.com.myapplication.data.model.step.user.BaseUserActionStep
import t.com.myapplication.feature.chat.view.MessageInput
import t.com.myapplication.injection.ConfigPersistent
import t.com.myapplication.util.rx.scheduler.SchedulerUtils
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by eladlevy on 13/02/2018.
 */
@ConfigPersistent
class MainPresenter @Inject
constructor(private val currentState: State, private val stepProvider: StepProvider) : BasePresenter<MainView>(), MessageInput.InputListener {

    fun initAdapter(context: Context) {
        checkViewAttached()
        baseView?.apply {
            val imageLoader = ImageLoader { imageView, url -> Picasso.with(context).load(url).into(imageView) }
            val holdersConfig = MessageHolders()
                    .setIncomingTextLayout(R.layout.item_custom_incoming_text_message)
                    .setOutcomingTextLayout(R.layout.item_custom_outcoming_text_message)
            val messageAdapter = MessagesListAdapter<Message>(currentState.userId, holdersConfig, imageLoader)
            setAdapter(messageAdapter)
            getFirstStep()
        }
    }

    private fun getFirstStep() {
        getNextStep(0, 0)
    }


    private fun getNextStep(stepIndex: Int, delay: Long) {
        checkViewAttached()
        baseView?.setBotTypingStatus(true)
        if (stepProvider.hasMoreSteps()) {
            stepProvider.getNextStep(stepIndex)
                    .delay(delay, TimeUnit.SECONDS)
                    .compose(SchedulerUtils.ioToMain<BaseStep>())
                    .subscribe({ newStep ->
                        currentState.currentStep = newStep
                        baseView?.apply {
                            showNewStepData(newStep)
                            when (currentState.isStepRequiredNoAction()) {
                                true -> onStepFinished()
                                false -> setBotTypingStatus(false)
                            }
                        }
                    })
        } else {
            // This was created for test purpose only! if we got here, we have no more steps and user press restart
            stepProvider.restart()
            baseView?.restartBot();
        }
    }

    private fun onStepFinished() {
        currentState.currentStepIndex++
        getNextStep(currentState.currentStepIndex, 2)
    }

    override fun onUserActionSubmit(input: Any?): Boolean {
        when (input) {
            is String -> AddUserMessageToList(input.toString())
        }
        val userActionStep: BaseUserActionStep = currentState.currentStep as BaseUserActionStep
        if (!userActionStep.isInputValid(input)) {
            baseView?.setBotTypingStatus(true)
            stepProvider.fakeDelayForError()
                    .delay(2, TimeUnit.SECONDS).compose(SchedulerUtils.ioToMain<String>())
                    .subscribe({ _ ->
                        baseView?.apply {
                            setBotTypingStatus(false)
                            showError(userActionStep.generateError())
                        }

                    })
            return true
        }
        currentState.handleActionSubmited(userActionStep, input)
        stepProvider.addMoreSteps(userActionStep)
        onStepFinished()
        return true
    }

    private fun AddUserMessageToList(input: CharSequence?) {
        val calendar = Calendar.getInstance()
        val userMessage = Message(currentState.userId, currentState.currentUser, input.toString())
        userMessage.setCreatedAt(calendar.time)
        baseView?.displayUserInputMessage(userMessage)
    }
}