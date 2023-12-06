package ru.geekbrains.hexcore;

public class Hex {
    private int s;
    private int q;
    private int r;

    public int getS() {
        return s;
    }

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    public void setS(int s) {
        this.s = s;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public void setR(int r) {
        this.r = r;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hex)
            return (getR() == ((Hex) obj).getR() &&
                    getQ() == ((Hex) obj).getQ() &&
                    getS() == ((Hex) obj).getS());
        return false;
    }

    @Override
    public int hashCode() {
        return 1000*getR() + 100*getQ() + getS();
    }
}
