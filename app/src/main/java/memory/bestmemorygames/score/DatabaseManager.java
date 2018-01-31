package memory.bestmemorygames.score;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper{
    private static final String DATABASE_NAME ="score";
    private static final int DATABASE_VERSION =2;

    public DatabaseManager(ScoreFragment context){
        super(null,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //permet de créer la base de donnée lorsque l'application est installé la premiere fois
    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "create table T_scores ("
                + " idScore integer primary key autoincrement,"
                + "name text not null,"
                + "score integer not null,"
                + "when_ integer not null"
                +")";
        db.execSQL(strSql);
        Log.i("DATABASE","onCreate invoked");
    }

    //met a jour la base de donnée
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertScore(String name, int score){
        name = name.replace("'","''");
        String strSql="insert into T_scores (name, score, when_) values  ('"
                +name+"'," + score +", " + new Date().getTime() +")";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE","insertScore invoked");
    }

    public List<ScoreData> readTop10(){
        List<ScoreData> scores  = new ArrayList<>();

        //utiliser ordre sql
        String strSql ="select  * from  T_scores order by  score desc limit 10";
        Cursor cursor  = this.getReadableDatabase().rawQuery(strSql,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ScoreData score  = new ScoreData(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),new Date(cursor.getInt(3)));
            scores.add(score);
            cursor.moveToNext();
        }
        cursor.close();
        return scores;
    }

}
