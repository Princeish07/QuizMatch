package com.quizmatch.app.data.model.firebase

import com.google.firebase.Timestamp

data class Score(var createdAt: com.google.firebase.Timestamp = Timestamp.now(), var score: Int = 0, var scoreId:String = "", var updatedAt: com.google.firebase.Timestamp = Timestamp.now()
) {

}
