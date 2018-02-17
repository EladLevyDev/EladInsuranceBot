package t.com.myapplication.data.model

import com.stfalcon.chatkit.commons.models.IUser

/**
 * Created by eladlevy on 15/02/2018.
 */

class AppUser(private var id: String, private var name: String) : BaseUser(id, name, "") {

     var phoneNumber: String = ""
    var termsAgree: Boolean? = false

    override fun getName(): String {
        return name
    }

    override fun getId(): String {
        return id
    }

    fun getAllParams(): String? {
        return phoneNumber + " "+ name + " "+id + " "+termsAgree
    }
}

//