package com.ifpd.aiguide

/**
 * 전자칠판 매뉴얼 기반 지식 베이스
 * ─ 인터넷 없이 동작, 전원 꺼져도 데이터 유지 (코드에 내장)
 * ─ 키워드 매칭으로 사용자 터치에 맞는 답변을 반환
 */
object KnowledgeBase {

    data class QAItem(
        val id: String,
        val category: String,
        val question: String,
        val answer: String,       // 화면에 표시
        val voiceAnswer: String,  // TTS 음성 출력
        val keywords: List<String>
    )

    // ───────────────────────────────────────────────
    // 매뉴얼 데이터 (전원이 꺼져도 절대 사라지지 않음)
    // ───────────────────────────────────────────────
    val items: List<QAItem> = listOf(

        // ── 전원 및 기본 조작 ──
        QAItem(
            id = "power_on",
            category = "전원",
            question = "전원 켜는 방법",
            answer = "전면 하단 전원 버튼을 누르거나\nNFC 카드를 전면 단자에 대세요.\n전원 표시등이 파란색으로 바뀝니다.",
            voiceAnswer = "전면 하단의 전원 버튼을 누르거나, NFC 카드를 전면 단자에 대면 전원이 켜집니다. 전원 표시등이 파란색으로 바뀝니다.",
            keywords = listOf("전원", "켜기", "시작", "부팅", "NFC", "전원켜기")
        ),
        QAItem(
            id = "power_off",
            category = "전원",
            question = "전원 끄는 방법",
            answer = "전면 하단 전원 버튼을 길게 누르거나\n리모컨 전원 버튼을 누르세요.\n작업 내용은 자동 저장됩니다.",
            voiceAnswer = "전면 하단의 전원 버튼을 길게 누르거나 리모컨의 전원 버튼을 누르면 됩니다. 작업 중인 내용은 자동으로 저장됩니다.",
            keywords = listOf("전원 끄기", "종료", "끄기", "전원OFF")
        ),
        QAItem(
            id = "volume",
            category = "전원",
            question = "음량 조절 방법",
            answer = "전면 하단 음량 +/- 버튼을 누르거나\n사이드 메뉴 볼륨 슬라이더를 사용하세요.",
            voiceAnswer = "전면 하단의 음량 플러스, 마이너스 버튼을 누르거나, 사이드 메뉴 하단의 볼륨 슬라이더로 조절하세요.",
            keywords = listOf("음량", "볼륨", "소리", "크기", "소리조절")
        ),
        QAItem(
            id = "touch_error",
            category = "전원",
            question = "터치가 안 될 때",
            answer = "전면 하단의 Touch ON/OFF 스위치를 확인하세요.\n스위치가 꺼져 있으면 터치가 동작하지 않습니다.",
            voiceAnswer = "전면 하단의 Touch ON/OFF 스위치를 확인하세요. 스위치가 꺼져 있으면 터치가 동작하지 않습니다. 스위치를 켜면 즉시 터치가 활성화됩니다.",
            keywords = listOf("터치", "터치안됨", "터치오류", "터치불가")
        ),
        QAItem(
            id = "remote",
            category = "전원",
            question = "리모컨 배터리 교체",
            answer = "AAA 1.5V 건전지 2개 사용.\n배터리 덮개를 열고 극성에 맞게 넣으세요.\n약 1년 사용 가능합니다.",
            voiceAnswer = "리모컨은 AAA 1.5V 건전지 2개를 사용합니다. 배터리 덮개를 열고 극성에 맞게 넣으세요. 약 1년 사용 가능합니다.",
            keywords = listOf("리모컨", "리모콘", "배터리", "건전지")
        ),

        // ── 화이트보드 ──
        QAItem(
            id = "wb_start",
            category = "화이트보드",
            question = "화이트보드 실행",
            answer = "홈 화면에서 화이트보드 아이콘을 누르세요.\n또는 리모컨 홈 버튼 → 화이트보드 선택.",
            voiceAnswer = "홈 화면에서 화이트보드 아이콘을 누르면 실행됩니다. 리모컨의 홈 버튼을 누른 후 화이트보드를 선택하셔도 됩니다.",
            keywords = listOf("화이트보드", "시작", "실행", "판서", "필기")
        ),
        QAItem(
            id = "wb_save",
            category = "화이트보드",
            question = "판서 저장 방법",
            answer = "사이드 메뉴 → 저장 버튼을 누르면\n이미지 또는 PDF 형식으로 저장됩니다.",
            voiceAnswer = "사이드 메뉴에서 저장 버튼을 누르면 이미지 또는 PDF 형식으로 내부 저장소에 저장됩니다.",
            keywords = listOf("저장", "파일저장", "내보내기", "PDF", "보관")
        ),
        QAItem(
            id = "annotation",
            category = "화이트보드",
            question = "화면 위에 필기 (어노테이션)",
            answer = "리모컨의 어노테이션 버튼을 누르면\n현재 화면 위에 바로 판서할 수 있습니다.\nHDMI 화면, 동영상 위에도 사용 가능.",
            voiceAnswer = "리모컨의 어노테이션 버튼을 누르면 현재 화면 위에 바로 필기할 수 있습니다. HDMI 연결 화면이나 동영상 위에서도 사용 가능합니다.",
            keywords = listOf("어노테이션", "화면위", "오버레이", "필기", "그리기")
        ),
        QAItem(
            id = "wb_share",
            category = "화이트보드",
            question = "작성 내용 공유",
            answer = "파일 공유 앱 실행 → QR 코드 스캔으로\n외부 기기로 전송 가능.\n블루투스, 이메일로도 공유 가능.",
            voiceAnswer = "파일 공유 앱에서 QR 코드를 스캔하면 외부 기기로 파일을 전송할 수 있습니다. 블루투스 또는 이메일로도 공유 가능합니다.",
            keywords = listOf("공유", "전송", "QR", "파일공유", "보내기")
        ),

        // ── 연결 ──
        QAItem(
            id = "wifi",
            category = "연결",
            question = "Wi-Fi 연결 방법",
            answer = "설정 → 네트워크 및 인터넷 → Wi-Fi 켜기\n2.4GHz ~ 6GHz 대역 지원.",
            voiceAnswer = "설정에서 네트워크 및 인터넷을 선택하고 Wi-Fi를 켜면 주변 신호가 감지됩니다. 2.4기가헤르츠에서 6기가헤르츠 대역을 지원합니다.",
            keywords = listOf("와이파이", "WiFi", "인터넷", "무선", "네트워크")
        ),
        QAItem(
            id = "hdmi",
            category = "연결",
            question = "외부 PC·노트북 연결",
            answer = "후면 HDMI 1, 2, 3 또는 DP 포트에 케이블 연결 후\n사이드 메뉴 소스 버튼으로 전환하세요.",
            voiceAnswer = "후면의 HDMI 1, 2, 3 또는 디스플레이포트에 케이블을 연결하세요. 연결 후 사이드 메뉴 소스 버튼이나 리모컨으로 입력 소스를 전환하세요.",
            keywords = listOf("HDMI", "외부연결", "PC연결", "노트북", "화면연결")
        ),
        QAItem(
            id = "eshare",
            category = "연결",
            question = "모바일 화면 미러링",
            answer = "E-Share 앱 실행 → 같은 네트워크 연결 후\nShare Screen으로 미러링하세요.",
            voiceAnswer = "이쉐어 앱을 실행하고 외부 기기와 동일한 네트워크에 연결하세요. Share Screen으로 모바일 화면을 칠판에 띄울 수 있습니다.",
            keywords = listOf("미러링", "화면공유", "이쉐어", "모바일", "스마트폰연결")
        ),
        QAItem(
            id = "bluetooth",
            category = "연결",
            question = "블루투스 연결",
            answer = "설정 → 연결된 기기 → 블루투스 켜기\n기기 검색 후 연결. 연결 기기는 자동 저장됩니다.",
            voiceAnswer = "설정에서 연결된 기기를 선택하고 블루투스를 켠 후 연결할 기기를 검색하세요. 한 번 연결한 기기는 자동으로 다시 연결됩니다.",
            keywords = listOf("블루투스", "bluetooth", "무선연결", "페어링")
        ),

        // ── 청소 및 관리 ──
        QAItem(
            id = "clean",
            category = "관리",
            question = "화면 청소 방법",
            answer = "부드러운 마른 헝겊으로 닦으세요.\n물, 벤젠, 알코올 등 화학물질은 사용 금지.\n날카로운 물체로 긁지 마세요.",
            voiceAnswer = "부드러운 마른 헝겊으로 닦으세요. 물을 직접 뿌리거나 벤젠, 알코올 같은 화학물질은 사용하지 마세요. 날카로운 물체로 화면을 긁지 마세요.",
            keywords = listOf("청소", "닦기", "화면청소", "먼지")
        ),
        QAItem(
            id = "afterimage",
            category = "관리",
            question = "잔상이 생겼을 때",
            answer = "장시간 고정 화면 사용 시 잔상 발생 가능.\n미사용 시 전원을 끄거나 절전 모드 사용.\n시간이 지나면 자연히 사라집니다.",
            voiceAnswer = "장시간 고정 화면 사용 시 잔상이 발생할 수 있습니다. 사용하지 않을 때는 전원을 끄거나 절전 모드를 사용하세요. 잔상은 시간이 지나면 자연스럽게 사라집니다.",
            keywords = listOf("잔상", "얼룩", "화면얼룩", "고스트")
        ),
        QAItem(
            id = "ventilation",
            category = "관리",
            question = "과열 방지",
            answer = "제품 주변에 충분한 공간 확보.\n통풍구를 막지 마세요.\n과열 시 전원을 끄고 식힌 후 재사용하세요.",
            voiceAnswer = "제품 주변에 충분한 공간을 두어 통풍이 잘 되게 하세요. 통풍구를 막지 마세요. 과열 시 전원을 끄고 충분히 식힌 후 다시 사용하세요.",
            keywords = listOf("과열", "발열", "통풍", "환기", "뜨거움")
        ),

        // ── 오류 및 AS ──
        QAItem(
            id = "no_power",
            category = "오류",
            question = "전원이 켜지지 않을 때",
            answer = "전원 코드 연결 상태 확인.\n후면 전원 스위치가 I 위치인지 확인.\n문제 지속 시 서비스 센터에 문의하세요.",
            voiceAnswer = "전원 코드가 올바르게 연결되어 있는지 확인하세요. 후면의 전원 스위치가 I 위치인지 확인하세요. 문제가 지속되면 서비스 센터에 문의하세요.",
            keywords = listOf("켜지지않음", "전원안켜짐", "동작안함", "부팅실패")
        ),
        QAItem(
            id = "no_sound",
            category = "오류",
            question = "소리가 나지 않을 때",
            answer = "음소거 상태 확인 (사이드 메뉴).\n볼륨이 0으로 설정되어 있지 않은지 확인.",
            voiceAnswer = "음소거 상태인지 확인하세요. 사이드 메뉴에서 음소거 버튼을 확인하고, 볼륨이 0으로 설정되어 있지 않은지 확인하세요.",
            keywords = listOf("소리없음", "음소거", "무음", "소리안남")
        ),
        QAItem(
            id = "firmware",
            category = "오류",
            question = "펌웨어 업데이트",
            answer = "OTA Service 앱 → Network upgrade 선택.\nUSB 파일로 Local upgrade도 가능.",
            voiceAnswer = "OTA Service 앱에서 Network upgrade를 선택하면 네트워크로 업데이트됩니다. USB 파일로 Local upgrade도 가능합니다.",
            keywords = listOf("펌웨어", "업데이트", "OTA", "업그레이드")
        )
    )

