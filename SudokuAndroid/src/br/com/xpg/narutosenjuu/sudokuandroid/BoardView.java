package br.com.xpg.narutosenjuu.sudokuandroid;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class BoardView extends View {

	private int[][] board;
	private int width;
	private int height;
	private ArrayList<PointOnBoard> points;

	public BoardView(Context context) {
		super(context);
		board = new int[9][9];
		points = new ArrayList<PointOnBoard>();
	}

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		board = new int[9][9];
		points = new ArrayList<PointOnBoard>();
	}

	public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		board = new int[9][9];
		points = new ArrayList<PointOnBoard>();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		width = w / 9;
		height = h / 9;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		// Draw the numbers...

		// Define color and style for numbers
		Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(getResources().getColor(R.color.black_overlay));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height * 0.75f);
		foreground.setTextScaleX(width / height);
		foreground.setTextAlign(Paint.Align.CENTER);

		// Draw the number in the center of the tile
		FontMetrics fm = foreground.getFontMetrics();
		// Centering in X: use alignment (and X at midpoint)
		float x = width / 2;
		// Centering in Y: measure ascent/descent first
		float y = height / 2 - (fm.ascent + fm.descent) / 2;

		//draw the number and the background buttons
		Paint show = new Paint();
		show.setColor(0xff99ff99);
		
		Paint hidden = new Paint();
		hidden.setColor(0xffffffff);
		
		Paint busy = new Paint();
		busy.setColor(0xff999999);

		for (PointOnBoard point : points) {
			
			if (point.getDisplay() == PointOnBoard.SHOW) {
				
				canvas.drawRect(getRect(point.getCol(),point.getRow()), show);
				canvas.drawText("" + point.getNumber(), point.getCol() * width
						+ x, point.getRow() * height + y, foreground);
				
			} else if (point.getDisplay() == PointOnBoard.HIDDEN) {
				canvas.drawRect(getRect(point.getCol(),point.getRow()), hidden);
				canvas.drawText("" , point.getCol() * width
						+ x, point.getRow() * height + y, foreground);

			} else if (point.getDisplay() == PointOnBoard.BUSY) {
				canvas.drawRect(getRect(point.getCol(),point.getRow()), busy);
				canvas.drawText("" + point.getNumber(), point.getCol() * width
						+ x, point.getRow() * height + y, foreground);

			}
		}

		//draw the lines on the sudoku board
		Paint dark = new Paint();
		//dark.setColor(getResources().getColor(R.color.black_overlay));
		dark.setColor(0xff000000);
		dark.setStrokeWidth(5.0f);
		
		//lines
		Paint lines = new Paint();
		lines.setStrokeWidth(3.0f);
		lines.setColor(0xffff0000);
		
		for (int i = 0; i < 9; i++) {
			if(i % 3 == 0)
				continue;
			canvas.drawLine(0, i * height, getWidth(), i * height, lines);
			canvas.drawLine(i * width, 0, i * width, getHeight(), lines);
			
		}	

		for (int i = 0; i < 9; i++) {
			if (i % 3 != 0)
				continue;
			canvas.drawLine(0, i * height, getWidth(), i * height, dark);
			canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
		}

	}

	public Rect getRect(int x, int y) {
		return new Rect(x * width , y * height 
				, x * width + width,  y * height + height);
	}

	public void setBoard(int board[][]) {
		this.board = board;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setPoints(ArrayList<PointOnBoard> points) {
		this.points = points;
	}
	
	public  ArrayList<PointOnBoard> getPoints()
	{
		return points;
	}
}
