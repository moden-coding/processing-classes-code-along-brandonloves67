import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.*;

public class App extends PApplet {
    ArrayList<bubble> bubbles;
    double timer;
    int scene;
    double highscore;
    double gameStart;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() {
        readHighscore();
        bubbles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bubbleMaker();
        }
        scene = 0;
        gameStart = millis();
    }

    public void settings() {
        size(1200, 800);
    }

    public void draw() {

        background(0);

        if (scene == 0) {
            for (bubble b : bubbles) {
                b.display();
                b.update();
            }
            fill(255);
            textSize(50);
            timer = millis() - gameStart;
            timer = ((int) millis() / 100) / 10.0;
            text("" + timer, width - 100, 50);
            if (bubbles.size() == 0) {
                scene = 1;
                if (highscore < timer) {
                    highscore = timer;
                    saveHighscore();
                }
            }
        } else {
            text("Score " + timer, 400, 400);
            text("Highscore: " + highscore, 400, 500);
        }

    }

    public void saveHighscore() {
        int numberToSave = 123; // This is the integer we want to save
        String filePath = "output.txt"; // Path to the text file

        try (PrintWriter writer = new PrintWriter("highscore.txt")) {
            writer.println(highscore); // Writes the integer to the file
            writer.close(); // Closes the writer and saves the file
           
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    

    public void readHighscore() {
        try (Scanner scanner = new Scanner(Paths.get("highscore.txt"))) {
            // we read the file until all lines have been read
            while (scanner.hasNextLine()) {
                // we read one line
                String row = scanner.nextLine();
                // we print the line that we read
                highscore = Double.valueOf(row);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void bubbleMaker() {
        int x = (int) random(700);
        int y = (int) random(700);

        bubble bubble = new bubble(x, y, this);
        bubbles.add(bubble);
    }

    public void keyPressed() {
        if (key == ' ') {
            if (scene == 0) {
                bubbles.clear();
            } else {
                setup();
            }

        }
    }

    public void mousePressed() {
        for (int i = 0; i < bubbles.size(); i++) {
            bubble b = bubbles.get(i);
            if (b.checkTouch(mouseX, mouseY) == false) {
                bubbles.remove(b);
            }
        }
    }

}
