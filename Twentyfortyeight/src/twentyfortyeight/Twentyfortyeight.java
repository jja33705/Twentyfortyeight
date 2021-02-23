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
		gameBoard.setBounds(80, 140, 402, 401);
		this.add(gameBoard);
		
		ScoreBoard scoreBoard = new ScoreBoard();
		scoreBoard.setBounds(80, 20, 400, 100);
		this.add(scoreBoard);
		
		JButton startButton = new JButton("����");
		startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		startButton.setBounds(490, 30, 80, 80);
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
	class GameBoard extends JPanel {  //���Ӻ���
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
			//4*4 �׸��� �׸�
			
			if(numbers == null) return;  //numbers�迭�� null�ϋ�(ó�� ��������) ����
				
			for(int i = 0; i < SIZE; i++) {
				for(int j = 0; j < SIZE; j++) {
					if(numbers[i][j] != 0) {
						Color rectColor = setRectColor(numbers[i][j]); //���ڿ� ���� ��ĥ�Ǵ� �� �ٸ��� ����
						g.setColor(rectColor);
						g.fillRoundRect(j * 100 + 1, i * 100 + 1, 99, 99, 10, 10);
						
						int fontOffset = setFontOffset(numbers[i][j]);  //���� �ڸ����� ���� ���ڰ� ������ ��ġ ����
						
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
	
	class ScoreBoard extends JPanel {   //��� ����� ���ھ�
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
		
		for(int i = 0; i < 2; ) { //���۹�ư�� �������� �����ϰ� �ΰ� ��ġ�� 2 ����.
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
		case KeyEvent.VK_UP:     //���� 
			for(int i = 0; i < SIZE; i++) {
				ArrayList<Integer> list = new ArrayList<>();
				for(int j = 0; j < SIZE; j++) {   //����Ʈ�� ����
					if(numbers[j][i] != 0) {
						list.add(numbers[j][i]);
					}
				}
				for(int j = 0; j < list.size()-1; j++) { //���� �� ��ħ
					if(list.get(j).equals(list.get(j+1))) {
						score += list.get(j) + list.get(j+1);
						list.set(j, list.get(j)+list.get(j+1));
						list.remove(j+1);
					}
				}
				for(int j = 0; j < SIZE; j++) {   //�迭�� ����
					if(j<list.size()) {
						newBoard[j][i] = list.get(j);
					} else {
						newBoard[j][i] = 0;
					}
				}
			}
			break;
		
		case KeyEvent.VK_DOWN:   //�Ʒ���
			for(int i = 0; i < SIZE; i++) {
				ArrayList<Integer> list = new ArrayList<>();
				for(int j = SIZE-1; j >= 0; j--) {   //����Ʈ�� ����
					if(numbers[j][i] != 0) {
						list.add(numbers[j][i]);
					}
				}
				for(int j = 0; j < list.size()-1; j++) { //���� �� ��ħ
					if(list.get(j).equals(list.get(j+1))) {
						score += list.get(j) + list.get(j+1);
						list.set(j, list.get(j)+list.get(j+1));
						list.remove(j+1);
					}
				}
				for(int j = SIZE-1, listIdx = 0; j >= 0; j--, listIdx++) {   //�迭�� ����
					if(listIdx<list.size()) {
						newBoard[j][i] = list.get(listIdx);
					} else {
						newBoard[j][i] = 0;
					}
				}
			}
			break;
		
		case KeyEvent.VK_RIGHT:  //����������
			for(int i = 0; i < SIZE; i++) {
				ArrayList<Integer> list = new ArrayList<>();
				for(int j = SIZE-1; j >= 0; j--) {   //����Ʈ�� ����
					if(numbers[i][j] != 0) {
						list.add(numbers[i][j]);
					}
				}
				for(int j = 0; j < list.size()-1; j++) { //���� �� ��ħ
					if(list.get(j).equals(list.get(j+1))) {
						score += list.get(j) + list.get(j+1);
						list.set(j, list.get(j)+list.get(j+1));
						list.remove(j+1);
					}
				}
				for(int j = SIZE-1, listIdx = 0; j >= 0; j--, listIdx++) {   //�迭�� ����
					if(listIdx < list.size()) {
						newBoard[i][j] = list.get(listIdx);
					} else {
						newBoard[i][j] = 0;
					}
				}
			}
			break;
		
		case KeyEvent.VK_LEFT: //��������
			for(int i = 0; i < SIZE; i++) {
				ArrayList<Integer> list = new ArrayList<>();
				for(int j = 0; j < SIZE; j++) {   //����Ʈ�� ����
					if(numbers[i][j] != 0) {
						list.add(numbers[i][j]);
					}
				}
				for(int j = 0; j < list.size()-1; j++) { //���� �� ��ħ
					if(list.get(j).equals(list.get(j+1))) {
						score += list.get(j) + list.get(j+1);
						list.set(j, list.get(j)+list.get(j+1));
						list.remove(j+1);
					}
				}
				for(int j = 0; j < SIZE; j++) {   //�迭�� ����
					if(j<list.size()) {
						newBoard[i][j] = list.get(j);
					} else {
						newBoard[i][j] = 0;
					}
				}
			}
			break;
			default: return;    //�Էµ� Ű�� ����Ű�� �ƴϸ� ����
		}
		
		if(isMoved(numbers, newBoard)) {  
			numbers = newBoard;
			this.repaint();
			
			Timer timer = new Timer();                
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					makeNumber();
					isGameOver = checkGameOver();           //���̻� �̵� �� �� �ִ� ��찡 ������ �˻�
					repaint();
					timer.cancel();
				}
			};
			timer.schedule(task, 170);              //0.17������ �Ŀ� ���ο� �� �׷���....
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	private Color setRectColor(int num) {   //���ڿ� ���� ��ĥ�Ǵ� ���� �ٸ��� ��
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
	
	private int setFontOffset(int num) {  //���� �ڸ����� ���� ���ڰ� �׷����� ��ġ�� ��������.
		if(num < 10) return 0;
		else if(num < 100) return 11;
		else if(num < 1000) return 22;
		else return 33;
	}
	
	private void makeNumber() {  //�����ϰ� 2�� 4�� �ϳ��� ���ں��� ����
		int randomNumber = Math.random()<0.8 ? 2 : 4; 
		while(true) {
			int random = (int)(Math.random() * SIZE * SIZE);
			if(numbers[random/SIZE][random%SIZE] == 0) {
				numbers[random/SIZE][random%SIZE] = randomNumber;
				return;
			}
		}
	}
	
	private boolean isMoved(int[][] board, int[][] newBoard) {  //�ٲ�� �ִ��� üũ
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				if(board[i][j] != newBoard[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkGameOver() {   //������ �������� üũ
		for(int i = 0; i < SIZE; i++) {  //��ĭ�� �ϳ��� ������ �� ����.
			for(int j = 0; j< SIZE; j++) {
				if(numbers[i][j] == 0) {
					return false;
				}
			}
		}
		
		for(int i = 0; i < SIZE; i++) {  //���ο� ��ĥ �� �ִ� �� �ִ��� �˻�
			for(int j = 0; j < SIZE - 1; j++) {
				if(numbers[i][j] == numbers[i][j+1]) {
					return false;
				}
			}
		}
		
		for(int i = 0; i < SIZE; i++) {  //���ο� ��ĥ �� �ִ� �� �ִ��� �˻�
			for(int j = 0; j < SIZE - 1; j++) {
				if(numbers[j][i] == numbers[j+1][i]) {
					return false;
				}
			}
		}
		
		return true;
	}
}
