package com.quizmatch.app.data.model.firebase

data class User(var email: String = "", var first_name: String = "",
                var last_name: String = "",var user_id:String = "", var matchId:MutableList<String> = mutableListOf<String>()
):java.io.Serializable {
    fun getFullName():String{
        return "${first_name.capitalize()}"
    }
}
