package com.quizmatch.app.data.model.firebase

import com.google.firebase.Timestamp

data class Match(var createdAt: com.google.firebase.Timestamp = Timestamp.now(), var matchId: String = "",
                 var matchName: String = "", var matchStatus:String = "", var player:MutableList<String> = mutableListOf<String>()
) {

}
