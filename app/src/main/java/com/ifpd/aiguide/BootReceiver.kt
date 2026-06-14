package com.ifpd.aiguide

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * 전원이 켜지면 자동으로 MainActivity를 실행합니다.
 * AndroidManifest에 RECEIVE_BOOT_COMPLETED 권한이 있어야 동작합니다.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == Intent.ACTION_BOOT_COMPLETED ||
            action == "android.intent.action.QUICKBOOT_POWERON"
        ) {
            val launch = Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(launch)
        }
    }
}
