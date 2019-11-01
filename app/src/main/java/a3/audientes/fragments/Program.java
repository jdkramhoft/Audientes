package a3.audientes.fragments;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Currently has dummy content.
 * Class represents a user-defined program
 */
public class Program implements Parcelable {

    private int volume;

    public Program(int volume){
        this.volume = volume;
    }

    protected Program(Parcel in) {
        volume = in.readInt();
    }

    public static final Creator<Program> CREATOR = new Creator<Program>() {
        @Override
        public Program createFromParcel(Parcel in) {
            return new Program(in);
        }

        @Override
        public Program[] newArray(int size) {
            return new Program[size];
        }
    };

    public int getVolume(){
        return this.volume;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(volume);
    }
}
