package com.ifpd.aiguide

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/**
 * 메인 화면
 * ─ 전원 켜지면 자동 실행 (BootReceiver → 이 Activity)
 * ─ 카테고리 버튼 터치 → 목록 표시 → 항목 터치 → 음성 안내
 * ─ 검색창에 입력 → 실시간 매뉴얼 검색 → 음성 안내
 * ─ 데이터는 KnowledgeBase 객체에 영구 내장 (전원 꺼져도 유지)
 */
class MainActivity : AppCompatActivity() {

    private lateinit var tts: TtsManager

    // UI 요소
    private lateinit var tvAnswer: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var tvStatus: TextView
    private lateinit var etSearch: EditText
    private lateinit var llCategories: LinearLayout
    private lateinit var llItems: LinearLayout
    private lateinit var scrollItems: ScrollView
    private lateinit var btnBack: Button
    private lateinit var ivSpeaking: View  // 음성 출력 중 표시

    // 절전 타이머 (5분 미조작 시 경고 음성)
    private val sleepHandler = Handler(Looper.getMainLooper())
    private val sleepRunnable = Runnable {
        tts.speak(KnowledgeBase.SLEEP_WARN)
    }
    private val SLEEP_DELAY_MS = 5 * 60 * 1000L  // 5분

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 전체 화면 + 화면 항상 켜짐 (전자칠판 안내 시스템)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)
        bindViews()

        // TTS 초기화
        tts = TtsManager(this)
        tts.onSpeakStart = { runOnUiThread { ivSpeaking.visibility = View.VISIBLE } }
        tts.onSpeakDone  = { runOnUiThread { ivSpeaking.visibility = View.GONE } }

        // 카테고리 버튼 생성
        buildCategoryButtons()

        // 검색창 실시간 검색
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                if (query.length >= 2) {
                    val result = KnowledgeBase.search(query)
                    if (result != null) showAnswer(result)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {}
        })

        // 뒤로가기 버튼
        btnBack.setOnClickListener { showCategories() }

        // 시작 인사 (전원 켜지면 자동 실행)
        Handler(Looper.getMainLooper()).postDelayed({
            tts.speak(KnowledgeBase.GREETING)
        }, 1000)

        resetSleepTimer()
    }

    // ── View 참조 ──────────────────────────────────
    private fun bindViews() {
        tvAnswer     = findViewById(R.id.tv_answer)
        tvQuestion   = findViewById(R.id.tv_question)
        tvStatus     = findViewById(R.id.tv_status)
        etSearch     = findViewById(R.id.et_search)
        llCategories = findViewById(R.id.ll_categories)
        llItems      = findViewById(R.id.ll_items)
        scrollItems  = findViewById(R.id.scroll_items)
        btnBack      = findViewById(R.id.btn_back)
        ivSpeaking   = findViewById(R.id.iv_speaking)
    }

    // ── 카테고리 버튼 동적 생성 ─────────────────────
    private fun buildCategoryButtons() {
        llCategories.removeAllViews()
        KnowledgeBase.categories.forEach { category ->
            val btn = Button(this).apply {
                text = category
                textSize = 18f
                setPadding(40, 30, 40, 30)
                setOnClickListener {
                    resetSleepTimer()
                    showCategoryItems(category)
                }
            }
            llCategories.addView(btn)
        }
        showCategories()
    }

    // ── 카테고리 화면 표시 ──────────────────────────
    private fun showCategories() {
        llCategories.visibility  = View.VISIBLE
        scrollItems.visibility   = View.GONE
        btnBack.visibility       = View.GONE
        tvQuestion.text          = "궁금한 기능을 선택하거나 검색해 주세요"
        tvAnswer.text            = ""
        tvStatus.text            = "전원이 켜진 상태에서만 작동합니다 | 데이터: 매뉴얼 기반"
    }

    // ── 카테고리 내 항목 목록 표시 ──────────────────
    private fun showCategoryItems(category: String) {
        llCategories.visibility = View.GONE
        scrollItems.visibility  = View.VISIBLE
        btnBack.visibility      = View.VISIBLE
        tvQuestion.text         = "[$category] 항목을 선택하세요"
        tvAnswer.text           = ""

        llItems.removeAllViews()
        KnowledgeBase.byCategory(category).forEach { item ->
            val btn = Button(this).apply {
                text = item.question
                textSize = 16f
                setPadding(30, 24, 30, 24)
                setOnClickListener {
                    resetSleepTimer()
                    showAnswer(item)
                }
            }
            llItems.addView(btn)
        }
    }

    // ── 답변 표시 + 음성 출력 ────────────────────────
    private fun showAnswer(item: KnowledgeBase.QAItem) {
        tvQuestion.text = "Q. ${item.question}"
        tvAnswer.text   = item.answer
        tvStatus.text   = "카테고리: ${item.category}"
        tts.speak(item.voiceAnswer)
        resetSleepTimer()
    }

    // ── 절전 타이머 리셋 ─────────────────────────────
    private fun resetSleepTimer() {
        sleepHandler.removeCallbacks(sleepRunnable)
        sleepHandler.postDelayed(sleepRunnable, SLEEP_DELAY_MS)
    }

    // ── 터치 이벤트로 타이머 리셋 ───────────────────
    override fun onUserInteraction() {
        super.onUserInteraction()
        resetSleepTimer()
    }

    override fun onDestroy() {
        sleepHandler.removeCallbacksAndMessages(null)
        tts.destroy()
        super.onDestroy()
    }
}
