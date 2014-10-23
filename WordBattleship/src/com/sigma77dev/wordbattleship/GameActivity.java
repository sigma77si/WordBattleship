package com.sigma77dev.wordbattleship;



import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.w3c.dom.Text;

/*import com.firebase.client.DataSnapshot;
 import com.firebase.client.Firebase;
 import com.firebase.client.FirebaseError;
 import com.firebase.client.ValueEventListener;*/










import com.sigma77dev.wordbattleship.R;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener {
	public static final String letterResource = null;
	Editable editable;
	Spinner spinner;
	SoundPool sp;
	private MenuItem soundOn, soundOff, menu;
	int[][] myboard, andrboard, myboardhits, andrboardhits;
	int miss, boom, applause, up, down, checkshotM, checkhitsM, checkshotA,
			checkhitsA, intTest;
	int turn = 1;
	int nummyhits = 0;
	int numandrhits = 0;
	public int GameLevel = 1;
	int rowS;
	int colS;
	int seconds=0;
	int secondsPopup=1;
	int NumOfShots = 0;
	int[] BestRes = new int[5];
	int a, b, v, g, d, e, j, z, i, ii, k, l, m, n, o, p, r, s, t, u, f, h, c,
			ch, sh, sht, y, x, iu, q, ru_a, ru_b, ru_v, ru_g, ru_d, ru_e,
			ru_ee, ru_eee, ru_j, ru_z, ru_i, ru_ii, ru_k, ru_l, ru_m, ru_n,
			ru_o, ru_p, ru_r, ru_s, ru_t, ru_u, ru_f, ru_h, ru_c, ru_ch, ru_sh,
			ru_sht, ru_y, ru_yy, ru_yyy, ru_x, ru_iu, ru_q, en_a, en_b, en_v,
			en_g, en_d, en_e, en_j, en_z, en_i, en_x, en_k, en_l, en_m, en_n,
			en_o, en_p, en_r, en_s, en_t, en_u, en_f, en_h, en_c, en_w, en_y,
			en_q;

	TextToSpeech tts;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}

		SharedPreferences preferences1 = getSharedPreferences(
				"BattleshipSettings", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor1 = preferences1.edit();
		editor1.putInt("Best1", BestRes[0]);
		editor1.putInt("Best2", BestRes[1]);
		editor1.putInt("Best3", BestRes[2]);
		editor1.putInt("Best4", BestRes[3]);
		editor1.putInt("Best5", BestRes[4]);
	//	editor1.putString("language", locale);

		editor1.commit();
		// editor1.putString("Number", MyID); editor1.commit();

	}

	String Idcreated = "true";
	String LastFirID;
	String popup = "on";
	String popup2 = "on";
	String locale;
	String soundOnOff = "on";
	View cellView,cellView1;
	int PutNextFIRID;
	String MyID, message, randomUUIDString, UUIDString, gameID,
			PokanaUUIDString, pokanaTimeString, pokanaAcceptString,
			usernameString;

	TableLayout table, tableMyBoard;
	Random rnd = new Random();
	Button Newgame, Test;
	Button Izberi;
	TextView Shots, Level, textShots, textLevel;
	String rndfourletterword, rndthreeletterword, rndtwoletterword,
			selectedWord, letter, StringTest;
	String[] fourletterword = { "дете", "елха", "жена", "кола", "лале", "мида",
			"небе", "нива", "поле", "чаша" };
	String[] threeletterword = { "бар", "бор", "вар", "вир", "дар", "мир",
			"нар", "пир", "пор", "цар" };
	String[] twoletterword = { "ад", "аз", "ас", "да", "до", "за", "на", "не",
			"от", "по" };

	String[] twoletterwordEN = { "be", "of", "in", "to", "it", "he", "on",
			"do", "at", "we" };
	String[] threeletterwordEN = { "are", "the", "and", "for", "you", "say",
			"she", "get", "one", "out" };
	String[] fourletterwordEN = { "have", "that", "this", "what", "make",
			"know", "year", "when", "come", "more" };

	String[] fourletterwordRU = { "один", "себя", "если", "рука", "даже",
			"дело", "глаз", "надо", "идти", "тоже" };
	String[] threeletterwordRU = { "что", "тот", "ето", "как", "она", "мир",
			"так", "уже", "для", "вот" };
	String[] twoletterwordRU = { "не", "он", "на", "по", "до", "но", "из",
			"за", "от", "да" };
	// 0 - big ship, 1 - middle ship, 2 - small ship
	CBattleFieldCell[] myShipsLocations = new CBattleFieldCell[3];
	String[] myWords = new String[3];
	CBattleFieldCell[] androidShipsLocations = new CBattleFieldCell[3];
	String[] androidWords = new String[3];
	Animation animRotate, animSequential, animMove, animBounce, animZoomout;
	private enum allLetters {
		а, б, в, г, д, е, ж, з, и, й, к, л, м, н, о, п, р, с, т, у, ф, х, ц, ч, ш, щ, ъ, ю, я, a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		SharedPreferences settings = getSharedPreferences("BattleshipSettings",
				Context.MODE_PRIVATE);
		//locale = settings.getString("language", "");
		//if ("null".equals(locale)) {
			locale = java.util.Locale.getDefault().getLanguage();
		//}
		setupUI();
		//soundOn =(MenuItem)findViewById(R.id.soundOnitem);
	//	soundOff = (MenuItem)findViewById(R.id.soundOffitem);
		this.menu = menu;

		table = (TableLayout) findViewById(R.id.andrBoard);
		tableMyBoard = (TableLayout) findViewById(R.id.myBoard);
		sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		miss = sp.load(this, R.raw.splash, 1);
		boom = sp.load(this, R.raw.explos1, 1);
		up = sp.load(this, R.raw.up, 1);
		down = sp.load(this, R.raw.down, 1);
		applause = sp.load(this, R.raw.applause, 1);
		myboard = new int[10][10];
		andrboard = new int[10][10];
		myboardhits = new int[10][10];
		andrboardhits = new int[10][10];
		Newgame = (Button) findViewById(R.id.NewGame);
		Izberi = (Button) findViewById(R.id.izberi);
		// Test = (Button) findViewById(R.id.test);
		Shots = (TextView) findViewById(R.id.tviewShots);
		textShots = (TextView) findViewById(R.id.tv_shots);
		Level = (TextView) findViewById(R.id.tviewLevel);
		textLevel = (TextView) findViewById(R.id.tv_level);
		Newgame.setOnClickListener(this);
		Izberi.setOnClickListener(this);
		// Test.setOnClickListener(this);
		animRotate = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.rotate);
		animSequential = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.sequential);
		animMove = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.move);
		animBounce = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.bounce);
		animZoomout = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.zoomout);
		
		load_sp();

		GenerateRandomShips(andrboardhits, androidShipsLocations, androidWords);
		GenerateRandomShips(myboardhits, myShipsLocations, myWords);
		VisualizeShips();

		SharedPreferences prfs = getSharedPreferences("BattleshipSettings",
				Context.MODE_PRIVATE);
		// SharedPreferences prfs = getSharedPreferences("BIGESTNUMBER",
		// Context.MODE_PRIVATE);

		BestRes[0] = prfs.getInt("Best1", 0);
		BestRes[1] = prfs.getInt("Best2", 0);
		BestRes[2] = prfs.getInt("Best3", 0);
		BestRes[3] = prfs.getInt("Best4", 0);
		BestRes[4] = prfs.getInt("Best5", 0);
		/*
		 * tts = new TextToSpeech(MainActivity.this,new
		 * TextToSpeech.OnInitListener() {
		 * 
		 * @Override public void onInit(int status) { // TODO Auto-generated
		 * method stub
		 * 
		 * if(status!=TextToSpeech.ERROR){ tts.setLanguage(Locale.US); } else
		 * Toast.makeText( MainActivity.this, "Sorry! Text To Speech failed...",
		 * Toast.LENGTH_LONG).show();
		 * 
		 * } });
		 */
		/*
		 * Player = new Firebase(FIREBASE_URL_1); mytest = new
		 * Firebase(FIREBASE_URL); pokanaTime = new Firebase(FIREBASE_URL);
		 * 
		 * pokanaUUID = new Firebase(FIREBASE_URL); pokanaAccept = new
		 * Firebase(FIREBASE_URL); mytest = new Firebase(FIREBASE_URL);
		 */
	}

	private void load_sp() {
		a = sp.load(this, R.raw.a, 1);
		b = sp.load(this, R.raw.b, 1);
		v = sp.load(this, R.raw.v, 1);
		g = sp.load(this, R.raw.g, 1);
		d = sp.load(this, R.raw.d, 1);
		e = sp.load(this, R.raw.e, 1);
		j = sp.load(this, R.raw.j, 1);
		z = sp.load(this, R.raw.z, 1);
		i = sp.load(this, R.raw.i, 1);
		ii = sp.load(this, R.raw.ii, 1);
		k = sp.load(this, R.raw.k, 1);
		l = sp.load(this, R.raw.l, 1);
		m = sp.load(this, R.raw.m, 1);
		n = sp.load(this, R.raw.n, 1);
		o = sp.load(this, R.raw.o, 1);
		p = sp.load(this, R.raw.p, 1);
		r = sp.load(this, R.raw.r, 1);
		s = sp.load(this, R.raw.s, 1);
		t = sp.load(this, R.raw.t, 1);
		u = sp.load(this, R.raw.u, 1);
		f = sp.load(this, R.raw.f, 1);
		h = sp.load(this, R.raw.h, 1);
		c = sp.load(this, R.raw.c, 1);
		ch = sp.load(this, R.raw.ch, 1);
		sh = sp.load(this, R.raw.sh, 1);
		sht = sp.load(this, R.raw.sht, 1);
		y = sp.load(this, R.raw.y, 1);
		x = sp.load(this, R.raw.x, 1);
		iu = sp.load(this, R.raw.iu, 1);
		q = sp.load(this, R.raw.q, 1);

		ru_a = sp.load(this, R.raw.bukvaa, 1);
		ru_b = sp.load(this, R.raw.bukvab, 1);
		ru_v = sp.load(this, R.raw.bukvav, 1);
		ru_g = sp.load(this, R.raw.bukvag, 1);
		ru_d = sp.load(this, R.raw.bukvad, 1);
		ru_e = sp.load(this, R.raw.bukvae, 1);
		ru_ee = sp.load(this, R.raw.bukvaee, 1);
		ru_j = sp.load(this, R.raw.bukvaj, 1);
		ru_z = sp.load(this, R.raw.bukvaz, 1);
		ru_i = sp.load(this, R.raw.bukvai, 1);
		ru_ii = sp.load(this, R.raw.bukvaii, 1);
		ru_k = sp.load(this, R.raw.bukvak, 1);
		ru_l = sp.load(this, R.raw.bukval, 1);
		ru_m = sp.load(this, R.raw.bukvam, 1);
		ru_n = sp.load(this, R.raw.bukvan, 1);
		ru_o = sp.load(this, R.raw.bukvao, 1);
		ru_p = sp.load(this, R.raw.bukvap, 1);
		ru_r = sp.load(this, R.raw.bukvar, 1);
		ru_s = sp.load(this, R.raw.bukvas, 1);
		ru_t = sp.load(this, R.raw.bukvat, 1);
		ru_u = sp.load(this, R.raw.bukvau, 1);
		ru_f = sp.load(this, R.raw.bukvaf, 1);
		ru_h = sp.load(this, R.raw.bukvah, 1);
		ru_c = sp.load(this, R.raw.bukvac, 1);
		ru_ch = sp.load(this, R.raw.bukvach, 1);
		ru_sh = sp.load(this, R.raw.bukvash, 1);
		ru_sht = sp.load(this, R.raw.bukvasht, 1);
		ru_y = sp.load(this, R.raw.bukvay, 1);
		ru_yy = sp.load(this, R.raw.bukvayy, 1);
		ru_yyy = sp.load(this, R.raw.bukvayyy, 1);
		ru_iu = sp.load(this, R.raw.bukvaiu, 1);
		ru_q = sp.load(this, R.raw.bukvaq, 1);

		en_a = sp.load(this, R.raw.en_a, 1);
		en_b = sp.load(this, R.raw.en_b, 1);
		en_c = sp.load(this, R.raw.en_c, 1);
		en_d = sp.load(this, R.raw.en_d, 1);
		en_e = sp.load(this, R.raw.en_e, 1);
		en_f = sp.load(this, R.raw.en_f, 1);
		en_g = sp.load(this, R.raw.en_g, 1);
		en_h = sp.load(this, R.raw.en_h, 1);
		en_i = sp.load(this, R.raw.en_i, 1);
		en_j = sp.load(this, R.raw.en_j, 1);
		en_k = sp.load(this, R.raw.en_k, 1);
		en_l = sp.load(this, R.raw.en_l, 1);
		en_m = sp.load(this, R.raw.en_m, 1);
		en_n = sp.load(this, R.raw.en_n, 1);
		en_o = sp.load(this, R.raw.en_o, 1);
		en_p = sp.load(this, R.raw.en_p, 1);
		en_q = sp.load(this, R.raw.en_q, 1);
		en_r = sp.load(this, R.raw.en_r, 1);
		en_s = sp.load(this, R.raw.en_s, 1);
		en_t = sp.load(this, R.raw.en_t, 1);
		en_u = sp.load(this, R.raw.en_u, 1);
		en_v = sp.load(this, R.raw.en_v, 1);
		en_w = sp.load(this, R.raw.en_w, 1);
		en_x = sp.load(this, R.raw.en_x, 1);
		en_y = sp.load(this, R.raw.en_y, 1);
		en_z = sp.load(this, R.raw.en_z, 1);
	}

	public void VisualizeShips() {
		table = (TableLayout) findViewById(R.id.myBoard);
		for (int i = 0; i < 10; i++) {
			TableRow tableRow = (TableRow) table.getChildAt(i);
			for (int j = 0; j < 10; j++) {
				View cellView = tableRow.getChildAt(j);
				if (myboardhits[i][j] == 1) {
					cellView.setBackgroundResource(R.drawable.ship);
				}
			}
		}
	}

	class CBattleFieldCell {
		private int Row;
		private int Column;
		// 0 means horizontal direction
		// 1 means vertical direction
		private int Direction;

		public CBattleFieldCell() {
			Row = 0;
			Column = 0;
			Direction = 0;
		}

		public CBattleFieldCell(int rowCell, int columnCell, int directionShip) {
			Row = rowCell;
			Column = columnCell;
			Direction = directionShip;
		}

		public int getRow() {
			return Row;
		}

		public void setRow(int argRow) {
			Row = argRow;
		}

		public int getColumn() {
			return Column;
		}

		public void setColumn(int argColumn) {
			Column = argColumn;
		}

		public int getDirection() {
			return Direction;
		}

		public void setDirection(int argDirection) {
			Direction = argDirection;
		}
	}

	private void GenerateRandomShips(int[][] board,
			CBattleFieldCell[] shipsLocations, String[] shipsWords) {
		int row;
		int col;
		// Locale.getDefault().getLanguage();
		// locale = java.util.Locale.getDefault().getLanguage();
		/*
		 * if(locale=="русский (Россия)"){ fourletterword=fourletterwordRU; }
		 * else if (locale=="English (United Kingdom)"){
		 * fourletterword=fourletterwordEN; } else if
		 * (locale=="български (България)"){ fourletterword=fourletterword; }
		 */
		boolean isShipPlaced = false;
		// 4 cells
		while (!isShipPlaced) {
			row = rnd.nextInt(10);
			col = rnd.nextInt(10);

			if (locale.equals("ru")) {
				rndfourletterword = (fourletterwordRU[new Random()
						.nextInt(fourletterwordRU.length)]);
			}

			else if (locale.equals("bg")) {
				rndfourletterword = (fourletterword[new Random()
						.nextInt(fourletterword.length)]);
			}

			else
				rndfourletterword = (fourletterwordEN[new Random()
						.nextInt(fourletterwordEN.length)]);

			// rndfourletterword = (fourletterword[new Random()
			// .nextInt(fourletterword.length)]);
			if (board[row][col] == 0) {
				if (col <= 6 && board[row][col + 1] == 0
						&& board[row][col + 2] == 0 && board[row][col + 3] == 0) {
					// right
					board[row][col] = 1;
					board[row][col + 1] = 1;
					board[row][col + 2] = 1;
					board[row][col + 3] = 1;
					shipsLocations[0] = new CBattleFieldCell(row, col, 0);
					shipsWords[0] = rndfourletterword; // Should be a random
														// generated word from
														// the list
					isShipPlaced = true;
				} else if (row <= 6 && board[row + 1][col] == 0
						&& board[row + 2][col] == 0 && board[row + 3][col] == 0) {
					// down
					board[row][col] = 1;
					board[row + 1][col] = 1;
					board[row + 2][col] = 1;
					board[row + 3][col] = 1;
					shipsLocations[0] = new CBattleFieldCell(row, col, 1);
					shipsWords[0] = rndfourletterword; // Should be a random
														// generated word from
														// the list
					isShipPlaced = true;
				}
			}
		}

		// 3 cells
		isShipPlaced = false;
		while (!isShipPlaced) {
			row = rnd.nextInt(10);
			col = rnd.nextInt(10);

			if (locale.equals("ru")) {
				rndthreeletterword = (threeletterwordRU[new Random()
						.nextInt(threeletterwordRU.length)]);
			}

			else if (locale.equals("bg")) {
				rndthreeletterword = (threeletterword[new Random()
						.nextInt(threeletterword.length)]);
			} else
				rndthreeletterword = (threeletterwordEN[new Random()
						.nextInt(threeletterwordEN.length)]);

			// rndthreeletterword = (threeletterword[new Random()
			// .nextInt(threeletterword.length)]);
			if (board[row][col] == 0) {
				if (col <= 7
						&& board[row][col + 1] == 0
						&& board[row][col + 2] == 0
						&& IsEmptyAroundShip(new CBattleFieldCell(row, col, 0),
								3, board)) {
					// right
					board[row][col] = 1;
					board[row][col + 1] = 1;
					board[row][col + 2] = 1;
					shipsLocations[1] = new CBattleFieldCell(row, col, 0);

					shipsWords[1] = rndthreeletterword; // Should be a random
														// generated word from
														// the list
					isShipPlaced = true;
				} else if (row <= 7
						&& board[row + 1][col] == 0
						&& board[row + 2][col] == 0
						&& IsEmptyAroundShip(new CBattleFieldCell(row, col, 1),
								3, board)) {
					// down
					board[row][col] = 1;
					board[row + 1][col] = 1;
					board[row + 2][col] = 1;
					shipsLocations[1] = new CBattleFieldCell(row, col, 1);

					shipsWords[1] = rndthreeletterword;// Should be a random
														// generated word from
														// the list
					isShipPlaced = true;
				}
			}
		}
		// 2 cells
		isShipPlaced = false;
		while (!isShipPlaced) {
			row = rnd.nextInt(10);
			col = rnd.nextInt(10);

			if (locale.equals("ru")) {
				rndtwoletterword = (twoletterwordRU[new Random()
						.nextInt(twoletterwordRU.length)]);
			}

			else if (locale.equals("bg")) {
				rndtwoletterword = (twoletterword[new Random()
						.nextInt(twoletterword.length)]);
			} else
				rndtwoletterword = (twoletterwordEN[new Random()
						.nextInt(twoletterwordEN.length)]);

			// rndtwoletterword = (twoletterword[new Random()
			// .nextInt(twoletterword.length)]);
			if (board[row][col] == 0) {
				if (col <= 8
						&& board[row][col + 1] == 0
						&& IsEmptyAroundShip(new CBattleFieldCell(row, col, 0),
								2, board)) {
					// right
					board[row][col] = 1;
					board[row][col + 1] = 1;
					shipsLocations[2] = new CBattleFieldCell(row, col, 0);

					shipsWords[2] = rndtwoletterword; // Should be a random
														// generated word from
														// the list
					isShipPlaced = true;

				} else if (row <= 7
						&& board[row + 1][col] == 0
						&& IsEmptyAroundShip(new CBattleFieldCell(row, col, 1),
								2, board)) {
					// down
					board[row][col] = 1;
					board[row + 1][col] = 1;
					shipsLocations[2] = new CBattleFieldCell(row, col, 1);
					shipsWords[2] = rndtwoletterword; // Should be a random
														// generated word from
														// the list
					isShipPlaced = true;
				}
			}
		}
	}

	private boolean IsEmptyAroundShip(CBattleFieldCell shipFirstCell,
			int length, int[][] board) {
		boolean isEmpty = true;

		int row = shipFirstCell.getRow();
		int col = shipFirstCell.getColumn();

		if (shipFirstCell.getDirection() == 0) {
			// Horizontal
			// Check left and right of the ship
			if ((col == 0 || board[row][col - 1] == 0)
					&& (col + length - 1 == 9 || board[row][col + length] == 0)) {
				isEmpty = true;
			} else {
				isEmpty = false;
			}

			// Check all the cells over the ship
			if (isEmpty && (row > 0)) {
				// loop all the cells
				for (int i = 0; i < length; i++) {
					isEmpty = isEmpty && (board[row - 1][col + i] == 0);
				}
			}

			// Check all the cells below the ship
			if (isEmpty && (row < 9)) {
				// loop all the cells
				for (int i = 0; i < length; i++) {
					isEmpty = isEmpty && (board[row + 1][col + i] == 0);
				}
			}
		} else if (shipFirstCell.getDirection() == 1) {
			// Vertical
			// Check up and down of the ship
			if ((row == 0 || board[row - 1][col] == 0)
					&& (row + length - 1 == 9 || board[row + length][col] == 0)) {
				isEmpty = true;
			} else {
				isEmpty = false;
			}

			// Check all the cells on the left the ship
			if (isEmpty && (col > 0)) {
				// loop all the cells
				for (int i = 0; i < length; i++) {
					isEmpty = isEmpty && (board[row + i][col - 1] == 0);
				}
			}

			// Check all the cells on the right the ship
			if (isEmpty && (col < 9)) {
				// loop all the cells
				for (int i = 0; i < length; i++) {
					isEmpty = isEmpty && (board[row + i][col + 1] == 0);
				}
			}
		}

		// empty around the ship
		// && (row - 2 == 0 || board[row - 3][col] == 0)
		// && (row == 9 || board[row + 1][col] == 0)
		// && (col == 0 || (board[row][col - 1] == 0
		// && board[row - 1][col - 1] == 0 && board[row - 2][col - 1] == 0))
		// && (col == 9 || (board[row][col + 1] == 0
		// && board[row - 1][col + 1] == 0 && board[row - 2][col + 1] == 0)))

		return isEmpty;
	}

	

	private void setupUI() {

		table = (TableLayout) findViewById(R.id.andrBoard);

		for (int row = 0; row < table.getChildCount(); row++) {
			View view = table.getChildAt(row);
			if (view instanceof TableRow) {
				TableRow tableRow = (TableRow) table.getChildAt(row);
				for (int col = 0; col < tableRow.getChildCount(); col++) {
					if (tableRow.getChildAt(col) instanceof ImageView) {
						View cellView = tableRow.getChildAt(col);
						cellView.setOnClickListener(new ImageViewClickListener(
								row, col));
					}
				}
			}
		}
	}

	public String getHitCellLetter(int rowHitCell, int colHitCell,
			CBattleFieldCell[] shipsLocations, String[] shipsWords) {
		String letter = "";

		for (int i = 0; i < 3; i++) {
			int row = shipsLocations[i].getRow();
			int col = shipsLocations[i].getColumn();
			int direction = shipsLocations[i].getDirection();
			// Show the letters, mark the cells as hit

			for (int j = 0; j < 4 - i; j++) {
				if (direction == 0) {
					if (row == rowHitCell && col + j == colHitCell) {
						letter = shipsWords[i].substring(j, j + 1);
						break;
					}

				} else if (direction == 1) {
					if (row + j == rowHitCell && col == colHitCell) {
						letter = shipsWords[i].substring(j, j + 1);
						break;
					}
				}
			}
		}
		return letter;
	}

	class ImageViewClickListener implements OnClickListener {
		private int row = -1;
		private int col = -1;

		public ImageViewClickListener(int row, int column) {
			this.row = row;
			this.col = column;

		}

		public void onClick(View v) {

			// Toast.makeText(MainActivity.this,"РЕД"+" "+ row + " "
			// +"КОЛОНА"+" "+ col, Toast.LENGTH_SHORT).show();

			table = (TableLayout) findViewById(R.id.andrBoard);
			checkshotA = andrboard[row][col];
			checkhitsA = andrboardhits[row][col];

			if (checkshotA == 0 && turn == 1) {

				turn = 2;

				NumOfShots++;
				String n = Integer.toString(NumOfShots);
				Shots.setText(n);
				// mytest.setValue(turn);
				if (checkhitsA == 0) {
					if (soundOnOff == "on") {
						sp.play(miss, 1, 1, 0, 0, 1);
					}
					TableRow tableRow = (TableRow) table.getChildAt(row);
					View cellView = tableRow.getChildAt(col);
					
					cellView.setBackgroundResource(R.drawable.btn_blue_matte_shot);
					
					andrboard[row][col] = 1;
				} else if (checkhitsA == 1) {
					TableRow tableRow = (TableRow) table.getChildAt(row);
					View cellView = tableRow.getChildAt(col);
					
					andrboard[row][col] = 2;
					String hitLetter = getHitCellLetter(row, col,
							androidShipsLocations, androidWords);
					TableRow tableRow1 = (TableRow) table.getChildAt(row);
					 cellView1 = tableRow1.getChildAt(col);
					cellView1
							.setBackgroundResource(GetImageForLetter(hitLetter));
					GetSoundForLetter(hitLetter);
					cellView1.startAnimation(animRotate);
					
					numandrhits++;
					if (numandrhits == 9) {

						if (GameLevel < 5) {
							if (soundOnOff == "on") {
							sp.play(up, 1, 1, 0, 0, 1);
							sp.play(applause, 1, 1, 0, 0, 1);
							}

							turn = 3;
							GameLevel++;
							String l = Integer.toString(GameLevel);
							Level.setText(l);

							table.setBackgroundColor(Color.RED);

							n = Integer.toString(NumOfShots);
							Shots.setText(n);
							popupNextLevelNew();

						} else {
							turn = 3;
							if (soundOnOff == "on") {
							sp.play(up, 1, 1, 0, 0, 1);
							sp.play(applause, 1, 1, 0, 0, 1);
							}
							popupNextLevelAllLang();
							GameLevel = 1;

						}
					}

				}

			} else if (checkshotA == 1 && turn == 1) {
				Toast.makeText(GameActivity.this, R.string.shoted,
						Toast.LENGTH_SHORT).show();
				String hitLetter = getHitCellLetter(row, col,
						androidShipsLocations, androidWords);
				TableRow tableRow1 = (TableRow) table.getChildAt(row);
				 cellView1 = tableRow1.getChildAt(col);
				cellView1
						.setBackgroundResource(GetImageForLetter(hitLetter));
				GetSoundForLetter(hitLetter);

			}

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					andrshot();

				}
			}, 1200);
			if (turn == 2) {
				// table2.setBackgroundColor(Color.GRAY);
				table.setBackgroundColor(Color.BLACK);
			}
		}

		private Object ValueOf(int numOfShots) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private void delay(Integer sec) {
int milsec=sec*1300;
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				andrshot();

			}
		}, milsec);
		
		seconds=1;

	}

	private void popupNextLevelAllLang() {
		if (locale.equals("en") || locale.equals("bg") || locale.equals("ru")) {
			popupNextLevel(locale);
		} else
			popupNextLevel("en");
	}

	private void popupNextLevel_en() {
		if (GameLevel < 5) {

			Toast.makeText(GameActivity.this, "You drowned all enemy ship",
					Toast.LENGTH_SHORT).show();

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					GameActivity.this);

			// set title
			alertDialogBuilder.setTitle("Level");

			// set dialog message
			alertDialogBuilder
					.setMessage("You start level" + " " + GameLevel)
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									newGame();
									dialog.cancel();
									NumOfShots = 0;

									String n = Integer.toString(NumOfShots);
									Shots.setText(n);

								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		} else {

			Toast.makeText(GameActivity.this, "*****Yes*****",
					Toast.LENGTH_SHORT).show();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					GameActivity.this);

			// set title
			alertDialogBuilder.setTitle("Compliments");

			// set dialog message
			alertDialogBuilder
					.setMessage("You completed all levels - Congratulations")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is
									// clicked, close
									// current activity
									dialog.cancel();

								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
	}

	private void popupNextLevel(String currentLocal) {
		Map<String, Map<String, String>> mapLanguage = new HashMap<String, Map<String, String>>();
		Map<String, String> bgLanguageMap = new HashMap<String, String>();
		Map<String, String> ruLanguageMap = new HashMap<String, String>();
		Map<String, String> enLanguageMap = new HashMap<String, String>();
		Map<String, String> currentLanguageMap = new HashMap<String, String>();
		bgLanguageMap.put("ALL_SHIPS_SINK", "Потопихте всички вражески кораби");
		bgLanguageMap.put("LEVEL", "Ниво");
		bgLanguageMap.put("NEW_RECORD", "Нов рекорд!");
		bgLanguageMap.put("SHOTS_IN_THIS_GAME", "Брой изстрели в тази игра:");
		bgLanguageMap
				.put("BEST_RESULT_FOR_LEVEL", "Най добър резултат за ниво");
		bgLanguageMap.put("BEFORE_THAT_GAME", "преди тази игра");
		bgLanguageMap.put("START_LEVEL", "Започвате ниво");
		bgLanguageMap.put("GREETINGS", "Браво");
		bgLanguageMap.put("ALL_LEVELS_PASSED",
				"Преминахте всички нива - Поздравления");
		bgLanguageMap.put("YES", "Да");
		mapLanguage.put("bg", bgLanguageMap);

		ruLanguageMap.put("ALL_SHIPS_SINK", "Вы утонули все вражеские корабли");
		ruLanguageMap.put("LEVEL", "Уровень");
		ruLanguageMap.put("NEW_RECORD", "Новый рекорд!");
		ruLanguageMap.put("SHOTS_IN_THIS_GAME",
				"Количество бросков в этой игре");
		ruLanguageMap.put("BEST_RESULT_FOR_LEVEL",
				"Самый хороший результат для уровня");
		ruLanguageMap.put("BEFORE_THAT_GAME", "до этой игры");
		ruLanguageMap.put("START_LEVEL", "Начинаете уровень");
		ruLanguageMap.put("GREETINGS", "Браво");
		ruLanguageMap.put("ALL_LEVELS_PASSED",
				"Вы прошли все уровни - Поздравляем");
		ruLanguageMap.put("YES", "Да");
		mapLanguage.put("ru", ruLanguageMap);

		enLanguageMap.put("ALL_SHIPS_SINK", "You sunk all enemy ships.");
		enLanguageMap.put("LEVEL", "Level");
		enLanguageMap.put("NEW_RECORD", "New record!");
		enLanguageMap.put("SHOTS_IN_THIS_GAME", "Number of shots in this game");
		enLanguageMap.put("BEST_RESULT_FOR_LEVEL", "The best result for level");
		enLanguageMap.put("BEFORE_THAT_GAME", "before that game");
		enLanguageMap.put("START_LEVEL", "Start level");
		enLanguageMap.put("GREETINGS", "Congratulations");
		enLanguageMap.put("ALL_LEVELS_PASSED",
				"You passed all levels - congratulations");
		enLanguageMap.put("YES", "Yes");
		mapLanguage.put("en", enLanguageMap);

		currentLanguageMap = mapLanguage.get(currentLocal);

		if (GameLevel < 6) {
			Toast.makeText(GameActivity.this,
					currentLanguageMap.get("ALL_SHIPS_SINK"),
					Toast.LENGTH_SHORT).show();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					GameActivity.this);

			// set title
			alertDialogBuilder.setTitle(currentLanguageMap.get("LEVEL")
					+ " "
					+ String.valueOf(GameLevel - 1)
					+ ((NumOfShots < BestRes[GameLevel - 1]) ? " "
							+ currentLanguageMap.get("NEW_RECORD") : ""));

			// set dialog message
			alertDialogBuilder
					.setMessage(
							currentLanguageMap.get("SHOTS_IN_THIS_GAME")
									+ ": "
									+ NumOfShots
									+ " "
									+ ((NumOfShots < BestRes[GameLevel - 1]) ? " "
											+ currentLanguageMap
													.get("NEW_RECORD")
											: "")
									+ "\n "
									+ currentLanguageMap
											.get("BEST_RESULT_FOR_LEVEL")
									+ " "
									+ String.valueOf(GameLevel - 1)
									+ " "
									+ currentLanguageMap
											.get("BEFORE_THAT_GAME") + ": "
									+ BestRes[GameLevel - 1] + " \n"
									+ currentLanguageMap.get("START_LEVEL")
									+ " " + GameLevel)
					.setCancelable(false)
					.setPositiveButton(currentLanguageMap.get("YES"),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									if (NumOfShots < BestRes[GameLevel - 1]
											|| BestRes[GameLevel - 1] == 0) {
										BestRes[GameLevel - 1] = NumOfShots;
									}
									;
									newGame();
									dialog.cancel();
									NumOfShots = 0;

									String n = Integer.toString(NumOfShots);
									Shots.setText(n);
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		} else {

			Toast.makeText(GameActivity.this,
					"*****" + currentLanguageMap.get("GREETINGS") + "*****",
					Toast.LENGTH_SHORT).show();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					GameActivity.this);

			// set title
			alertDialogBuilder.setTitle(currentLanguageMap.get("GREETINGS"));

			// set dialog message
			alertDialogBuilder
					.setMessage(currentLanguageMap.get("ALL_LEVELS_PASSED"))
					.setCancelable(false)
					.setPositiveButton(currentLanguageMap.get("YES"),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is
									// clicked, close
									// current activity
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
	}

	private void popupNextLevelNew() {
		if (GameLevel < 6) {
			Toast.makeText(GameActivity.this, R.string.ALL_SHIPS_SINK,
					Toast.LENGTH_SHORT).show();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					GameActivity.this);

			// set title
			alertDialogBuilder.setTitle(getString(R.string.LEVEL)
					+ " "
					+ String.valueOf(GameLevel - 1)
					+ ((NumOfShots < BestRes[GameLevel - 1]) ? " "
							+ getString(R.string.NEW_RECORD) : ""));

			// set dialog message
			alertDialogBuilder
					.setMessage(
							getString(R.string.SHOTS_IN_THIS_GAME)
									+ NumOfShots
									+ " "
									+ ((NumOfShots < BestRes[GameLevel - 1]) ? " "
											+ getString(R.string.NEW_RECORD)
											: "") + "\n"
									+ getString(R.string.BEST_RESULT_FOR_LEVEL)
									+ " " + String.valueOf(GameLevel - 1) + " "
									+ getString(R.string.BEFORE_THAT_GAME)
									+ BestRes[GameLevel - 1] + " \n"
									+ getString(R.string.START_LEVEL) + " "
									+ GameLevel)
					.setCancelable(false)
					.setPositiveButton(R.string.YES,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									if (NumOfShots < BestRes[GameLevel - 1]
											|| BestRes[GameLevel - 1] == 0) {
										BestRes[GameLevel - 1] = NumOfShots;
									}
									;
									newGame();
									dialog.cancel();
									NumOfShots = 0;
									secondsPopup=1;

									String n = Integer.toString(NumOfShots);
									Shots.setText(n);
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		} else {

			Toast.makeText(GameActivity.this,
					"*****" + getString(R.string.GREETINGS) + "*****",
					Toast.LENGTH_SHORT).show();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					GameActivity.this);

			// set title
			alertDialogBuilder.setTitle(getString(R.string.GREETINGS));

			// set dialog message
			alertDialogBuilder
					.setMessage(getString(R.string.ALL_LEVELS_PASSED))
					.setCancelable(false)
					.setPositiveButton(R.string.YES,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is
									// clicked, close
									// current activity
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}

	}

	private void popupNextLevel_ru() {
		if (GameLevel < 5) {
			Toast.makeText(GameActivity.this,
					"Вы утонули все вражеские корабли", Toast.LENGTH_SHORT)
					.show();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					GameActivity.this);

			// set title
			alertDialogBuilder.setTitle("Уровень");

			// set dialog message
			alertDialogBuilder
					.setMessage("Вы начинаете уровень" + " " + GameLevel)
					.setCancelable(false)
					.setPositiveButton("Да",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									newGame();
									dialog.cancel();
									NumOfShots = 0;

									String n = Integer.toString(NumOfShots);
									Shots.setText(n);

								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		} else {

			Toast.makeText(GameActivity.this, "*****Браво*****",
					Toast.LENGTH_SHORT).show();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					GameActivity.this);

			// set title
			alertDialogBuilder.setTitle("Браво");

			// set dialog message
			alertDialogBuilder
					.setMessage("Выполненные все уровни - Поздравляем")
					.setCancelable(false)
					.setPositiveButton("Да",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is
									// clicked, close
									// current activity
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
	}

	private CBattleFieldCell GetNextAndroidMove_Random() {
		CBattleFieldCell newCell = new CBattleFieldCell();

		while (myboard[newCell.Row][newCell.Column] != 0) {
			newCell.setRow(rnd.nextInt(10));
			newCell.setColumn(rnd.nextInt(10));
		}

		return newCell;
	}

	private CBattleFieldCell[] GetFoundShipsLocations(int lenghtShip,
			int board[][]) {
		boolean isShipFound;
		int foundShipNumber = 0;
		CBattleFieldCell[] foundShips = new CBattleFieldCell[4];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (myboard[i][j] == 2) {
					// extend the cell right
					if (j + lenghtShip - 1 < 10) {
						isShipFound = true;
						for (int k = 1; k < lenghtShip; k++) {
							isShipFound = isShipFound
									&& (myboard[i][j + k] == 2);
						}
						if (isShipFound
								&& (j == 0 || myboard[i][j - 1] != 2)
								&& (j + lenghtShip - 1 == 9 || myboard[i][j
										+ lenghtShip] != 2)) {
							foundShips[foundShipNumber] = new CBattleFieldCell(
									i, j, 0);
							foundShipNumber += 1;
						}
					}
				}
				if (myboard[j][i] == 2) {
					// extend the cell down
					if (j + lenghtShip - 1 < 10) {
						isShipFound = true;
						for (int k = 1; k < lenghtShip; k++) {
							isShipFound = isShipFound
									&& (myboard[j + k][i] == 2);
						}
						if (isShipFound
								&& (j == 0 || myboard[j - 1][i] != 2)
								&& (j + lenghtShip - 1 == 9 || myboard[j
										+ lenghtShip][i] != 2)) {
							foundShips[foundShipNumber] = new CBattleFieldCell(
									j, i, 1);
						}
					}
				}
			}
		}

		return foundShips;
	}

	private CBattleFieldCell GetNextAndroidMove() {
		CBattleFieldCell newCell = new CBattleFieldCell();
		boolean isNextMoveFound = false;
		boolean isBigShipFound = false;
		boolean isMiddleShipFound = false;
		boolean isSmallShipFound = false;

		CBattleFieldCell[] foundBigShips = GetFoundShipsLocations(4, myboard);
		isBigShipFound = foundBigShips[0] != null;

		CBattleFieldCell[] foundMiddleShips = GetFoundShipsLocations(3, myboard);
		isMiddleShipFound = foundMiddleShips[0] != null;

		CBattleFieldCell[] foundSmallShips = GetFoundShipsLocations(2, myboard);
		isSmallShipFound = foundSmallShips[0] != null;

		if (isMiddleShipFound && !isBigShipFound) {
			int foundShipNumber = 0;
			while (foundMiddleShips[foundShipNumber] != null) {
				int rowCurrent = foundMiddleShips[foundShipNumber].getRow();
				int colCurrent = foundMiddleShips[foundShipNumber].getColumn();
				int directionCurrent = foundMiddleShips[foundShipNumber]
						.getDirection();

				if (directionCurrent == 0) {
					if (colCurrent > 0
							&& myboard[rowCurrent][colCurrent - 1] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent,
								colCurrent - 1, 0);
					} else if (colCurrent < 7
							&& myboard[rowCurrent][colCurrent + 3] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent,
								colCurrent + 3, 0);
					}
				} else if (directionCurrent == 1) {
					if (rowCurrent > 0
							&& myboard[rowCurrent - 1][colCurrent] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent - 1,
								colCurrent, 0);
					} else if (rowCurrent < 7
							&& myboard[rowCurrent + 3][colCurrent] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent + 3,
								colCurrent, 0);
					}
				}
				if (isNextMoveFound)
					break;
				foundShipNumber++;
			}
		}

		if (!isNextMoveFound) {
			if (isSmallShipFound && (!isMiddleShipFound || !isBigShipFound)) {
				int foundShipNumber = 0;
				while (foundSmallShips[foundShipNumber] != null) {
					int rowCurrent = foundSmallShips[foundShipNumber].getRow();
					int colCurrent = foundSmallShips[foundShipNumber]
							.getColumn();
					int directionCurrent = foundSmallShips[foundShipNumber]
							.getDirection();

					if (directionCurrent == 0) {
						if (colCurrent > 0
								&& myboard[rowCurrent][colCurrent - 1] == 0) {
							isNextMoveFound = true;
							newCell = new CBattleFieldCell(rowCurrent,
									colCurrent - 1, 0);
						} else if (colCurrent < 8
								&& myboard[rowCurrent][colCurrent + 2] == 0) {
							isNextMoveFound = true;
							newCell = new CBattleFieldCell(rowCurrent,
									colCurrent + 2, 0);
						}
					} else if (directionCurrent == 1) {
						if (rowCurrent > 0
								&& myboard[rowCurrent - 1][colCurrent] == 0) {
							isNextMoveFound = true;
							newCell = new CBattleFieldCell(rowCurrent - 1,
									colCurrent, 0);
						} else if (rowCurrent < 8
								&& myboard[rowCurrent + 2][colCurrent] == 0) {
							isNextMoveFound = true;
							newCell = new CBattleFieldCell(rowCurrent + 2,
									colCurrent, 0);
						}
					}
					if (isNextMoveFound)
						break;
					foundShipNumber++;
				}
			}

		}

		if (!isNextMoveFound) {
			// if all left/right/down/up cells of a filled cell are not checked
			// or empty - use one of the not checked as the next move
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (myboard[i][j] == 2) {
						if ((i == 0 || myboard[i - 1][j] == 0 || myboard[i - 1][j] == 1)
								&& (i == 9 || myboard[i + 1][j] == 0 || myboard[i + 1][j] == 1)
								&& (j == 0 || myboard[i][j - 1] == 0 || myboard[i][j - 1] == 1)
								&& (j == 9 || myboard[i][j + 1] == 0 || myboard[i][j + 1] == 1)) {
							if (i > 0 && myboard[i - 1][j] == 0) {
								newCell.setRow(i - 1);
								newCell.setColumn(j);
								isNextMoveFound = true;
							} else if (i < 9 && myboard[i + 1][j] == 0) {
								newCell.setRow(i + 1);
								newCell.setColumn(j);
								isNextMoveFound = true;
							} else if (j > 0 && myboard[i][j - 1] == 0) {
								newCell.setRow(i);
								newCell.setColumn(j - 1);
								isNextMoveFound = true;
							} else if (j < 9 && myboard[i][j + 1] == 0) {
								newCell.setRow(i);
								newCell.setColumn(j + 1);
								isNextMoveFound = true;
							}

						}
					}
				}
			}
		}

		//

		if (!isNextMoveFound) {
			boolean isBorderWithShip = true;
			boolean isEnoughPlaceForShip = true;
			int trialNumber = 0;
			while ((isBorderWithShip || !isEnoughPlaceForShip)
					&& trialNumber < 100) {
				int row = rnd.nextInt(10);
				int col = rnd.nextInt(10);
				if (myboard[row][col] == 0) {
					isBorderWithShip = getHasBorderWithShip(row, col);

					isEnoughPlaceForShip = getHasEnoughPlaceForShip(row, col,
							isSmallShipFound, isMiddleShipFound, isBigShipFound);

					if (!isBorderWithShip && isEnoughPlaceForShip) {
						newCell.setRow(row);
						newCell.setColumn(col);
					}
				}
				trialNumber++;
			}

		}

		if (!isNextMoveFound) {
			newCell = GetNextAndroidMove_Random();
		}

		return newCell;
	}

	private boolean getHasEnoughPlaceForShip(int row, int col,
			boolean isSmallShipFound, boolean isMiddleShipFound,
			boolean isBigShipFound) {
		boolean isEnoughPlace = true;

		if (!isSmallShipFound) {

			// Just check if there is empty cell up, down, left or right
			isEnoughPlace = (row > 0 && myboard[row - 1][col] == 0)
					|| (row < 9 && myboard[row + 1][col] == 0)
					|| (col > 0 && myboard[row][col - 1] == 0)
					|| (col < 9 && myboard[row][col + 1] == 0);
		} else if (!isMiddleShipFound) {
			isEnoughPlace = AreCellsEmpty(row, col, 1, 3)
					|| AreCellsEmpty(row - 1, col, 1, 3)
					|| AreCellsEmpty(row - 2, col, 1, 3)
					|| AreCellsEmpty(row, col, 0, 3)
					|| AreCellsEmpty(row, col - 1, 0, 3)
					|| AreCellsEmpty(row, col - 2, 0, 3);
		} else if (!isBigShipFound) {
			isEnoughPlace = AreCellsEmpty(row, col, 1, 4)
					|| AreCellsEmpty(row - 1, col, 1, 4)
					|| AreCellsEmpty(row - 2, col, 1, 4)
					|| AreCellsEmpty(row - 3, col, 1, 4)
					|| AreCellsEmpty(row, col, 0, 4)
					|| AreCellsEmpty(row, col - 1, 0, 4)
					|| AreCellsEmpty(row, col - 2, 0, 4)
					|| AreCellsEmpty(row, col - 3, 0, 4);
		}

		return isEnoughPlace;

	}

	private boolean AreCellsEmpty(int row, int col, int direction, int length)

	{
		boolean isEmpty = row >= 0 && row <= 9 && col >= 0 && col <= 9;

		if (isEmpty) {
			if (direction == 0) {
				if (col + length - 1 <= 9) {
					for (int i = 0; i < length; i++) {
						isEmpty = isEmpty && (myboard[row][col + i] == 0);
					}
				}
			} else if (direction == 1) {
				if (row + length - 1 <= 9) {
					for (int i = 0; i < length; i++) {
						isEmpty = isEmpty && (myboard[row + i][col] == 0);
					}
				}
			}
		}

		return isEmpty;
	}

	private boolean getHasBorderWithShip(int row, int col) {
		boolean isBorder = true;

		isBorder = (row > 0 && myboard[row - 1][col] == 2)
				|| (row < 9 && myboard[row + 1][col] == 2)
				|| (col > 0 && myboard[row][col - 1] == 2)
				|| (col < 9 && myboard[row][col + 1] == 2);

		return isBorder;
	}

	private CBattleFieldCell GetNextAndroidMove_ExtendAllShips() {
		CBattleFieldCell newCell = new CBattleFieldCell();
		boolean isNextMoveFound = false;
		boolean isBigShipFound = false;
		boolean isMiddleShipFound = false;
		boolean isSmallShipFound = false;

		CBattleFieldCell[] foundBigShips = GetFoundShipsLocations(4, myboard);
		isBigShipFound = foundBigShips[0] != null;

		CBattleFieldCell[] foundMiddleShips = GetFoundShipsLocations(3, myboard);
		isMiddleShipFound = foundMiddleShips[0] != null;

		if (isMiddleShipFound && !isBigShipFound) {
			int foundShipNumber = 0;
			while (foundMiddleShips[foundShipNumber] != null) {
				int rowCurrent = foundMiddleShips[foundShipNumber].getRow();
				int colCurrent = foundMiddleShips[foundShipNumber].getColumn();
				int directionCurrent = foundMiddleShips[foundShipNumber]
						.getDirection();

				if (directionCurrent == 0) {
					if (colCurrent > 0
							&& myboard[rowCurrent][colCurrent - 1] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent,
								colCurrent - 1, 0);
					} else if (colCurrent < 7
							&& myboard[rowCurrent][colCurrent + 3] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent,
								colCurrent + 3, 0);
					}
				} else if (directionCurrent == 1) {
					if (rowCurrent > 0
							&& myboard[rowCurrent - 1][colCurrent] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent - 1,
								colCurrent, 0);
					} else if (rowCurrent < 7
							&& myboard[rowCurrent + 3][colCurrent] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent + 3,
								colCurrent, 0);
					}
				}
				if (isNextMoveFound)
					break;
				foundShipNumber++;
			}
		}

		if (!isNextMoveFound) {
			CBattleFieldCell[] foundSmallShips = GetFoundShipsLocations(2,
					myboard);
			isSmallShipFound = foundSmallShips[0] != null;
			if (isSmallShipFound && (!isMiddleShipFound || !isBigShipFound)) {
				int foundShipNumber = 0;
				while (foundSmallShips[foundShipNumber] != null) {
					int rowCurrent = foundSmallShips[foundShipNumber].getRow();
					int colCurrent = foundSmallShips[foundShipNumber]
							.getColumn();
					int directionCurrent = foundSmallShips[foundShipNumber]
							.getDirection();

					if (directionCurrent == 0) {
						if (colCurrent > 0
								&& myboard[rowCurrent][colCurrent - 1] == 0) {
							isNextMoveFound = true;
							newCell = new CBattleFieldCell(rowCurrent,
									colCurrent - 1, 0);
						} else if (colCurrent < 8
								&& myboard[rowCurrent][colCurrent + 2] == 0) {
							isNextMoveFound = true;
							newCell = new CBattleFieldCell(rowCurrent,
									colCurrent + 2, 0);
						}
					} else if (directionCurrent == 1) {
						if (rowCurrent > 0
								&& myboard[rowCurrent - 1][colCurrent] == 0) {
							isNextMoveFound = true;
							newCell = new CBattleFieldCell(rowCurrent - 1,
									colCurrent, 0);
						} else if (rowCurrent < 8
								&& myboard[rowCurrent + 2][colCurrent] == 0) {
							isNextMoveFound = true;
							newCell = new CBattleFieldCell(rowCurrent + 2,
									colCurrent, 0);
						}
					}
					if (isNextMoveFound)
						break;
					foundShipNumber++;
				}
			}

		}

		if (!isNextMoveFound) {
			// if all left/right/down/up cells of a filled cell are not checked
			// or empty - use one of the not checked as the next move
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (myboard[i][j] == 2) {
						if ((i == 0 || myboard[i - 1][j] == 0 || myboard[i - 1][j] == 1)
								&& (i == 9 || myboard[i + 1][j] == 0 || myboard[i + 1][j] == 1)
								&& (j == 0 || myboard[i][j - 1] == 0 || myboard[i][j - 1] == 1)
								&& (j == 9 || myboard[i][j + 1] == 0 || myboard[i][j + 1] == 1)) {
							if (i > 0 && myboard[i - 1][j] == 0) {
								newCell.setRow(i - 1);
								newCell.setColumn(j);
								isNextMoveFound = true;
							} else if (i < 9 && myboard[i + 1][j] == 0) {
								newCell.setRow(i + 1);
								newCell.setColumn(j);
								isNextMoveFound = true;
							} else if (j > 0 && myboard[i][j - 1] == 0) {
								newCell.setRow(i);
								newCell.setColumn(j - 1);
								isNextMoveFound = true;
							} else if (j < 9 && myboard[i][j + 1] == 0) {
								newCell.setRow(i);
								newCell.setColumn(j + 1);
								isNextMoveFound = true;
							}

						}
					}
				}
			}
		}

		if (!isNextMoveFound) {
			newCell = GetNextAndroidMove_Random();
		}

		return newCell;
	}

	private CBattleFieldCell GetNextAndroidMove_ExtendMiddleShip() {
		CBattleFieldCell newCell = new CBattleFieldCell();
		boolean isNextMoveFound = false;
		boolean isBigShipFound = false;
		boolean isMiddleShipFound = false;
		boolean isSmallShipFound = false;

		CBattleFieldCell[] foundBigShips = GetFoundShipsLocations(4, myboard);
		isBigShipFound = foundBigShips[0] != null;

		CBattleFieldCell[] foundMiddleShips = GetFoundShipsLocations(3, myboard);
		isMiddleShipFound = foundMiddleShips[0] != null;

		if (isMiddleShipFound && !isBigShipFound) {
			int foundShipNumber = 0;
			while (foundMiddleShips[foundShipNumber] != null) {
				int rowCurrent = foundMiddleShips[foundShipNumber].getRow();
				int colCurrent = foundMiddleShips[foundShipNumber].getColumn();
				int directionCurrent = foundMiddleShips[foundShipNumber]
						.getDirection();

				if (directionCurrent == 0) {
					if (colCurrent > 0
							&& myboard[rowCurrent][colCurrent - 1] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent,
								colCurrent - 1, 0);
					} else if (colCurrent < 7
							&& myboard[rowCurrent][colCurrent + 3] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent,
								colCurrent + 3, 0);
					}
				} else if (directionCurrent == 1) {
					if (rowCurrent > 0
							&& myboard[rowCurrent - 1][colCurrent] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent - 1,
								colCurrent, 0);
					} else if (rowCurrent < 7
							&& myboard[rowCurrent + 3][colCurrent] == 0) {
						isNextMoveFound = true;
						newCell = new CBattleFieldCell(rowCurrent + 3,
								colCurrent, 0);
					}
				}
				if (isNextMoveFound)
					break;
				foundShipNumber++;
			}
		}

		if (!isNextMoveFound) {
			CBattleFieldCell[] foundSmallShips = GetFoundShipsLocations(2,
					myboard);
			isSmallShipFound = foundSmallShips[0] != null;
		}

		if (!isNextMoveFound) {
			// if all left/right/down/up cells of a filled cell are not checked
			// or empty - use one of the not checked as the next move
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (myboard[i][j] == 2) {
						if ((i == 0 || myboard[i - 1][j] == 0 || myboard[i - 1][j] == 1)
								&& (i == 9 || myboard[i + 1][j] == 0 || myboard[i + 1][j] == 1)
								&& (j == 0 || myboard[i][j - 1] == 0 || myboard[i][j - 1] == 1)
								&& (j == 9 || myboard[i][j + 1] == 0 || myboard[i][j + 1] == 1)) {
							if (i > 0 && myboard[i - 1][j] == 0) {
								newCell.setRow(i - 1);
								newCell.setColumn(j);
								isNextMoveFound = true;
							} else if (i < 9 && myboard[i + 1][j] == 0) {
								newCell.setRow(i + 1);
								newCell.setColumn(j);
								isNextMoveFound = true;
							} else if (j > 0 && myboard[i][j - 1] == 0) {
								newCell.setRow(i);
								newCell.setColumn(j - 1);
								isNextMoveFound = true;
							} else if (j < 9 && myboard[i][j + 1] == 0) {
								newCell.setRow(i);
								newCell.setColumn(j + 1);
								isNextMoveFound = true;
							}

						}
					}
				}
			}
		}

		if (!isNextMoveFound) {
			newCell = GetNextAndroidMove_Random();
		}

		return newCell;
	}

	private CBattleFieldCell GetNextAndroidMove_SingleCell() {
		CBattleFieldCell newCell = new CBattleFieldCell();
		boolean isNextMoveFound = false;
		// if all left/right/down/up cells of a filled cell are not checked or
		// empty - use one of the not checked as the next move
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (myboard[i][j] == 2) {
					if ((i == 0 || myboard[i - 1][j] == 0 || myboard[i - 1][j] == 1)
							&& (i == 9 || myboard[i + 1][j] == 0 || myboard[i + 1][j] == 1)
							&& (j == 0 || myboard[i][j - 1] == 0 || myboard[i][j - 1] == 1)
							&& (j == 9 || myboard[i][j + 1] == 0 || myboard[i][j + 1] == 1)) {
						if (i > 0 && myboard[i - 1][j] == 0) {
							newCell.setRow(i - 1);
							newCell.setColumn(j);
							isNextMoveFound = true;
						} else if (i < 9 && myboard[i + 1][j] == 0) {
							newCell.setRow(i + 1);
							newCell.setColumn(j);
							isNextMoveFound = true;
						} else if (j > 0 && myboard[i][j - 1] == 0) {
							newCell.setRow(i);
							newCell.setColumn(j - 1);
							isNextMoveFound = true;
						} else if (j < 9 && myboard[i][j + 1] == 0) {
							newCell.setRow(i);
							newCell.setColumn(j + 1);
							isNextMoveFound = true;
						}

					}
				}
			}
		}

		if (!isNextMoveFound) {
			newCell = GetNextAndroidMove_Random();
		}

		return newCell;
	}

	public void andrshot() {

		
		if (GameLevel == 1) {
			CBattleFieldCell moveCell = GetNextAndroidMove_Random();
			rowS = moveCell.getRow();
			colS = moveCell.getColumn();
		} else if (GameLevel == 2) {
			CBattleFieldCell moveCell = GetNextAndroidMove_SingleCell();
			rowS = moveCell.getRow();
			colS = moveCell.getColumn();
		} else if (GameLevel == 3) {
			CBattleFieldCell moveCell = GetNextAndroidMove_ExtendMiddleShip();
			rowS = moveCell.getRow();
			colS = moveCell.getColumn();
		} else if (GameLevel == 4) {
			CBattleFieldCell moveCell = GetNextAndroidMove_ExtendAllShips();
			rowS = moveCell.getRow();
			colS = moveCell.getColumn();
		} else if (GameLevel == 5) {
			CBattleFieldCell moveCell = GetNextAndroidMove();
			rowS = moveCell.getRow();
			colS = moveCell.getColumn();
		}

		checkshotM = myboard[rowS][colS];
		checkhitsM = myboardhits[rowS][colS];

		if (checkshotM == 0 && turn == 2) {

			if (checkhitsM == 0) {
				if (soundOnOff == "on") {
				sp.play(miss, 1, 1, 0, 0, 1);
				}
				TableRow tableRow = (TableRow) tableMyBoard.getChildAt(rowS);
				View cellView = tableRow.getChildAt(colS);
				cellView.setBackgroundResource(R.drawable.btn_blue_matte_shot);
				
				myboard[rowS][colS] = 1;
			} else if (checkhitsM == 1) {
				
				TableRow tableRow = (TableRow) tableMyBoard.getChildAt(rowS);
				View cellView = tableRow.getChildAt(colS);
				
				myboard[rowS][colS] = 2;
				final String hitLetter = getHitCellLetter(rowS, colS,
						myShipsLocations, myWords);
				TableRow tableRow1 = (TableRow) tableMyBoard.getChildAt(rowS);
				final View cellView1 = tableRow1.getChildAt(colS);
				

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						cellView1
								.setBackgroundResource(GetImageForLetter(hitLetter));
						GetSoundForLetter(hitLetter);
						cellView1.startAnimation(animRotate);
					}
				}, 1500);

				

				nummyhits++;

			}
			if (nummyhits == 9) {
				if (soundOnOff == "on") {
				sp.play(down, 1, 1, 0, 0, 1);
				}
				Toast.makeText(GameActivity.this, R.string.you_lose,
						Toast.LENGTH_SHORT).show();
				// Toast.makeText(MainActivity.this, GameLevel,
				// Toast.LENGTH_SHORT)
				// .show();
				tableMyBoard.setBackgroundColor(Color.RED);
				turn = 3;

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						GameActivity.this);

				// set title
				alertDialogBuilder.setTitle(getString(R.string.level));

				// set dialog message
				alertDialogBuilder
						.setMessage(getString(R.string.you_lose)+ "\n" +getString(R.string.you_stay) + GameLevel)
						.setCancelable(false)
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										newGame();
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			} else {
				turn = 1;
				// mytest.setValue(turn);
				tableMyBoard.setBackgroundColor(Color.BLACK);
				table.setBackgroundColor(Color.GRAY);
			}
		} else if (checkshotM == 1 && turn == 2) {

			checkhitcell();
			turn = 1;
			// mytest.setValue(turn);
			tableMyBoard.setBackgroundColor(Color.BLACK);
			table.setBackgroundColor(Color.GRAY);
		}

	}

	public void checkhitcell() {
		while (checkshotM == 1) {
			int row = rnd.nextInt(10);
			int col = rnd.nextInt(10);

			checkshotM = myboard[row][col];
			checkhitsM = myboardhits[row][col];

			if (checkshotM == 0 && turn == 2) {

				if (checkhitsM == 0) {
					if (soundOnOff == "on") {
					sp.play(miss, 1, 1, 0, 0, 1);
					}
					TableRow tableRow = (TableRow) tableMyBoard.getChildAt(row);
					View cellView = tableRow.getChildAt(col);
					cellView.setBackgroundResource(R.drawable.btn_blue_matte_shot);
				
					myboard[row][col] = 1;
				} else if (checkhitsM == 1) {
					
					TableRow tableRow = (TableRow) tableMyBoard.getChildAt(row);
					View cellView = tableRow.getChildAt(col);
					
					myboard[row][col] = 2;
					nummyhits++;
				}
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*switch (item.getItemId()) {
		case R.id.menu_Help:

			 Intent in = new Intent(this, HelpActivity.class);
			 startActivity(in);
			// return true;
			break;

		case R.id.menu_BG:
			
			locale = "bg";
			GenerateRandomShips(andrboardhits, androidShipsLocations,
					androidWords);
			GenerateRandomShips(myboardhits, myShipsLocations, myWords);
			newGame();
			Newgame.setText("Нова игра");
			Izberi.setText("Избери дума");
			textShots.setText("Изстрела");
			textLevel.setText("Ниво");
			NumOfShots = 0;
			String n = Integer.toString(NumOfShots);
			Shots.setText(n);
			GameLevel = 1;
			String l = Integer.toString(GameLevel);
			Level.setText(l);
			// return true;
			break;

		case R.id.menu_RU:
			locale = "ru";
			GenerateRandomShips(andrboardhits, androidShipsLocations,
					androidWords);
			GenerateRandomShips(myboardhits, myShipsLocations, myWords);
			newGame();
			Newgame.setText("Новая игра");
			Izberi.setText("Выберите слово");
			textShots.setText("Выстрелов");
			textLevel.setText("Уровень");
			NumOfShots = 0;
			String num = Integer.toString(NumOfShots);
			Shots.setText(num);
			GameLevel = 1;
			String le = Integer.toString(GameLevel);
			Level.setText(le);
			// return true;
			break;

		case R.id.menu_EN:
			locale = "en";
			GenerateRandomShips(andrboardhits, androidShipsLocations,
					androidWords);
			GenerateRandomShips(myboardhits, myShipsLocations, myWords);
			newGame();
			Newgame.setText("New game");
			Izberi.setText("Choose word");
			textShots.setText("Shots");
			textLevel.setText("Level");
			NumOfShots = 0;
			String numb = Integer.toString(NumOfShots);
			Shots.setText(numb);
			GameLevel = 1;
			String lev = Integer.toString(GameLevel);
			Level.setText(lev);
			// return true;
			break;
		case R.id.soundOnitem:
			
			
				soundOnOff = "on";

			
			break;
		case R.id.soundOffitem:
		
			
				soundOnOff = "of";

			
			break;
		}
	//crash here //  ((MyApplication) getApplication()).setLanguage(locale);
		Configuration config = getBaseContext().getResources()
				.getConfiguration();
		Locale localeNew = new Locale(locale);
		Locale.setDefault(localeNew);
		config.locale = localeNew;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
*/
		return super.onOptionsItemSelected(item);

		// return

	}

	public int GetSoundForLetter(String lettershow) {
		int puttlett = 0;
		allLetters allLett = allLetters.valueOf(lettershow);
		if (soundOnOff == "on") {
		
		switch (allLett) {

		default:
			break;
		case а:

			if (locale.equals("ru")) {
				sp.play(ru_a, 1, 1, 0, 0, 1);
			} else
				sp.play(a, 1, 1, 0, 0, 1);
			break;
		case б:

			if (locale.equals("ru")) {
				sp.play(ru_b, 1, 1, 0, 0, 1);
			} else
				sp.play(b, 1, 1, 0, 0, 1);
			break;
		case в:

			if (locale.equals("ru")) {
				sp.play(ru_v, 1, 1, 0, 0, 1);
			} else
				sp.play(v, 1, 1, 0, 0, 1);
			break;
		case г:

			if (locale.equals("ru")) {
				sp.play(ru_g, 1, 1, 0, 0, 1);
			} else
				sp.play(g, 1, 1, 0, 0, 1);
			break;
		case д:

			if (locale.equals("ru")) {
				sp.play(ru_d, 1, 1, 0, 0, 1);
			} else
				sp.play(d, 1, 1, 0, 0, 1);
			break;
		case е:

			if (locale.equals("ru")) {
				sp.play(ru_e, 1, 1, 0, 0, 1);
			} else
				sp.play(e, 1, 1, 0, 0, 1);
			break;
		case ж:

			if (locale.equals("ru")) {
				sp.play(ru_j, 1, 1, 0, 0, 1);
			} else
				sp.play(j, 1, 1, 0, 0, 1);
			break;
		case з:

			if (locale.equals("ru")) {
				sp.play(ru_z, 1, 1, 0, 0, 1);
			} else
				sp.play(z, 1, 1, 0, 0, 1);
			break;
		case и:

			if (locale.equals("ru")) {
				sp.play(ru_i, 1, 1, 0, 0, 1);
			} else
				sp.play(i, 1, 1, 0, 0, 1);
			break;

		case й:

			if (locale.equals("ru")) {
				sp.play(ru_ii, 1, 1, 0, 0, 1);
			} else
				sp.play(ii, 1, 1, 0, 0, 1);
			break;
		case к:

			if (locale.equals("ru")) {
				sp.play(ru_k, 1, 1, 0, 0, 1);
			} else
				sp.play(k, 1, 1, 0, 0, 1);
			break;
		case л:

			if (locale.equals("ru")) {
				sp.play(ru_l, 1, 1, 0, 0, 1);
			} else
				sp.play(l, 1, 1, 0, 0, 1);
			break;
		case м:

			if (locale.equals("ru")) {
				sp.play(ru_m, 1, 1, 0, 0, 1);
			} else
				sp.play(m, 1, 1, 0, 0, 1);
			break;
		case н:

			if (locale.equals("ru")) {
				sp.play(ru_n, 1, 1, 0, 0, 1);
			} else
				sp.play(n, 1, 1, 0, 0, 1);
			break;
		case о:

			if (locale.equals("ru")) {
				sp.play(ru_o, 1, 1, 0, 0, 1);
			} else
				sp.play(o, 1, 1, 0, 0, 1);
			break;
		case п:

			if (locale.equals("ru")) {
				sp.play(ru_p, 1, 1, 0, 0, 1);
			} else
				sp.play(p, 1, 1, 0, 0, 1);
			break;
		case р:

			if (locale.equals("ru")) {
				sp.play(ru_r, 1, 1, 0, 0, 1);
			} else
				sp.play(r, 1, 1, 0, 0, 1);
			break;
		case с:

			if (locale.equals("ru")) {
				sp.play(ru_s, 1, 1, 0, 0, 1);
			} else
				sp.play(s, 1, 1, 0, 0, 1);
			break;
		case т:

			if (locale.equals("ru")) {
				sp.play(ru_t, 1, 1, 0, 0, 1);
			} else
				sp.play(t, 1, 1, 0, 0, 1);
			break;
		case у:

			if (locale.equals("ru")) {
				sp.play(ru_u, 1, 1, 0, 0, 1);
			} else
				sp.play(u, 1, 1, 0, 0, 1);
			break;
		case ф:

			if (locale.equals("ru")) {
				sp.play(ru_f, 1, 1, 0, 0, 1);
			} else
				sp.play(f, 1, 1, 0, 0, 1);
			break;
		case х:

			if (locale.equals("ru")) {
				sp.play(ru_h, 1, 1, 0, 0, 1);
			} else
				sp.play(h, 1, 1, 0, 0, 1);
			break;
		case ц:

			if (locale.equals("ru")) {
				sp.play(ru_c, 1, 1, 0, 0, 1);
			} else
				sp.play(c, 1, 1, 0, 0, 1);
			break;
		case ч:

			if (locale.equals("ru")) {
				sp.play(ru_ch, 1, 1, 0, 0, 1);
			} else
				sp.play(ch, 1, 1, 0, 0, 1);
			break;
		case ш:

			if (locale.equals("ru")) {
				sp.play(ru_sh, 1, 1, 0, 0, 1);
			} else
				sp.play(sh, 1, 1, 0, 0, 1);
			break;
		case щ:

			if (locale.equals("ru")) {
				sp.play(ru_sht, 1, 1, 0, 0, 1);
			} else
				sp.play(sht, 1, 1, 0, 0, 1);
			break;
		case ъ:

			if (locale.equals("ru")) {
				sp.play(ru_y, 1, 1, 0, 0, 1);
			} else
				sp.play(y, 1, 1, 0, 0, 1);
			break;

		case я:

			if (locale.equals("ru")) {
				sp.play(ru_q, 1, 1, 0, 0, 1);
			} else
				sp.play(q, 1, 1, 0, 0, 1);
			break;
		case a:

			sp.play(en_a, 1, 1, 0, 0, 1);
			break;
		case b:

			sp.play(en_b, 1, 1, 0, 0, 1);
			break;
		case c:

			sp.play(en_c, 1, 1, 0, 0, 1);
			break;
		case d:

			sp.play(en_d, 1, 1, 0, 0, 1);
			break;
		case e:

			sp.play(en_e, 1, 1, 0, 0, 1);
			break;
		case f:

			sp.play(en_f, 1, 1, 0, 0, 1);
			break;
		case g:

			sp.play(en_g, 1, 1, 0, 0, 1);
			break;
		case h:

			sp.play(en_h, 1, 1, 0, 0, 1);
			break;
		case i:

			sp.play(en_i, 1, 1, 0, 0, 1);
			break;
		case j:

			sp.play(en_j, 1, 1, 0, 0, 1);
			break;

		case k:

			sp.play(en_k, 1, 1, 0, 0, 1);
			break;
		case l:

			sp.play(en_l, 1, 1, 0, 0, 1);
			break;
		case m:

			sp.play(en_m, 1, 1, 0, 0, 1);
			break;
		case n:

			sp.play(en_n, 1, 1, 0, 0, 1);
			break;
		case o:

			sp.play(en_o, 1, 1, 0, 0, 1);
			break;
		case p:

			sp.play(en_p, 1, 1, 0, 0, 1);
			break;
		case q:

			sp.play(en_q, 1, 1, 0, 0, 1);
			break;
		case r:

			sp.play(en_r, 1, 1, 0, 0, 1);
			break;
		case s:

			sp.play(en_s, 1, 1, 0, 0, 1);
			break;
		case t:

			sp.play(en_t, 1, 1, 0, 0, 1);
			break;
		case u:

			sp.play(en_u, 1, 1, 0, 0, 1);
			break;
		case v:

			sp.play(en_v, 1, 1, 0, 0, 1);
			break;
		case w:

			sp.play(en_w, 1, 1, 0, 0, 1);
			break;
		case x:

			sp.play(en_x, 1, 1, 0, 0, 1);
			break;
		case y:

			sp.play(en_y, 1, 1, 0, 0, 1);
			break;
		case z:

			sp.play(en_z, 1, 1, 0, 0, 1);
			break;

		}
		}

		return puttlett;
	}

	public int GetImageForLetter(String lettershow) {
		int puttlett = 0;
		allLetters allLett = allLetters.valueOf(lettershow);
		switch (allLett) {

		default:
			break;
		case а:
			puttlett = R.drawable.a;

			break;
		case б:
			puttlett = R.drawable.b;

			break;
		case в:
			puttlett = R.drawable.v;

			break;
		case г:
			puttlett = R.drawable.g;

			break;
		case д:
			puttlett = R.drawable.d;

			break;
		case е:
			puttlett = R.drawable.e;

			break;
		case ж:
			puttlett = R.drawable.j;

			break;
		case з:
			puttlett = R.drawable.z;

			break;
		case и:
			puttlett = R.drawable.i;

			break;

		case й:
			puttlett = R.drawable.ii;

			break;
		case к:
			puttlett = R.drawable.k;

			break;
		case л:
			puttlett = R.drawable.l;

			break;
		case м:
			puttlett = R.drawable.m;

			break;
		case н:
			puttlett = R.drawable.n;

			break;
		case о:
			puttlett = R.drawable.o;

			break;
		case п:
			puttlett = R.drawable.p;

			break;
		case р:
			puttlett = R.drawable.r;

			break;
		case с:
			puttlett = R.drawable.s;

			break;
		case т:
			puttlett = R.drawable.t;
			break;

		case у:
			puttlett = R.drawable.u;

			break;
		case ф:
			puttlett = R.drawable.f;

			break;
		case х:
			puttlett = R.drawable.h;

			break;
		case ц:
			puttlett = R.drawable.c;

			break;
		case ч:
			puttlett = R.drawable.ch;

			break;
		case ш:
			puttlett = R.drawable.sh;

			break;
		case щ:
			puttlett = R.drawable.sht;

			break;
		case ъ:
			puttlett = R.drawable.y;

			break;

		case я:
			puttlett = R.drawable.q;

			break;
		case a:
			puttlett = R.drawable.a_e;

			break;
		case b:
			puttlett = R.drawable.b_e;

			break;
		case c:
			puttlett = R.drawable.c_e;

			break;
		case d:
			puttlett = R.drawable.d_e;

			break;
		case e:
			puttlett = R.drawable.e_e;

			break;
		case f:
			puttlett = R.drawable.f_e;

			break;
		case g:
			puttlett = R.drawable.g_e;

			break;
		case h:
			puttlett = R.drawable.h_e;

			break;
		case i:
			puttlett = R.drawable.i_e;

			break;
		case j:
			puttlett = R.drawable.j_e;

			break;

		case k:
			puttlett = R.drawable.k_e;

			break;
		case l:
			puttlett = R.drawable.l_e;

			break;
		case m:
			puttlett = R.drawable.m_e;

			break;
		case n:
			puttlett = R.drawable.n_e;

			break;
		case o:
			puttlett = R.drawable.o_e;

			break;
		case p:
			puttlett = R.drawable.p_e;

			break;
		case q:
			puttlett = R.drawable.q_e;

			break;
		case r:
			puttlett = R.drawable.r_e;

			break;
		case s:
			puttlett = R.drawable.s_e;

			break;
		case t:
			puttlett = R.drawable.t_e;

			break;
		case u:
			puttlett = R.drawable.u_e;

			break;
		case v:
			puttlett = R.drawable.v_e;

			break;
		case w:
			puttlett = R.drawable.w_e;

			break;
		case x:
			puttlett = R.drawable.x_e;

		case y:
			puttlett = R.drawable.y_e;
			break;

		case z:
			puttlett = R.drawable.z_e;

			break;

		}
		
		return puttlett;
	}

	public void newGame() {
		turn = 1;
		nummyhits = 0;
		numandrhits = 0;

		table = (TableLayout) findViewById(R.id.andrBoard);
		for (int i = 0; i < 10; i++) {
			TableRow tableRow = (TableRow) table.getChildAt(i);
			for (int j = 0; j < 10; j++) {
				View cellView = tableRow.getChildAt(j);
				andrboard[i][j] = 0;
				andrboardhits[i][j] = 0;
				cellView.setBackgroundResource(R.drawable.btn_blue_matte);
				

			}
		}

		tableMyBoard = (TableLayout) findViewById(R.id.myBoard);
		for (int r = 0; r < 10; r++) {
			TableRow tableRow2 = (TableRow) tableMyBoard.getChildAt(r);
			for (int c = 0; c < 10; c++) {
				View cellView2 = tableRow2.getChildAt(c);
				myboard[r][c] = 0;
				myboardhits[r][c] = 0;
				cellView2.setBackgroundResource(R.drawable.btn_blue_matte);
			}
		}
		tableMyBoard.setBackgroundColor(Color.BLACK);
		table.setBackgroundColor(Color.BLACK);
		GenerateRandomShips(andrboardhits, androidShipsLocations, androidWords);
		GenerateRandomShips(myboardhits, myShipsLocations, myWords);

		VisualizeShips();

	}

	class NewThread extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			for (int i = 0; i < 3; i++) {
				if (selectedWord == androidWords[i]) {
					if (soundOnOff == "on") {
					sp.play(boom, 1, 1, 0, 0, 1);
					}

					for (int j = 0; j < 4 - i; j++) {
						String letter = selectedWord.substring(j, j + 1);
						GetSoundForLetter(letter);

						// publishProgress(letterResource);
						// cellView.setBackgroundResource(letterResource);

						// GetSoundForLetter(letter);

						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
			return null;
		}

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {

		case R.id.NewGame: {

			newGame();
			GameLevel = 1;
			String l = Integer.toString(GameLevel);
			Level.setText(l);
			NumOfShots = 0;
			String numb = Integer.toString(NumOfShots);
			Shots.setText(numb);

		}

			break;

		case R.id.izberi: {
			String[] items = null;
			if (locale.equals("ru")) {
				items = view.getResources().getStringArray(R.array.dumi2);
			} else if (locale.equals("bg")) {
				items = view.getResources().getStringArray(R.array.dumi1);
			} else if (locale.equals("en")) {
				items = view.getResources().getStringArray(R.array.dumi3);
			} else
				items = view.getResources().getStringArray(R.array.dumi);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					GameActivity.this, android.R.layout.simple_list_item_1,
					items);

			new AlertDialog.Builder(GameActivity.this).setTitle(" ")// (R.string.choose_word)
					.setAdapter(adapter, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								int which) {
							turn = 2;

							table = (TableLayout) findViewById(R.id.andrBoard);
							String n = Integer.toString(NumOfShots);
							Shots.setText(n);

							switch (which) {

							case 0:
								if (locale.equals("ru")) {
									selectedWord = "не";
								} else if (locale.equals("bg")) {
									selectedWord = "ад";

								}

								else
									selectedWord = "be";

								break;
							case 1:
								if (locale.equals("ru")) {
									selectedWord = "он";
								} else if (locale.equals("bg")) {
									selectedWord = "аз";
								}

								else
									selectedWord = "of";

								break;
							case 2:
								if (locale.equals("ru")) {
									selectedWord = "на";
								} else if (locale.equals("bg")) {
									selectedWord = "ас";
								}

								else
									selectedWord = "in";

								break;

							case 3:
								if (locale.equals("ru")) {
									selectedWord = "по";
								} else if (locale.equals("bg")) {
									selectedWord = "да";
								}

								else
									selectedWord = "to";

								break;
							case 4:
								if (locale.equals("ru")) {
									selectedWord = "до";
								} else if (locale.equals("bg")) {
									selectedWord = "до";
								}

								else
									selectedWord = "it";

								break;
							case 5:
								if (locale.equals("ru")) {
									selectedWord = "но";
								} else if (locale.equals("bg")) {
									selectedWord = "за";
								}

								else
									selectedWord = "he";

								break;
							case 6:
								if (locale.equals("ru")) {
									selectedWord = "из";
								} else if (locale.equals("bg")) {
									selectedWord = "на";
								}

								else
									selectedWord = "on";

								break;
							case 7:
								if (locale.equals("ru")) {
									selectedWord = "за";
								} else if (locale.equals("bg")) {
									selectedWord = "не";
								}

								else
									selectedWord = "do";

								break;
							case 8:
								if (locale.equals("ru")) {
									selectedWord = "от";
								} else if (locale.equals("bg")) {
									selectedWord = "от";
								}

								else
									selectedWord = "at";

								break;
							case 9:
								if (locale.equals("ru")) {
									selectedWord = "да";
								} else if (locale.equals("bg")) {
									selectedWord = "по";
								}

								else
									selectedWord = "we";

								break;
							case 10:
								if (locale.equals("ru")) {
									selectedWord = "что";
								} else if (locale.equals("bg")) {
									selectedWord = "бар";
								}

								else
									selectedWord = "are";

								break;
							case 11:
								if (locale.equals("ru")) {
									selectedWord = "тот";
								} else if (locale.equals("bg")) {
									selectedWord = "бор";
								}

								else
									selectedWord = "the";

								break;
							case 12:
								if (locale.equals("ru")) {
									selectedWord = "ето";
								} else if (locale.equals("bg")) {
									selectedWord = "вар";
								}

								else
									selectedWord = "and";

								break;
							case 13:
								if (locale.equals("ru")) {
									selectedWord = "как";
								} else if (locale.equals("bg")) {
									selectedWord = "вир";
								}

								else
									selectedWord = "for";

								break;
							case 14:
								if (locale.equals("ru")) {
									selectedWord = "она";
								} else if (locale.equals("bg")) {
									selectedWord = "дар";
								}

								else
									selectedWord = "you";

								break;
							case 15:
								if (locale.equals("ru")) {
									selectedWord = "мир";
								} else if (locale.equals("bg")) {
									selectedWord = "мир";
								}

								else
									selectedWord = "say";

								break;
							case 16:
								if (locale.equals("ru")) {
									selectedWord = "так";
								} else if (locale.equals("bg")) {
									selectedWord = "нар";
								}

								else
									selectedWord = "she";

								break;
							case 17:
								if (locale.equals("ru")) {
									selectedWord = "уже";
								} else if (locale.equals("bg")) {
									selectedWord = "пир";
								}

								else
									selectedWord = "get";

								break;
							case 18:
								if (locale.equals("ru")) {
									selectedWord = "для";
								} else if (locale.equals("bg")) {
									selectedWord = "пор";
								}

								else
									selectedWord = "one";

								break;
							case 19:
								if (locale.equals("ru")) {
									selectedWord = "вот";
								} else if (locale.equals("bg")) {
									selectedWord = "цар";
								}

								else
									selectedWord = "out";

								break;
							case 20:
								if (locale.equals("ru")) {
									selectedWord = "один";
								} else if (locale.equals("bg")) {
									selectedWord = "дете";
								}

								else
									selectedWord = "have";

								break;
							case 21:
								if (locale.equals("ru")) {
									selectedWord = "себя";
								} else if (locale.equals("bg")) {
									selectedWord = "елха";
								}

								else
									selectedWord = "that";

								break;
							case 22:
								if (locale.equals("ru")) {
									selectedWord = "если";
								} else if (locale.equals("bg")) {
									selectedWord = "жена";
								}

								else
									selectedWord = "this";

								break;
							case 23:
								if (locale.equals("ru")) {
									selectedWord = "рука";
								} else if (locale.equals("bg")) {
									selectedWord = "кола";
								}

								else
									selectedWord = "what";

								break;
							case 24:
								if (locale.equals("ru")) {
									selectedWord = "даже";
								} else if (locale.equals("bg")) {
									selectedWord = "лале";
								}

								else
									selectedWord = "make";

								break;
							case 25:
								if (locale.equals("ru")) {
									selectedWord = "дело";
								} else if (locale.equals("bg")) {
									selectedWord = "мида";
								}

								else
									selectedWord = "know";

								break;
							case 26:
								if (locale.equals("ru")) {
									selectedWord = "глаз";
								} else if (locale.equals("bg")) {
									selectedWord = "небе";
								}

								else
									selectedWord = "year";

								break;
							case 27:
								if (locale.equals("ru")) {
									selectedWord = "надо";
								} else if (locale.equals("bg")) {
									selectedWord = "нива";
								}

								else
									selectedWord = "when";

								break;
							case 28:
								if (locale.equals("ru")) {
									selectedWord = "идти";
								} else if (locale.equals("bg")) {
									selectedWord = "поле";
								}

								else
									selectedWord = "come";

								break;
							case 29:
								if (locale.equals("ru")) {
									selectedWord = "тоже";
								} else if (locale.equals("bg")) {
									selectedWord = "чаша";
								} else
									selectedWord = "more";

								break;

							}
							// tts.speak(selectedWord, TextToSpeech.QUEUE_FLUSH,
							// null);
							dialog.dismiss();
							// Check if the word exist and if so show the
							// letters
							// selectedWord = "test";
							// assign the selected word

							checkIfSamePopulateLettersSounds(dialog);

						}

						private void checkIfSamePopulateLettersSounds(

						DialogInterface dialog) {
							NumOfShots++;
							String n = Integer.toString(NumOfShots);
							Shots.setText(n);
							for (int i = 0; i < 3; i++) {
								if (selectedWord == androidWords[i]) {
									NewThread check = new NewThread();
									check.execute();
									
									int row = androidShipsLocations[i].getRow();
									int col = androidShipsLocations[i]
											.getColumn();
									int direction = androidShipsLocations[i]
											.getDirection();
									// Show the letters, mark the cells as hit

									for (int j = 0; j < 4 - i; j++) {
										String letter = selectedWord.substring(
												j, j + 1);
										int letterResource = GetImageForLetter(letter);

										if (direction == 0) {
											if (andrboard[row][col + j] != 2) {
												andrboard[row][col + j] = 2;
												// Show the letter in cell row,
												// col+j
												TableRow tableRow = (TableRow) table
														.getChildAt(row);
												View cellView = tableRow
														.getChildAt(col + j);
												cellView.setBackgroundResource(letterResource);
												cellView.startAnimation(animRotate);
												

												// GetImageForLetter(letter);
												secondsPopup++;
                                                seconds++;
												numandrhits++;
												// Check if all ships are found
												if (numandrhits == 9) {

													if (GameLevel < 6) {
														if (soundOnOff == "on") {
														sp.play(up, 1, 1, 0, 0,
																1);
														sp.play(applause, 1, 1,
																0, 0, 1);
														}

														turn = 3;
														GameLevel++;
														String l = Integer
																.toString(GameLevel);
														Level.setText(l);

														table.setBackgroundColor(Color.RED);

														popupNextLevelNew();
													} else {
														turn = 3;
														if (soundOnOff == "on") {
														sp.play(up, 1, 1, 0, 0,
																1);
														sp.play(applause, 1, 1,
																0, 0, 1);
														}
														popupNextLevelNew();
														GameLevel = 1;

													}
												}

											}

										} else if (direction == 1) {
											if (andrboard[row + j][col] != 2) {
												andrboard[row + j][col] = 2;
												// Show the letter in cell
												// row+j, col

												TableRow tableRow = (TableRow) table
														.getChildAt(row + j);
												View cellView = tableRow
														.getChildAt(col);
												cellView.setBackgroundResource(letterResource);
												cellView.startAnimation(animRotate);
												numandrhits++;

												// Check if all ships are found
												if (numandrhits == 9) {

													if (GameLevel < 6) {
														if (soundOnOff == "on") {
														sp.play(up, 1, 1, 0, 0,
																1);
														sp.play(applause, 1, 1,
																0, 0, 1);
														}

														turn = 3;
														GameLevel++;
														String l = Integer
																.toString(GameLevel);
														Level.setText(l);

														table.setBackgroundColor(Color.RED);
														new Handler().postDelayed(new Runnable() {

															@Override
															public void run() {

																popupNextLevelNew();

															}
														}, secondsPopup*2000);
														
													} else {
														turn = 3;
														if (soundOnOff == "on") {
														sp.play(up, 1, 1, 0, 0,
																1);
														sp.play(applause, 1, 1,
																0, 0, 1);
														}
														popupNextLevelNew();
														GameLevel = 1;

													}

												}
											}

										}
										dialog.dismiss();

										/*
										 * try { Thread.sleep(2000); } catch
										 * (InterruptedException e) { // TODO
										 * Auto-generated catch block
										 * e.printStackTrace(); }
										 */
									}
																		
								}

								

							}
							if (soundOnOff == "on") {
							sp.play(down, 1, 1, 0, 0, 1);
							}
							delay( seconds);
							
						}
					}).create().show();

			/*
			 * openAlert(v); nazad.setVisibility(View.INVISIBLE);
			 * napred.setVisibility(View.INVISIBLE);
			 */
			// tts.speak(selectedWord,TextToSpeech.QUEUE_FLUSH, null);
		}
			break;

		}
	}

}
