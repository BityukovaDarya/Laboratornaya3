package ru.tpu.lab3;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable, Comparable {

    public String firstName;
    public String lastName;
    public String patronymic;

    public Student() {
    }

    private Student(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        patronymic = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(patronymic);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int compareTo(Object o) {
        int lastNames = this.lastName.compareTo(((Student)o).lastName);
        if (lastNames != 0) return lastNames;
        else {
            int firstNames = this.firstName.compareTo(((Student)o).firstName);
            if (firstNames != 0) return firstNames;
            else {
                return this.patronymic.compareTo(((Student)o).patronymic);
            }
        }
    }
}
