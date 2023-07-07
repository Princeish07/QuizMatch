package com.quizmatch.app.data.model.firebase

data class User(var email: String, var first_name: String,
                var last_name: String, var user_id:String, var match_status:
String, var opponent_id:String){
    fun getFullName():String{
        return "${first_name.capitalize()}"
    }
}
