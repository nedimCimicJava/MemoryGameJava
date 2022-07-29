import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MemoryGame implements ActionListener{

    JFrame frame = new JFrame("Memory Game");
    JPanel field = new JPanel();

    JPanel start_screen = new JPanel();
    JPanel end_screen = new JPanel();
    JPanel instruct = new JPanel();

    JButton btn[] = new JButton[20];
    JButton start = new JButton("Start");

    JButton goBack = new JButton("Main Menu");



    private JLabel IntroText = new JLabel("Memory Game");

    Random randomGenerator = new Random();

    String[] board;

    String[] cardNames = {"a.png","g.png","l.png","o.jpg","p.png","pl.png"};

    String cards[]=new String[6];

    Boolean shown = true;

    int temp=30;
    int temp2=30;

    private boolean purgatory = false;

    public MemoryGame() {


        frame.setSize(500,300);
        frame.setLocation(500,300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start_screen.setLayout(new BorderLayout());
        start.addActionListener(this);
        start.setEnabled(true);
        start.setBounds(100, 100, 100, 80);

        IntroText.setFont(new Font("Serif", Font.PLAIN, 50));
        instruct.add(IntroText);


        start_screen.add(instruct, BorderLayout.CENTER);
        start_screen.add(start, BorderLayout.SOUTH);

        frame.add(start_screen, BorderLayout.CENTER);
        frame.setVisible(true);


    }


    public String getName(String imgName) {

        if (imgName == "a.png") {
            return "Apple";
        }
        if (imgName == "g.png") {
            return "Grape";
        }
        if (imgName == "l.png") {
            return "Lemon";
        }
        if (imgName == "p.png") {
            return "Pear";
        }
        if (imgName == "pl.png") {
            return "Plum";
        }
        if (imgName == "o.jpg") {
            return "Orange";
        }

        return "Unknown";

    }

    public String getImgName(String name) {

        if (name == "Apple") {
            return "a.png";
        }
        if (name == "Grape") {
            return "g.png";
        }
        if (name == "Lemon") {
            return "l.png";
        }
        if (name == "Pear") {
            return "p.png";
        }
        if (name == "Plum") {
            return "pl.png";
        }
        if (name == "Orange") {
            return "o.jpg";
        }

        return "basket.png";

    }




    public void setUpGame(){

        clearMain();
        board = new String[12];
        for(int i=0;i<(12);i++){
            btn[i] = new JButton("");
            btn[i].setBackground(new Color(220, 220, 220));
            btn[i].addActionListener(this);
            btn[i].setEnabled(true);
            field.add(btn[i]);
        }



        for(int i=0;i<6;i++){
            for(int z=0;z<2;z++){
                while(true){
                    int y = randomGenerator.nextInt(12);
                    if(board[y]==null){
                        btn[y].setText(getName(cardNames[i]));
                        try {
                            Image img = ImageIO.read(getClass().getResource("source" + cardNames[i]));
                            btn[y].setIcon(new ImageIcon(img));
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }

                        ImageIcon icon = new ImageIcon("source/" + cardNames[i]);
                        Image image = icon.getImage();
                        Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                        icon = new ImageIcon(newimg);
                        btn[y].setIcon(icon);

                        File imageCheck = new File("source/a.png");

                        if(imageCheck.exists()) {
                            System.out.println("Image file found!");}
                        else {
                            System.out.println("Image file not found!");
                        }

                        board[y]=getName(cardNames[i]);
                        break;
                    }
                }
            }
        }
        createBoard();

    }




    public void createBoard(){//this is just gui stuff to show the board
        field.setLayout(new BorderLayout());
        start_screen.add(field, BorderLayout.CENTER);

        field.setLayout(new GridLayout(5,4,2,2));
        field.setBackground(Color.black);
        field.requestFocus();
    }

    public void clearMain(){//clears the main menu so I can add the board
        start_screen.remove(instruct);
        start_screen.remove(start);

        start_screen.revalidate();
        start_screen.repaint();
    }



    public void hideField(){//this sets all the boxes blank
        for(int i=0;i<(12);i++){
            //if(boardQ[i]==0)

            btn[i].setText("");
            ImageIcon icon = new ImageIcon("source/basket.png");
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(newimg);
            btn[i].setIcon(icon);
        }
        shown=false;
    }

    public void switchSpot(int i){//this will switch the current spot to either blank or what it should have
        if(board[i]!="done"){//when a match is correctly chosen, it will no longer switch when pressed
            if(btn[i].getText()==""){
                btn[i].setText(board[i]);

                String img = getImgName(btn[i].getText());
                ImageIcon icon = new ImageIcon("source/" + img);
                Image image = icon.getImage();
                Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newimg);
                btn[i].setIcon(icon);


            } else {
                btn[i].setText("");
                ImageIcon icon = new ImageIcon("source/basket.png");
                Image image = icon.getImage();
                Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newimg);
                btn[i].setIcon(icon);
            }
        }
    }

    public boolean checkWin(){//checks if every spot is labeled as done
        for(int i=0;i<(12);i++){
            if (board[i]!="done")return false;
        }
        winner();
        return true;
    }

    public void winner(){

        start_screen.remove(field);
        start_screen.add(end_screen, BorderLayout.CENTER);
        end_screen.add(new JLabel("You Win"), BorderLayout.NORTH);
        end_screen.add(goBack);
        goBack.setEnabled(true);
        goBack.addActionListener(this);
    }

    public void goToMainScreen(){
        new MemoryGame();
    }




    @Override
    public void actionPerformed(ActionEvent e) {


        Object source = e.getSource();

        if(purgatory){
            switchSpot(temp2);
            switchSpot(temp);
            temp=(12);
            temp2=30;
            purgatory=false;
        }

        if(source == start) {
            setUpGame();
        }

        if(source==goBack){//back to main screen
            frame.dispose();
            goToMainScreen();
        }

        for(int i =0;i<(12);i++){//gameplay when a button is pressed
            if(source==btn[i]){
                if(shown){
                    hideField();//if first time, hides field
                }else{//otherwise play
                    switchSpot(i);
                    if(temp>=(12)){
                        temp=i;
                    } else {
                        if((board[temp]!=board[i])||(temp==i)){
                            temp2=i;
                            purgatory=true;


                        } else {
                            board[i]="done";
                            board[temp]="done";
                            checkWin();
                            temp=(12);
                        }

                    }
                }

            }
        }


    }

    public static void main(String[] args) {
        new MemoryGame();// Calling the class constructor.
    }

}
