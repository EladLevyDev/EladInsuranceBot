package t.com.myapplication.data.model.step

import io.reactivex.Single
import t.com.myapplication.data.model.State
import t.com.myapplication.data.model.message.MessagesMockData
import t.com.myapplication.data.model.step.base.BaseStep
import t.com.myapplication.data.model.step.bot.BaseBotStep
import t.com.myapplication.data.model.step.bot.BotFormattedTextStep
import t.com.myapplication.data.model.step.user.*
import t.com.myapplication.injection.ConfigPersistent
import java.util.*
import javax.inject.Inject

/**
 * Created by eladlevy on 16/02/2018.
 */

@ConfigPersistent
class StepProvider @Inject
constructor(private val currentState: State, private val messageProvider: MessagesMockData) {

    val steps: ArrayList<BaseStep> = ArrayList<BaseStep>()

    init {
        initStepList()
    }

    private fun initStepList() {
        steps.clear()
        steps.add(BaseBotStep(messageProvider.getMessageObject(0)))
        steps.add(FirstNameActionStep(messageProvider.getMessageObject(1), messageProvider.getErrorObject(MessagesMockData.MockData.Errors.FIRST_NAME,
                false)))
    }

    /*
        This should arrived from server, for now. a simple mock.
     */
    fun addMoreSteps(userActionStep: BaseUserActionStep) {
        when (userActionStep) {
            is FirstNameActionStep -> {
                steps.add(BotFormattedTextStep(messageProvider.getMessageObject(2), currentState.currentUser.name))
                steps.add((PhoneActionStep(messageProvider.getMessageObject(3), messageProvider.getErrorObject(MessagesMockData.MockData.Errors.PHONE, false))))
            }
            is PhoneActionStep -> steps.add(TermsActionStep(messageProvider.getMessageObject(4),
                    messageProvider.getErrorObject(MessagesMockData.MockData.Errors.SELECTION_FINISH_ERROR, true), MessagesMockData.MockData.Selection.TERMS_POSITIVE_BUTTON,
                    MessagesMockData.MockData.Selection.TERMS_NEGATIVE_BUTTON))
            is TermsActionStep -> {
                steps.add(BaseBotStep(messageProvider.getMessageObject(5)))
                steps.add(RestartBotActionStep(messageProvider.getMessageObject(6),
                        messageProvider.getErrorObject(MessagesMockData.MockData.Errors.SELECTION_FINISH_ERROR, true), MessagesMockData.MockData.Selection.FINISH_POSITIVE_BUTTON,
                        MessagesMockData.MockData.Selection.FINISH_NEGATIVE_BUTTON))
            }

        }
    }

    fun getNextStep(stepIndex: Int): Single<BaseStep> {
        return Single.create { emitter ->
            emitter.onSuccess(steps[stepIndex])
        }
    }


    fun fakeDelayForError(): Single<String> {
        return Single.create { emitter ->
            emitter.onSuccess("")
        }
    }

    fun hasMoreSteps(): Boolean {
        return steps.size > currentState.currentStepIndex
    }

    fun restart() {
        initStepList()
        currentState.initNewState()
    }

}

