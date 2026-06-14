# 전자칠판 AI 안내 시스템
## IFPD 8600LCTQ 전용 오프라인 AI 음성 안내 앱

---

## ✅ 이 앱이 하는 일

| 기능 | 설명 |
|------|------|
| **전원 켜면 자동 실행** | 전자칠판 부팅 시 앱이 자동으로 시작됩니다 |
| **오프라인 완전 작동** | 인터넷 없이 매뉴얼 데이터만으로 동작 |
| **데이터 영구 보존** | 전원이 꺼져도 데이터 절대 삭제 안 됨 |
| **터치 → 음성 안내** | 항목 터치 시 내장 스피커로 음성 출력 |
| **키워드 검색** | 검색창에 입력하면 실시간으로 답변 탐색 |
| **절전 모드 경고** | 5분 미조작 시 음성으로 안내 |

---

## 📁 파일 구조

```
IFPDAIGuide/
├── app/src/main/
│   ├── AndroidManifest.xml          ← 권한, 자동실행 설정
│   ├── java/com/ifpd/aiguide/
│   │   ├── MainActivity.kt          ← UI + 터치 처리
│   │   ├── KnowledgeBase.kt         ← AI 지식 베이스 (매뉴얼 데이터)
│   │   ├── TtsManager.kt            ← 음성 출력 엔진
│   │   └── BootReceiver.kt          ← 전원 켜짐 자동 실행
│   └── res/layout/
│       └── activity_main.xml        ← 화면 레이아웃
└── app/build.gradle                 ← 빌드 설정
```

---

## 🚀 설치 방법

### 방법 1: Android Studio 사용 (개발자)
1. Android Studio에서 프로젝트 열기
2. 전자칠판을 USB로 PC에 연결
3. 전자칠판 설정 → 개발자 옵션 → USB 디버깅 켜기
4. Android Studio에서 ▶ Run 버튼 클릭
5. 설치 완료 → 전원 끄고 다시 켜면 자동 실행 확인

### 방법 2: APK 파일 설치 (간단)
1. Android Studio → Build → Generate Signed APK
2. APK 파일을 USB로 전자칠판에 복사
3. 전자칠판 파일 관리자에서 APK 실행
4. "알 수 없는 앱 설치 허용" 후 설치

---

## 📝 데이터 추가하는 방법

`KnowledgeBase.kt` 파일을 열고 `items` 목록에 항목 추가:

```kotlin
QAItem(
    id = "my_new_item",
    category = "카테고리명",          // 전원, 화이트보드, 연결, 관리, 오류
    question = "질문 제목",
    answer = "화면에 표시될 텍스트",
    voiceAnswer = "음성으로 읽어줄 전체 문장",
    keywords = listOf("검색키워드1", "검색키워드2")
),
```

---

## 🔧 주요 설정 변경

| 설정 | 파일 | 위치 |
|------|------|------|
| 절전 경고 시간 변경 | MainActivity.kt | `SLEEP_DELAY_MS` 값 변경 |
| 음성 속도 변경 | TtsManager.kt | `setSpeechRate(0.95f)` |
| 음성 높낮이 변경 | TtsManager.kt | `setPitch(1.0f)` |
| 시작 인사말 변경 | KnowledgeBase.kt | `GREETING` 상수 |

---

## ⚙️ 동작 원리 (AI 설명)

```
[전원 ON]
    ↓
[BootReceiver: BOOT_COMPLETED 수신]
    ↓
[MainActivity 자동 실행]
    ↓
[KnowledgeBase 로드 (코드 내장 데이터)]
    ↓
[TTS 엔진 초기화 (Android 13 내장)]
    ↓
[환영 음성 재생]
    ↓
[대기: 터치 / 검색 입력 감지]
    ↓
[KnowledgeBase.search() → 키워드 매칭]
    ↓
[화면 표시 + TTS 음성 출력]
    ↓
[루프 반복]
    ↓
[전원 OFF → 안전 종료, 데이터 보존]
```

---

## 📞 개발 문의
이 코드는 Claude AI가 매뉴얼(전자_칠판.pdf)을 기반으로 자동 생성했습니다.
