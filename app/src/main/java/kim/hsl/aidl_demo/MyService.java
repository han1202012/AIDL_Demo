package kim.hsl.aidl_demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    public static final String TAG = "MyService";

    /**
     * 存储从其它进程传过来的 Student 对象
     */
    private List<Student> students;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        students = new ArrayList<>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    /**
     * 创建 IMyAidlInterface.Stub 抽象类子类对象 , 实现其中的 3 个抽象方法
     * Binder 调用 transact 方法时 , 会调用 IMyAidlInterface.Stub 的 onTransact 方法
     * 在 IMyAidlInterface.Stub.onTransact 方法中会调用下面实现的抽象方法
     */
    private IMyAidlInterface.Stub stub = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong,
                               boolean aBoolean, float aFloat, double aDouble,
                               String aString) throws RemoteException {
            Log.i(TAG, "anInt=" + anInt + " , aLong=" + aLong +
                    " , aBoolean=" + aBoolean + " , aFloat=" + aFloat +
                    " , aDouble=" + aDouble + " , aString=" + aString);
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            if (students != null) {
                students.add(student);
            }
        }

        @Override
        public List<Student> getStudents() throws RemoteException {
            return students;
        }
    };
}