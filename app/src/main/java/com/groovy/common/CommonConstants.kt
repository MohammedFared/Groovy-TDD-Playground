package com.groovy.common

object CommonConstants {
    object Networking {
        private const val NGROK_FORWARDING_LINK = ""
        const val BASE_URL = "https://$NGROK_FORWARDING_LINK.ngrok-free.app/groovy/"
    }
}
