package guilhe.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTests {

    private SharedPreferences mPrefs;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        mPrefs = appContext.getSharedPreferences("utils_test", Context.MODE_PRIVATE);
        mPrefs.edit().clear().apply();
    }

    @Test
    public void putObjectWithClass() throws Exception {
        SharedPrefsUtils.putObject(mPrefs, "key", 1);

        int a = 1;
        int b = SharedPrefsUtils.getObject(mPrefs, "key", int.class, 1);
        assertEquals(a, b);

        a = 2;
        b = SharedPrefsUtils.getObject(mPrefs, "KEY", int.class, 2);
        assertEquals(a, b);
    }

    @Test
    public void putObjectWithType() throws Exception {
        SharedPrefsUtils.putObject(mPrefs, "key", 1);
        TypeToken<Integer> type = new TypeToken<Integer>() {
        };

        int b = SharedPrefsUtils.getObject(mPrefs, "key", type, 0);
        assertEquals(1, b);

        b = SharedPrefsUtils.getObject(mPrefs, "KEY", type, 2);
        assertEquals(2, b);

        b = SharedPrefsUtils.getObject(mPrefs, "KEY", type, 2);
        assertNotEquals(1, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putObjectException0() throws Exception {
        SharedPrefsUtils.putObject(null, null, int.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putObjectException1() throws Exception {
        SharedPrefsUtils.putObject(null, null, new TypeToken<Integer>() {
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void putObjectException2() throws Exception {
        SharedPrefsUtils.putObject(null, "", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putObjectException3() throws Exception {
        SharedPrefsUtils.putObject(mPrefs, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putObjectException4() throws Exception {
        SharedPrefsUtils.putObject(null, "", int.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putObjectException5() throws Exception {
        SharedPrefsUtils.putObject(mPrefs, "", int.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putObjectException6() throws Exception {
        SharedPrefsUtils.putObject(mPrefs, null, int.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void putObjectException7() throws Exception {
        SharedPrefsUtils.putObject(null, "key", int.class);
    }

    @Test
    public void getObjectWithClass() throws Exception {
        assertNotNull(SharedPrefsUtils.getObject(null, null, int.class, 1));
        assertNotNull(SharedPrefsUtils.getObject(null, "", int.class, 1));
        assertNotNull(SharedPrefsUtils.getObject(null, "key", int.class, 1));
        assertNotNull(SharedPrefsUtils.getObject(mPrefs, "key", int.class, 1));

        int res = 1;
        SharedPrefsUtils.putObject(mPrefs, "key", res);
        int val = SharedPrefsUtils.getObject(mPrefs, "key", int.class, 2);
        assertEquals(res, val);
    }

    @Test
    public void getObjectWithType() throws Exception {
        List<MyObjectType> list = new ArrayList<>();
        list.add(new MyObjectType("string", 1, true));
        SharedPrefsUtils.putObject(mPrefs, "key", list);

        assertEquals(list, SharedPrefsUtils.getObject(mPrefs, "key", new TypeToken<List<MyObjectType>>() {
        }, new ArrayList<MyObjectType>()));
        assertNotEquals(list, SharedPrefsUtils.getObject(mPrefs, "key", List.class, new ArrayList<MyObjectType>()));
    }

    @Test
    public void getObjectWithType2() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        SharedPrefsUtils.putObject(mPrefs, "key", list);

        assertEquals(list, SharedPrefsUtils.getObject(mPrefs, "key", new TypeToken<List<Integer>>(){}, new ArrayList<Integer>()));
        assertNotEquals(list, SharedPrefsUtils.getObject(mPrefs, "key", List.class, new ArrayList<Integer>()));
    }

    @Test(expected = JsonParseException.class)
    public void getObjectWithClassException() throws Exception {
        SharedPrefsUtils.putObject(mPrefs, "key", 1);
        SharedPrefsUtils.getObject(mPrefs, "key", boolean.class, false);
    }

    @Test
    public void getObjectWithTypeException() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        SharedPrefsUtils.putObject(mPrefs, "key", list);

        exception.expect(JsonParseException.class);
        SharedPrefsUtils.getObject(mPrefs, "key", float.class, 1f);
    }

    private static class MyObjectType implements Parcelable {
        private String mFieldString;
        private int mFieldInt;
        private boolean mFieldBoolean;

        public MyObjectType(String fieldString, int fieldInt, boolean fieldBoolean) {
            mFieldString = fieldString;
            mFieldInt = fieldInt;
            mFieldBoolean = fieldBoolean;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MyObjectType that = (MyObjectType) o;

            if (mFieldInt != that.mFieldInt) return false;
            if (mFieldBoolean != that.mFieldBoolean) return false;
            return mFieldString != null ? mFieldString.equals(that.mFieldString) : that.mFieldString == null;

        }

        @Override
        public int hashCode() {
            int result = mFieldString != null ? mFieldString.hashCode() : 0;
            result = 31 * result + mFieldInt;
            result = 31 * result + (mFieldBoolean ? 1 : 0);
            return result;
        }

        public MyObjectType() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mFieldString);
            dest.writeInt(this.mFieldInt);
            dest.writeByte(this.mFieldBoolean ? (byte) 1 : (byte) 0);
        }

        protected MyObjectType(Parcel in) {
            this.mFieldString = in.readString();
            this.mFieldInt = in.readInt();
            this.mFieldBoolean = in.readByte() != 0;
        }

        public static final Creator<MyObjectType> CREATOR = new Creator<MyObjectType>() {
            @Override
            public MyObjectType createFromParcel(Parcel source) {
                return new MyObjectType(source);
            }

            @Override
            public MyObjectType[] newArray(int size) {
                return new MyObjectType[size];
            }
        };
    }
}