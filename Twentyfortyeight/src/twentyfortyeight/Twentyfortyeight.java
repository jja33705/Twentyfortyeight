package twentyfortyeight;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Twentyfortyeight extends JFrame implements ActionListener, KeyListener{
	public static final int SIZE = 4;
	
	private int[][] numbers;
	
	private int score = 0;
	
	private boolean isGameOver = false;
	
	public Twentyfortyeight() {
		this.setSize(600, 600);
		this.setTitle("2048");
		this.setLayout(null);
		
		GameBoard gameBoard = new GameBoard();
		gameBoard.setBounds(100, 140, 402, 401);
		this.add(gameBoard);
		
		ScoreBoard scoreBoard = new ScoreBoard();
		scoreBoard.setBounds(100, 20, 400, 100);
		this.add(scoreBoard);
		
		JButton startButton = new JButton("시작");
		startButton.setBounds(510, 40, 60, 60);
		startButton.setFocusable(false);
		startButton.setBackground(Color.WHITE);
		startButton.addActionListener(this);
		this.add(startButton);
		
		this.addKeyListener(this);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Twentyfortyeight();
	}
	class GameBoard extends JPanel {  //게임보드
		public GameBoard() {
			this.setBackground(new Color(200, 200, 200));
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, 400, 400);
			g.drawLine(100, 0, 100, 400);
			g.drawLine(200, 0, 200, 400);
			g.drawLine(300, 0, 300, 400);
			g.drawLine(0, 100, 400, 100);
			g.drawLine(0, 200, 400, 200);
			g.drawLine(0, 300, 400, 300);
			//4*4 그리드 그림
			
			if(numbers == null) return;  //numbers배열이 null일떄(처음 켰을때는) 리턴
				
			for(int i = 0; i < SIZE; i++) {
				for(int j = 0; j < SIZE; j++) {
					if(numbers[i][j] != 0) {
						Color rectColor = setRectColor(numbers[i][j]); //숫자에 따라 색칠되는 색 다르게 지정
						g.setColor(rectColor);
						g.fillRoundRect(j * 100 + 1, i * 100 + 1, 99, 99, 10, 10);
						
						int fontOffset = setFontOffset(numbers[i][j]);  //숫자 자릿수에 따라 숫자가 써지는 위치 조정
						
						g.setColor(Color.BLACK);
						g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
						g.drawString(String.valueOf(numbers[i][j]), j * 100 +39 - fontOffset, i * 100 + 61);
					}
				}
			}
			
			if(isGameOver) {
				g.setColor(Color.RED);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
				g.drawString("GameOver", 20, 200);
			}
			
		}
	}
	
	class ScoreBoard extends JPanel {   //상단 제목과 스코어
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 400, 100);
			g.setColor(Color.BLACK);
			g.drawLine(200, 0, 200, 100);
			g.drawLine(200, 50, 400, 50);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
			g.drawString("Make 2048", 12, 65);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
			g.drawString("Score", 260, 40);
			g.drawString(String.valueOf(score), 260, 85);
		}
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		numbers = new int[SIZE][SIZE];
		score = 0;
		
		for(int i = 0; i < 2; ) { //시작버튼이 눌렸을때 랜덤하게 두개 위치에 2 생성.
			int random = (int)(Math.random() * SIZE * SIZE);
			if(numbers[random/SIZE][random%SIZE] == 0) {
				numbers[random/SIZE][random%SIZE] = 2;
				i++;
			}
		}
		this.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(numbers == null) return;
		
		int keyCode = e.getKeyCode();
		int[][] newBoard = new int[SIZE][SIZE];
		
		switch(keyCode) {
		case KeyEvent.VK_UP:     //위로 
			for(int i = 0; i < SIZE; i++) {
				ArrayList<Integer> list = new ArrayList<>();
				for(int j = 0; j < SIZE; j++) {   //리스트에 넣음
					if(numbers[j][i] != 0) {
						list.add(numbers[j][i]);
					}
				}
				for(int j = 0; j < list.size()-1; j++) { //같은 값 합침
					if(list.get(j).equals(list.get(j+1))) {
						score += list.get(j) + list.get(j+1);
						list.set(j, list.get(j)+list.get(j+1));
						list.remove(j+1);
					}
				}
				for(int j = 0; j < SIZE; j++) {   //배열에 넣음
					if(j<list.size()) {
						newBoard[j][i] = list.get(j);
					} else {
						newBoard[j][i] = 0;
					}
				}
			}
			break;
		
		case KeyEvent.VK_DOWN:   //아래로
			for(int i = 0; i < SIZE; i++) {
				ArrayList<Integer> list = new ArrayList<>();
				for(int j = SIZE-1; j >= 0; j--) {   //리스트에 넣음
					if(numbers[j][i] != 0) {
						list.add(numbers[j][i]);
					}
				}
				for(int j = 0; j < list.size()-1; j++) { //같은 값 합침
					if(list.get(j).equals(list.get(j+1))) {
						score += list.get(j) + list.get(j+1);
						list.set(j, list.get(j)+list.get(j+1));
						list.remove(j+1);
					}
				}
				for(int j = SIZE-1, listIdx = 0; j >= 0; j--, listIdx++) {   //배열에 넣음
					if(listIdx<list.size()) {
						newBoard[j][i] = list.get(listIdx);
					} else {
						newBoard[j][i] = 0;
					}
				}
			}
			break;
		
		case KeyEvent.VK_RIGHT:  //오른쪽으로
			for(int i = 0; i < SIZE; i++) {
				ArrayList<Integer> list = new ArrayList<>();
				for(int j = SIZE-1; j >= 0; j--) {   //리스트에 넣음
					if(numbers[i][j] != 0) {
						list.add(numbers[i][j]);
					}
				}
				for(int j = 0; j < list.size()-1; j++) { //같은 값 합침
					if(list.get(j).equals(list.get(j+1))) {
						score += list.get(j) + list.get(j+1);
						list.set(j, list.get(j)+list.get(j+1));
						list.remove(j+1);
					}
				}
				for(int j = SIZE-1, listIdx = 0; j >= 0; j--, listIdx++) {   //배열에 넣음
					if(listIdx < list.size()) {
						newBoard[i][j] = list.get(listIdx);
					} else {
						newBoard[i][j] = 0;
					}
				}
			}
			break;
		
		case KeyEvent.VK_LEFT: //왼쪽으로
			for(int i = 0; i < SIZE; i++) {
				ArrayList<Integer> list = new ArrayList<>();
				for(int j = 0; j < SIZE; j++) {   //리스트에 넣음
					if(numbers[i][j] != 0) {
						list.add(numbers[i][j]);
					}
				}
				for(int j = 0; j < list.size()-1; j++) { //같은 값 합침
					if(list.get(j).equals(list.get(j+1))) {
						score += list.get(j) + list.get(j+1);
						list.set(j, list.get(j)+list.get(j+1));
						list.remove(j+1);
					}
				}
				for(int j = 0; j < SIZE; j++) {   //배열에 넣음
					if(j<list.size()) {
						newBoard[i][j] = list.get(j);
					} else {
						newBoard[i][j] = 0;
					}
				}
			}
			break;
		}
		
		if(isMoved(numbers, newBoard)) {  
			numbers = newBoard;
			this.repaint();
			
			Timer timer = new Timer();                //0.2초정도 후에 새로운 블럭 그려줌....
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					makeNumber();
					isGameOver = checkGameOver();           //더이상 이동 할 수 있는 경우가 없는지 검사
					repaint();
					timer.cancel();
				}
			};
			timer.schedule(task, 200);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	private Color setRectColor(int num) {   //숫자에 따라 색칠되는 색을 다르게 함
		switch(num) {
		case 2: return new Color(250, 243, 243);
		case 4: return new Color(250, 233, 233);
		case 8: return new Color(250, 223, 223);
		case 16: return new Color(250, 213, 213);
		case 32: return new Color(250, 203, 203);
		case 64: return new Color(250, 193, 193);
		case 128: return new Color(250, 183, 183);
		case 256: return new Color(250, 173, 173);
		case 512: return new Color(250, 163, 163);
		case 1024: return new Color(250, 153, 153);
		case 2048: return new Color(250, 143, 143);
		default: return Color.RED;
		}
	}
	
	private int setFontOffset(int num) {  //숫자 자릿수에 따라 숫자가 그려지는 위치를 조정해줌.
		if(num < 10) return 0;
		else if(num < 100) return 11;
		else if(num < 1000) return 22;
		else return 33;
	}
	
	private void makeNumber() {  //랜덤하게 2와 4중 하나의 숫자블럭을 생성
		int randomNumber = Math.random()<0.8 ? 2 : 4; 
		while(true) {
			int random = (int)(Math.random() * SIZE * SIZE);
			if(numbers[random/SIZE][random%SIZE] == 0) {
				numbers[random/SIZE][random%SIZE] = randomNumber;
				return;
			}
		}
	}
	
	private boolean isMoved(int[][] board, int[][] newBoard) {  //바뀐게 있는지 체크
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if(board[i][j] != newBoard[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkGameOver() {   //게임이 끝났는지 체크
		for(int i = 0; i < SIZE; i++) {  //빈칸이 하나라도 있으면 안 끝남.
			for(int j = 0; j< SIZE; j++) {
				if(numbers[i][j] == 0) {
					return false;
				}
			}
		}
		
		for(int i = 0; i < SIZE; i++) {  //가로에 합칠 수 있는 값 있는지 검사
			for(int j = 0; j < SIZE - 1; j++) {
				if(numbers[i][j] == numbers[i][j+1]) {
					return false;
				}
			}
		}
		
		for(int i = 0; i < SIZE; i++) {  //세로에 함칠 수 있는 값 있는지 검사
			for(int j = 0; j < SIZE - 1; j++) {
				if(numbers[j][i] == numbers[j+1][i]) {
					return false;
				}
			}
		}
		
		return true;
	}
}
