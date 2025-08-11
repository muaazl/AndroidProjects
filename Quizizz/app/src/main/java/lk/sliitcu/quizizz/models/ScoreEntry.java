package lk.sliitcu.quizizz.models;

public class ScoreEntry {
    private final int attempt;
    private final int score;
    private final long timestamp;

    public ScoreEntry(int attempt, int score, long timestamp) {
        this.attempt = attempt;
        this.score = score;
        this.timestamp = timestamp;
    }

    public int getAttempt() {
        return attempt;
    }

    public int getScore() {
        return score;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