    // ───────────────────────────────────────────────
    // 기본 음성 메시지
    // ───────────────────────────────────────────────
    const val GREETING   = "안녕하세요. 전자칠판 AI 안내 시스템입니다. 궁금한 기능을 터치하거나 카테고리를 선택해 주세요."
    const val NOT_FOUND  = "해당 내용을 찾을 수 없습니다. 다른 키워드로 검색하거나 카테고리를 선택해 주세요."
    const val SLEEP_WARN = "화면이 곧 절전 모드로 전환됩니다. 계속 사용하시려면 화면을 터치해 주세요."
    const val GOODBYE    = "전원을 끕니다. 이용해 주셔서 감사합니다."

    // 카테고리 목록
    val categories = listOf("전원", "화이트보드", "연결", "관리", "오류")

    /**
     * 키워드로 가장 적합한 답변을 찾습니다.
     * 일치하는 키워드가 많을수록 높은 점수를 부여합니다.
     */
    fun search(query: String): QAItem? {
        if (query.isBlank()) return null
        val q = query.trim().lowercase()
        return items
            .map { item ->
                val score = item.keywords.count { kw -> q.contains(kw.lowercase()) || kw.lowercase().contains(q) }
                Pair(item, score)
            }
            .filter { it.second > 0 }
            .maxByOrNull { it.second }
            ?.first
    }

    /** 특정 카테고리의 항목만 반환 */
    fun byCategory(category: String): List<QAItem> =
        items.filter { it.category == category }
}
