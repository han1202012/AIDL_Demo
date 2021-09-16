package kim.hsl.aidl_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private IMyAidlInterface aidl;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        /**
         * 传入需要的 Service , 让系统寻找指定的远程服务
         * @param name
         * @param service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 通过 IBinder 对象 , 从系统中获取对应的远程服务或代理对象
            aidl = IMyAidlInterface.Stub.asInterface(service);
            Log.i(TAG, "AIDL 获取成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            aidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 通过 Action 和 包名 , 绑定远程服务
        Intent intent = new Intent("android.intent.action.MyService");
        intent.setPackage("kim.hsl.aidl_demo");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        findViewById(R.id.add).setOnClickListener((View view)->{
            try {
                aidl.addStudent(new Student("Tom"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.get).setOnClickListener((View view)->{
            try {
                List<Student> students = aidl.getStudents();
                Log.i(TAG, "students = " + students);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}