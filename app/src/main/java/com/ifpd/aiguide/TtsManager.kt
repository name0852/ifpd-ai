package com.ifpd.aiguide

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.Locale

/**
 * Android 내장 TTS 엔진 래퍼
 * ─ 인터넷 없이 한국어 음성 출력
 * ─ 전자칠판 내장 스피커로 바로 재생
 */
class TtsManager(context: Context) {

    private var tts: TextToSpeech? = null
    private var isReady = false
    private var pendingText: String? = null

    var onSpeakStart: (() -> Unit)? = null
    var onSpeakDone: (() -> Unit)? = null

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.KOREAN
                tts?.setSpeechRate(0.95f)   // 약간 느리게 - 안내 방송 느낌
                tts?.setPitch(1.0f)
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) { onSpeakStart?.invoke() }
                    override fun onDone(utteranceId: String?) { onSpeakDone?.invoke() }
                    override fun onError(utteranceId: String?) { onSpeakDone?.invoke() }
                })
                isReady = true
                // 대기 중이던 텍스트가 있으면 바로 재생
                pendingText?.let { speak(it) }
                pendingText = null
            }
        }
    }

    fun speak(text: String) {
        if (!isReady) {
            pendingText = text  // 준비 완료 후 재생
            return
        }
        tts?.stop()
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "IFPD_${System.currentTimeMillis()}")
    }

    fun stop() {
        tts?.stop()
    }

    fun destroy() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
