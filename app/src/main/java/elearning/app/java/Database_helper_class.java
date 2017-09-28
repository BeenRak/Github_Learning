package elearning.app.java;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;



import android.database.sqlite.SQLiteException;

public class Database_helper_class extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/elearning.app.java/databases/";
	public final String TABLE_E_LEARNING = "ques_ans";
	public static final String ID_COLUMNS = "_id";
	public static final String QUES_COLUMN = "questions";
	public static final String ANS_COLUMN = "answers";

	private static String DB_NAME = "elearning_database_db";
	private SQLiteDatabase myDataBase;

	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 */
	public Database_helper_class(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();
		SQLiteDatabase db_Read = null;

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			db_Read = this.getReadableDatabase();
			db_Read.close();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}
		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {

		InputStream in = null;
		OutputStream out = null;
		String dbFilepath = DB_PATH + DB_NAME;
		try {
			in = myContext.getAssets().open(DB_NAME);
			out = new FileOutputStream(dbFilepath);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new Error("problem while copying the database");
		}

	}

	public void openDataBase() throws SQLiteException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public Cursor getCursor() {
		// TODO Auto-generated method stub
		SQLiteQueryBuilder querybuilder = new SQLiteQueryBuilder();
		querybuilder.setTables(TABLE_E_LEARNING);
		String[] columns = new String[] { ID_COLUMNS, QUES_COLUMN, ANS_COLUMN };

		Cursor mCursor = querybuilder.query(myDataBase, columns, null, null,
				null, null, null,null);
	
		return mCursor;
	}

	public String getQues(Cursor c) {
		return c.getString(1);
	}

	
	
	/// Get  content////////
	public Cursor getcorrect_Content(int Id)
	{
	String myPath = DB_PATH + DB_NAME;
	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);


	Cursor cur;
	cur=myDataBase.rawQuery("select quiz_text from correct where quiz_id='"+Id+"'",null);
//	cur=myDataBase.rawQuery("select quiz_text from correct where quiz_id=?",new String[]{String.valueOf(Id)});
	cur.moveToFirst();


	myDataBase.close();
	return cur;
	};

	
	// get the  of correct list
	public Cursor getQuiz_List()
	{
	String myPath = DB_PATH + DB_NAME;
	myDataBase= SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	Cursor cur;
	cur=myDataBase.rawQuery("select quiz_id,quiz_text,correct_answer from correct",null);
	cur.moveToFirst();
	int	i = cur.getCount();
	myDataBase.close();
	return cur;
	};

	
	// get option 
	
	public Cursor getAns(int quizid)
	{
	String myPath = DB_PATH + DB_NAME;
	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	Cursor cur;
	cur = myDataBase.rawQuery("select opt_ans from options where quiz_id='"+quizid+"'", null);
	//cur = myDataBase.rawQuery("select opt_ans from options where quiz_id=?",new String[]{String.valueOf(qusNo)});
	cur.moveToFirst();
	myDataBase.close();
	return cur;
	}

	
	//get option list

	
	public Cursor getAnsList()
	{
	String myPath = DB_PATH + DB_NAME;
	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);


	Cursor cur;
	cur = myDataBase.rawQuery("select opt_ans from options", null);
	cur.moveToFirst();
	myDataBase.close();
	return cur;
	}
	
	// get all options
	
	
	public Cursor getCorrAns()
	{
	String myPath = DB_PATH + DB_NAME;
	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);


	Cursor cur;
	cur = myDataBase.rawQuery("select correct_answer from correct", null);
	cur.moveToFirst();
	myDataBase.close();


	return cur;
	}

	public Cursor rawQuery(String string, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}
