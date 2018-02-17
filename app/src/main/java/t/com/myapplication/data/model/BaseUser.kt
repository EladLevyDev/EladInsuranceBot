package t.com.myapplication.data.model

import com.stfalcon.chatkit.commons.models.IUser

/**
 * Created by eladlevy on 15/02/2018.
 */

open class BaseUser(private var id: String, private var name: String, private var avatar: String) : IUser {

    override fun getId(): String {
        return id
    }

    override fun getName(): String {
        return name
    }

    override fun getAvatar(): String {
        return avatar
    }
}
