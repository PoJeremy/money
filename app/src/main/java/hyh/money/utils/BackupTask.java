package hyh.money.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by HuangYH on 2015/6/7.
 */
public class BackupTask extends AsyncTask<String, Void, Integer> {

    private static final String COMMAND_BACKUP = "backupDatabase";

    public static final String COMMAND_RESTORE = "restroeDatabase";

    private Context mContext;

    public BackupTask(Context context) {

        this.mContext = context;

    }

    @Override

    protected Integer doInBackground(String... params) {

        // TODO Auto-generated method stub

//        File dbFile = mContext.getDatabasePath(Environment.getExternalStorageDirectory().getAbsolutePath()
//                + "/data/data/hyh.money/databases/money.db");
        File dbFile = mContext.getDatabasePath("/data/data/hyh.money/databases/money.db");

        File exportDir = new File(Environment.getExternalStorageDirectory(), "money");

        if (!exportDir.exists()) {

            exportDir.mkdirs();

        }

        File backup = new File(exportDir, dbFile.getName());

        String command = params[0];

        if (command.equals(COMMAND_BACKUP)) {

            try {

                backup.createNewFile();

                fileCopy(dbFile, backup);

                return Log.d("backup", "备份ok");

            } catch (Exception e) {

                // TODO: handle exception

                e.printStackTrace();

                return Log.d("backup", "备份fail");

            }

        } else if (command.equals(COMMAND_RESTORE)) {

            try {

                fileCopy(backup, dbFile);

                return Log.d("restore", "恢复success");

            } catch (Exception e) {

                // TODO: handle exception

                e.printStackTrace();

                return Log.d("restore", "恢复fail");

            }

        } else {

            return null;

        }

    }


    private void fileCopy(File dbFile, File backup) throws IOException {

        // TODO Auto-generated method stub

        FileChannel inChannel = new FileInputStream(dbFile).getChannel();

        FileChannel outChannel = new FileOutputStream(backup).getChannel();

        try {

            inChannel.transferTo(0, inChannel.size(), outChannel);

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        } finally {

            if (inChannel != null) {

                inChannel.close();

            }

            if (outChannel != null) {

                outChannel.close();

            }

        }

    }
}
