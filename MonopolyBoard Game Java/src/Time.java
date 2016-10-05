public class Time implements java.io.Serializable{

    private int hour, minute, second;

    public Time() {
		setTime(0, 0, 0);
		}

    public Time (int h, int m, int s) {
        setTime(h, m, s);
    }

    public void setTime(int h, int m, int s) {
        hour = h;
        minute = m;
        second = s;
    }

    public int getHour() {
		return hour;
		}

    public int getMinute() {
		return minute;
		}

    public int getSecond() {
		return second;
		}

    public boolean equals(Time input) {
        return hour == input.getHour() &&
        minute == input.getMinute() &&
        second == input.getSecond();
    }
    public Time getDuration(Time in) {
		int inhour = in.getHour();
		int inmin  = in.getMinute();
		int insec  = in.getSecond();

		int gsec = 0, gmin = 0, ghour = 0;

		if(insec < second){
			inmin--;
			insec += 60;
		}

		gsec = insec - second;

		if(inmin < minute) {
			inhour--;
			inmin += 60;
		}

		gmin = inmin - minute;
		ghour = inhour - hour;
		return new Time(ghour, gmin, gsec);
	}

    public Time addTime(Time input) {
        int secs = (hour + input.getHour()) * 60 * 60 +
        (minute + input.getMinute()) * 60 +
        second + input.getSecond();

        int sec = secs % 60;
        int rest = secs / 60;
        int min = rest % 60;
        rest = rest / 60;
        int hr = rest % 24;

        return new Time(hr, min, sec);
    }
    public int compareTo(Time input) {
        return hour > input.getHour() ? 1 :
        hour < input.getHour() ? -1 :
        minute > input.getMinute() ? 1 :
        minute < input.getMinute() ? -1 :
        second > input.getSecond() ?
        1 : second < input.getSecond() ? -1 : 0;
    }

    public String toString() {
        return hour + ":" + minute + ":" + second;
    }
}
