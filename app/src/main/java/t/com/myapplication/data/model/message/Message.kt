package t.com.myapplication.data.model.message

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.MessageContentType
import t.com.myapplication.data.model.BaseUser
import java.util.*

/*
 * Created by elad
 */
open class Message constructor(private var id: String, private var user: BaseUser, private var text: String?, private var createdAt: Date? = Date()) : IMessage, MessageContentType.Image, /*this is for default image messages implementation*/
        MessageContentType /*and this one is for custom content type (in this case - voice message)*/ {
    private var image: Image? = null

    override fun getId(): String {
        return id
    }

    override fun getText(): String? {
        return text
    }
    fun setText(newText: String){
        text = newText
    }

    override fun getCreatedAt(): Date? {
        return createdAt
    }

    override fun getUser(): BaseUser {
        return this.user
    }

    override fun getImageUrl(): String? {
        return image?.url
    }

    fun setCreatedAt(createdAt: Date) {
        this.createdAt = createdAt
    }

    class Image(val url: String)

}
