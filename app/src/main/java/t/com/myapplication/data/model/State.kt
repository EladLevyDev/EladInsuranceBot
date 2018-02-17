package t.com.myapplication.data.model

import t.com.myapplication.data.model.step.base.BaseStep
import t.com.myapplication.data.model.step.user.BaseUserActionStep
import t.com.myapplication.data.model.step.user.FirstNameActionStep
import t.com.myapplication.data.model.step.user.PhoneActionStep
import t.com.myapplication.data.model.step.user.TermsActionStep
import t.com.myapplication.injection.ConfigPersistent
import javax.inject.Inject

/**
 * Created by eladlevy on 16/02/2018.
 */

@ConfigPersistent
class State @Inject
constructor() {

    val userId = "0"
    var currentStepIndex: Int = 0
    lateinit var currentStep: BaseStep
     var currentUser: AppUser = AppUser(userId, "noName")

    fun handleActionSubmited(userActionStep: BaseUserActionStep, input: Any?) {
        when (userActionStep) {
            is FirstNameActionStep -> currentUser = AppUser(userId, input.toString())
            is PhoneActionStep -> currentUser.phoneNumber = input.toString()
            is TermsActionStep -> currentUser.termsAgree = input as Boolean?
            else -> return
        }
    }

    fun isStepRequiredNoAction(): Boolean {
        return !currentStep.isValidationRequired()
    }

    fun initNewState() {
        currentUser = AppUser(userId, "noName")
        currentStepIndex = 0
    }
}

