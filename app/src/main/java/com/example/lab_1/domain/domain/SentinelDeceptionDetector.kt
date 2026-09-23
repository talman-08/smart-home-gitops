package com.example.lab_1.domain

data class DetectionResult(
    val isAttack: Boolean,
    val confidence: Int
)

class SentinelDeceptionDetector {

    private val dangerPatterns = listOf(
        Regex("\\bcritical\\b", RegexOption.IGNORE_CASE),
        Regex("\\bimminent\\b", RegexOption.IGNORE_CASE),
        Regex("\\bemergency\\b", RegexOption.IGNORE_CASE),
        Regex("\\bblowout\\b", RegexOption.IGNORE_CASE),
        Regex("\\bfailure\\b", RegexOption.IGNORE_CASE),
        Regex("\\bcrack\\b", RegexOption.IGNORE_CASE),
        Regex("\\bfreezing\\b", RegexOption.IGNORE_CASE)
    )

    private val manipulationPatterns = listOf(
        Regex("\\bdo not lower\\b", RegexOption.IGNORE_CASE),
        Regex("\\bmust not lower\\b", RegexOption.IGNORE_CASE),
        Regex("\\burgent\\b", RegexOption.IGNORE_CASE)
    )

    fun analyze(text: String): DetectionResult {

        var score = 0

        dangerPatterns.forEach { pattern ->
            if (pattern.containsMatchIn(text)) {
                score += 15
            }
        }

        manipulationPatterns.forEach { pattern ->
            if (pattern.containsMatchIn(text)) {
                score += 20
            }
        }

        if (text.contains("LuxAgent", ignoreCase = true)) {
            score += 25
        }

        score = score.coerceIn(0, 100)

        return DetectionResult(
            isAttack = score >= 50,
            confidence = score
        )
    }
}