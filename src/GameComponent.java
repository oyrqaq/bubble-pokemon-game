import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GameComponent extends JComponent{
    Timer timer;
    private static final int TICKS_PER_SECOND = 1000;
    private static final int REPAINT_INTERVAL_MS = 1000 / TICKS_PER_SECOND;
    private static final long serialVersionUID = 1L;
    private JLabel label = new JLabel();
    private JFrame frame = new JFrame();
    int levelnum, waitTimer;
    double size = 40;
    protected ArrayList<MapGenerator> lvs = new ArrayList<>();
    ArrayList<Level> bricks = new ArrayList<>();
    ArrayList<Bubble> bubbles = new ArrayList<>();
    ArrayList<Monster> monsters = new ArrayList<>();
    ArrayList<SecondMonster> smonsters = new ArrayList<>();
    ArrayList<Trap> traps = new ArrayList<>();
    MapGenerator level;
    Hero hero;
    boolean isPaused = false;
    private ArrayList<Fruit> fruits = new ArrayList<>();
    private String BGMname = "BGM.wav";
    private Clip BGM;
    private HigherScore scores;
    private Score scoreBoard;
    
    public static enum STATE {
        MENU, GAME, HELP, SCORE
    }
    
    public static STATE State = STATE.MENU;

    public GameComponent(int levelnum) {
        Scanner scanner = null;
        String[] names = new String[5];
        int[] scores = new int[5];

        try {
            scanner = new Scanner(new File("HighScores"));
            int count = 0;
            while (scanner.hasNextLine()) {
                String next = scanner.nextLine();
                if (count < 5) {
                    names[count] = next;
                }
                if (count >= 5) {
                    scores[count - 5] = Integer.parseInt(next);
                }
                count++;
            }
        } catch (FileNotFoundException exception) {
            System.out.println("You gave a bad file name.");
        } finally {
            scanner.close();
        }

        this.scores = new HigherScore(names, scores);
        this.scoreBoard = new Score();
        this.levelnum = levelnum;
        lvs.add(0, null);
        this.addMouseListener(new MouseInput());
        for (int i = 1; i <= 6; i++) {
            MapGenerator levelFile = new MapGenerator(i);
            lvs.add(i, levelFile);
        }
        this.hero = new Hero(this, size);
        setLevel(this.levelnum);
        
        this.label.setForeground(Color.WHITE);
        this.label.setLayout(null);
        this.label.setFont(new Font("Airal", Font.BOLD, 20));
        this.label.setLocation(250, 0);
        this.label.setSize(300, 50);
        
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(new File(this.BGMname).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            this.BGM = clip;
            clip.open(sound);
            clip.loop(1);
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
        
        this.timer = new Timer(REPAINT_INTERVAL_MS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics graphics){
        Menu menu = new Menu();
        Help help = new Help();
        Score scoreBoard = new Score();
        super.paintComponent(graphics);
        this.frame.getContentPane().setBackground(new Color(0,0,0));
        if (State == STATE.GAME) {
            ArrayList<Bubble> removeBubble = new ArrayList<>();
            ArrayList<Monster> removeMonster = new ArrayList<>();
            ArrayList<SecondMonster> removeSmonster = new ArrayList<>();
            ArrayList<Trap> removeTrap = new ArrayList<>();
            ArrayList<Fruit> removeFruit = new ArrayList<>();
            Graphics2D g = (Graphics2D) graphics;

            if (!hero.isKillable()) {
                hero.drawOn(g);
            }else end("You lose!");
            for (Level brick : bricks) {
                    try {
                        brick.drawOn(g);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            for (Monster monster : monsters) {
                monster.drawOn(g);
                if (monster.isKillable()) {
                    removeMonster.add(monster);
                }
            }
            for (Bubble bubble : bubbles) {
                bubble.drawOn(g);
                if (bubble.isKillable()) {
                    removeBubble.add(bubble);
                }
            }
            for (SecondMonster smonster : smonsters) {
                try {
                    smonster.drawOn(g);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (smonster.isKillable()) {
                    removeSmonster.add(smonster);
                }
            }
            for (Trap trap : traps) {
                trap.drawOn(g);
                if (trap.isKillable()) {
                    removeTrap.add(trap);
                }
            }
            for (Fruit fruit : fruits) {
                fruit.drawOn(g);
                this.hero.score += fruit.getScore();
                if (fruit.isKillable()) {
                    removeFruit.add(fruit);
                }
            }
            for (Fruit remove : removeFruit) {
                fruits.remove(remove);
            }
            for (Trap remove : removeTrap) {
                traps.remove(remove);
            }
            for (Bubble remove : removeBubble) {
                bubbles.remove(remove);
            }
            for (Monster remove : removeMonster) {
                monsters.remove(remove);
            }
            for (SecondMonster remove : removeSmonster) {
                smonsters.remove(remove);
            }
            
            this.label.setText("  Level: " + getLevelnum() + "   Score: " + this.hero.score + "    Life: " + this.hero.life);
            
            if (monsters.isEmpty()&&traps.isEmpty()&&fruits.isEmpty()) {
                waitTimer ++;
                if (waitTimer == 100) {
                    if(this.levelnum < 6){
                    this.setLevel(this.levelnum+1);
                    }else{
                        end("You Win!");
                    }
                    waitTimer = 0;
                }
            }
        } else if (State == STATE.MENU) {
            try {
                menu.render(graphics);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else if (State == STATE.HELP) {
            help.render(graphics);
        } else if (State == STATE.SCORE){
            scoreBoard.render(graphics);
        }
    }
    
    public void drawAll() {
        this.repaint();
    }

    public void updateAll() {
        this.hero.updatePosition();
    }

    public int getLevelnum() {
        return this.levelnum;
    }
    
    public MapGenerator getLevel() {
        return this.level;
    }
    
    public ArrayList<Level> getLevels() {
        return this.bricks;
    }
    
    public Hero getHero(){
        return this.hero;
    }
    
    public ArrayList<Bubble> getBubbles() {
        return this.bubbles;
    }
    
    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }

    public ArrayList<SecondMonster> getSmonsters() {
        return this.smonsters;
    }
    
    public ArrayList<Trap> getTraps() {
        return this.traps;
    }

    public ArrayList<Fruit> getFruits() {
        return this.fruits ;
    }

    public boolean getisPaused() {
        return isPaused;
    }
    
    public void setLevel(int i) {
        int oldScore = this.hero.getScore();
        int oldlife = this.hero.life;
        bricks.clear();
        monsters.clear();
        bubbles.clear();
        smonsters.clear();
        traps.clear();
        fruits.clear();
        this.levelnum = i;
        MapGenerator newlevel = new MapGenerator(this.levelnum);
        this.level = newlevel;
        this.level.updateFile(this.levelnum);
        this.hero = new Hero(this, size);
        this.hero.score = oldScore;
        this.hero.life = oldlife;
        ArrayList<ArrayList<Integer>> levelInfo = this.level.getLevelInfo();
        for (int row = 0; row < levelInfo.size(); row++) {
            for (int col = 0; col < levelInfo.get(0).size(); col++) {
                switch (levelInfo.get(row).get(col)) {
                case 1: {
                    Level brick = new Level(row, col, size);
                    bricks.add(brick);
                    break;
                }
                case 2: {
                    Monster monster = new Monster(row, col, this, false, size);
                    monsters.add(monster);
                    break;
                }
                case 3: {
                    Monster monster = new Monster(row, col, this, true, size);
                    monsters.add(monster);
                    break;
                }
                }
            }
        }
    }

    public void end(String saying) {
        this.isPaused = true;
        State = STATE.MENU;
        this.BGM.stop();
        JFrame frame1 = new JFrame();
        JPanel panel1 = new JPanel();

        JLabel label1 = new JLabel(saying);
        label1.setLayout(null);
        label1.setFont(new Font("Arial", Font.BOLD, 40));
        label1.setSize(300, 100);
        
        JButton restart = new JButton("Restart");
        JButton exit = new JButton("Exit");

        JTextField namebox = new JTextField();
        JButton addscore = new JButton("AddScore");
        
        ArrayList<JFrame> frames = new ArrayList<JFrame>();
        frames.add(this.frame);
        frames.add(frame1);
            if (this.scores.newHigherScore(this.hero.score)) {
                String name = JOptionPane.showInputDialog("Player Name?");
                this.scores.newScore(name, this.hero.score);
                String[] names = this.scores.getNames();
                int[] scoress = this.scores.getScores();
                try {
                    PrintWriter pw = new PrintWriter("HighScores");
                    pw.println(names[0]);
                    pw.println(names[1]);
                    pw.println(names[2]);
                    pw.println(names[3]);
                    pw.println(names[4]);
                    pw.println(Integer.toString(scoress[0]));
                    pw.println(Integer.toString(scoress[1]));
                    pw.println(Integer.toString(scoress[2]));
                    pw.println(Integer.toString(scoress[3]));
                    pw.println(Integer.toString(scoress[4]));
                    pw.close();
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                }
            }

        EndListener restartlsn = new EndListener("restart", frames);
        restart.addActionListener(restartlsn);
        
        EndListener exitlsn = new EndListener("exit", frames);
        exit.addActionListener(exitlsn);
        if (exitlsn.clicked) {
            System.exit(0);
        }

        panel1.add(restart, BorderLayout.WEST);
        panel1.add(exit, BorderLayout.EAST);

        frame1.add(label1, BorderLayout.NORTH);
        frame1.add(panel1, BorderLayout.SOUTH);
        frame1.pack();

        int x1 = this.frame.getX() + this.frame.getWidth()/2 - frame1.getWidth()/2;
        int y1 = this.frame.getY() + this.frame.getHeight()/2 - frame1.getHeight()/2 ;
        frame1.setLocation(x1, y1);

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);
    }
    
    public void setWindow() {
        this.frame.setSize(new Dimension(575, 600));
        this.frame.setTitle("Bubble Bobble");
        this.frame.add(this.label);
        this.frame.add(this);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int x = width / 2 - this.frame.getWidth() / 2;
        int y = height / 2 - this.frame.getHeight() / 2 - 40;
        this.frame.setLocation(x, y);

        GameListener a = new GameListener(this);

        addKeyListener(a);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }
}
