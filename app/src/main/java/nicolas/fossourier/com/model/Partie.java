package nicolas.fossourier.com.model;

public class Partie {
    private String mFirstname;
    private int mScore;
    private int mRang;

    public Partie() {
    }

    public static int size() {
        return 0;
    }


    public String getFirstname() {
        return mFirstname;
    }

    public int getScore() {
        return mScore;
    }

    public int getRang() {
        return mRang;
    }

    public void setFirstname(String firstname) {
        mFirstname=firstname;
    }

    public void setRang(int rang) {
        mRang=rang;
    }

    public void setScore(int score) {
        mScore=score;
    }

    @Override
    public String toString() {
        return "Partie{" +
                "mFirstname='" + mFirstname + '\'' +
                '}';
    }

    /**
     * @param firstname
     * @param score
     */
    public Partie(String firstname, int score) {
        this.mFirstname=firstname;
        this.mScore=score;
    }


}