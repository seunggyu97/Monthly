package com.example.monthly

class Constant {
    companion object {
        // 알림 아이디 선언
        const val NOTIFICATION_ID = 0
        const val CHANNEL_ID = "notification_channel"
    }

    data class Expenditure (
        val day: String,
        val dayOfWeek: String,
        val totalValue: String
            )

}