public class HigherScore {
    private String[] names;
    private int[] scores;

    public HigherScore(String[] names, int[] scores) {
        this.scores = scores;
        this.names = names;
    }

    public boolean newHigherScore(int score) {
        if (score > this.scores[4]) {
            return true;
        }
        return false;
    }

    public String[] getNames() {
        return this.names;
    }

    public int[] getScores() {
        return this.scores;
    }

    public void newScore(String name, int score) {
        if (score > this.scores[4]) {
            this.names[4] = name;
            this.scores[4] = score;
            if (score > this.scores[3]) {
                this.names[4] = this.names[3];
                this.scores[4] = this.scores[3];
                this.names[3] = name;
                this.scores[3] = score;
                if (score > this.scores[2]) {
                    this.names[3] = this.names[2];
                    this.scores[3] = this.scores[2];
                    this.names[2] = name;
                    this.scores[2] = score;
                    if (score > this.scores[1]) {
                        this.names[2] = this.names[1];
                        this.scores[2] = this.scores[1];
                        this.names[1] = name;
                        this.scores[1] = score;
                        if (score > this.scores[0]) {
                            this.names[1] = this.names[0];
                            this.scores[1] = this.scores[0];
                            this.names[0] = name;
                            this.scores[0] = score;
                        }
                    }
                }
            }
        }
    }
}