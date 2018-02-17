/*******************************************************************************
 * Copyright 2016 stfalcon.com
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package t.com.myapplication.feature.chat.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.view.ViewCompat
import android.support.v4.widget.Space
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import t.com.myapplication.R
import t.com.myapplication.data.model.step.user.BaseSelectionActionStep
import t.com.myapplication.data.model.step.user.BaseUserActionStep
import t.com.myapplication.util.hideKeyboard
import java.util.*
import kotlin.concurrent.timerTask

class MessageInput : RelativeLayout, TextWatcher {

    val STEP_POSITIVE = true
    val STEP_NEGATIVE = false

    @BindView(R.id.revealItems)
    lateinit var revealView: LinearLayout
    @BindView(R.id.messageInput)
    lateinit var messageInput: EditText
    @BindView(R.id.messageSendButton)
    lateinit var messageSendButton: ImageButton
    @BindView(R.id.btnNegative)
    lateinit var messageNegativeButton: Button
    @BindView(R.id.btnPositive)
    lateinit var messagePositiveButton: Button

    @BindView(R.id.sendButtonSpace)
    lateinit var sendButtonSpace: Space
    private var input: CharSequence? = null
    private var inputListener: InputListener? = null
    private var stepRequiredAction: Boolean = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    /**
     * Sets callback for 'submit' button.
     *
     * @param inputListener input callback
     */
    fun setInputListener(inputListener: InputListener) {
        this.inputListener = inputListener
    }

    @OnClick(R.id.messageSendButton)
    internal fun onSendButtonClicked() {
        val isSubmitted = onSubmit()
        if (isSubmitted) {
            messageInput.setText("")
        }
    }

    @OnClick(R.id.btnPositive)
    internal fun onPositiveButtonClicked() {
        submitResults(STEP_POSITIVE)
    }

    @OnClick(R.id.btnNegative)
    internal fun onNegativeButtonClicked() {
        submitResults(STEP_NEGATIVE)
    }

    private fun submitResults(value: Boolean) {
        hideAnimation()
        inputListener!!.onUserActionSubmit(value)
        stepRequiredAction = false;
    }

    /**
     * This method is called to notify you that, within s,
     * the count characters beginning at start have just replaced old text that had length before
     */
    override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        input = s
        messageSendButton.isEnabled = input!!.length > 1 && stepRequiredAction
    }

    /**
     * This method is called to notify you that, within s,
     * the count characters beginning at start are about to be replaced by new text with length after.
     */
    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    /**
     * This method is called to notify you that, somewhere within s, the text has been changed.
     */
    override fun afterTextChanged(editable: Editable) {

    }

    private fun onSubmit(): Boolean {
        stepRequiredAction = false;
        return inputListener!!.onUserActionSubmit(input.toString().trim())
    }

    private fun init(context: Context, attrs: AttributeSet) {
        init(context)
        val style = MessageInputStyle.parse(context, attrs)

        this.messageInput.maxLines = style.inputMaxLines
        this.messageInput.hint = style.inputHint
        this.messageInput.setText(style.inputText)
        this.messageInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.inputTextSize.toFloat())
        this.messageInput.setTextColor(style.inputTextColor)
        this.messageInput.setHintTextColor(style.inputHintColor)
        ViewCompat.setBackground(this.messageInput, style.inputBackground)
        setCursor(style.inputCursorDrawable)

        this.messageSendButton.setImageDrawable(style.inputButtonIcon)
        this.messageSendButton.layoutParams.width = style.inputButtonWidth
        this.messageSendButton.layoutParams.height = style.inputButtonHeight
        ViewCompat.setBackground(messageSendButton, style.inputButtonBackground)
        this.sendButtonSpace.layoutParams.width = style.inputButtonMargin

        if (paddingLeft == 0
                && paddingRight == 0
                && paddingTop == 0
                && paddingBottom == 0) {
            setPadding(
                    style.inputDefaultPaddingLeft,
                    style.inputDefaultPaddingTop,
                    style.inputDefaultPaddingRight,
                    style.inputDefaultPaddingBottom
            )
        }
    }

    private fun init(context: Context) {
        View.inflate(context, R.layout.view_message_input, this)
        ButterKnife.bind(this)
        messageInput.addTextChangedListener(this)
        messageInput.setText("")
    }

    fun initNewStepActionValues(userActionStep: BaseUserActionStep) {

        when (userActionStep) {
            is BaseSelectionActionStep -> {
                messagePositiveButton.text = userActionStep.btnPositiveText
                messageNegativeButton.text = userActionStep.btnNegativeText
                startAnimation()
            }
        }

        stepRequiredAction = true
        messageSendButton.isEnabled = input!!.length > 2

    }

    fun startAnimation() {

      //  hideKeyboard()
        val animator = getAnimation(true)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 500
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                revealView.visibility = View.VISIBLE
                hideKeyboardWithDelay();
            }
        })
        animator.start()

    }

    /*
        Fixes blink when removing keyboard
     */
    fun hideKeyboardWithDelay() {
        val timer = Timer()
        timer.schedule(timerTask {
            hideKeyboard()
        }, 500)
    }


    fun hideAnimation() {
        val anim = getAnimation(false)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                revealView.visibility = View.INVISIBLE
            }
        })
        anim.start()
    }

    private fun getAnimation(show: Boolean): Animator {
        if (show) {
            val cx = revealView.left + revealView.right
            val cy = revealView.top
            val radius = Math.max(revealView.width, revealView.height)
            return android.view.ViewAnimationUtils.createCircularReveal(revealView, cx, cy, 0f, radius.toFloat())
        } else {
            val cx = revealView.left + revealView.right
            val cy = revealView.top
            val radius = Math.max(revealView.width, revealView.height)
            return android.view.ViewAnimationUtils.createCircularReveal(revealView, cx, cy, radius.toFloat(), 0f)
        }
    }

    private fun setCursor(drawable: Drawable?) {
        if (drawable == null) return

        try {
            val drawableResField = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            drawableResField.isAccessible = true

            val drawableFieldOwner: Any
            val drawableFieldClass: Class<*>
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                drawableFieldOwner = this.messageInput
                drawableFieldClass = TextView::class.java
            } else {
                val editorField = TextView::class.java.getDeclaredField("mEditor")
                editorField.isAccessible = true
                drawableFieldOwner = editorField.get(this.messageInput)
                drawableFieldClass = drawableFieldOwner.javaClass
            }
            val drawableField = drawableFieldClass.getDeclaredField("mCursorDrawable")
            drawableField.isAccessible = true
            drawableField.set(drawableFieldOwner, arrayOf(drawable, drawable))
        } catch (ignored: Exception) {
        }

    }

    /**
     * Interface definition for a callback to be invoked when user pressed 'submit' button
     */
    interface InputListener {

        /**
         * Fires when user presses 'send' button.
         *
         * @param input input entered by user
         * @return if input text is valid, you must return `true` and input will be cleared, otherwise return false.
         */
        fun onUserActionSubmit(input: Any?): Boolean

    }

    fun blockInput() {
        stepRequiredAction = false
    }

    fun errorTryAgain() {
        stepRequiredAction = true
    }

}
