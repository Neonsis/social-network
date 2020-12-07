export const parseDate = (date: Date): string => {
    if (new Date().toLocaleDateString() === date.toLocaleDateString()) {
        // Today
        const time = date.toLocaleTimeString("ru-RU", {
            hour: "2-digit",
            minute: "2-digit"
        });
        return "сегодня в " + time;
    } else {
        return date.toLocaleTimeString("ru-RU", {
            day: "2-digit",
            month: "short",
            hour: "2-digit",
            minute: "2-digit"
        });
    }
}