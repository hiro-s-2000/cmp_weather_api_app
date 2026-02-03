package solo.trader.weather.app.project

import kotlinx.datetime.LocalDateTime

enum class SessionState {
    Live,
    Past,
    Upcoming,
    ;

    companion object {
        fun from(startsAt: LocalDateTime, endsAt: LocalDateTime, now: LocalDateTime): SessionState = when {
            now in startsAt..<endsAt -> Live
            endsAt <= now -> Past
            else -> Upcoming
        }
    }
}
