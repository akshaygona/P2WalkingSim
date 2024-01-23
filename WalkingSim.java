//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P02 Walking Simulator
// Course:   CS 300 Fall 2022
//
// Author:   Akshay Gona
// Email:    gona@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    none
// Partner Email:   none
// Partner Lecturer's Name: none
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         Varun Munagala, helped me debug my code
// Online Sources:  (identify each by URL and describe how it helped)
//
///////////////////////////////////////////////////////////////////////////////
import processing.core.PImage;

import java.io.File;
import java.util.Random;

/**
 * This class and the methods contained within create and animate a figure, making it walk by
 * iterating through a cycle of images.
 */
public class WalkingSim {
    private static Random randGen;
    private static int bgColor;
    private static PImage[] frames;
    private static Walker[] walkers;
    /**
     * the setup method contains variables and arrays which will be used for the rest of the class
     * the variables include the array of walker frames, and the array is populated.
     */
    public static void setup() {
        randGen = new Random();
        bgColor = randGen.nextInt();
        frames = new PImage[Walker.NUM_FRAMES];
        walkers = new Walker[8];

        int randomWalkerLen = randGen.nextInt(8) + 1;
        for (int i = 0; i < randomWalkerLen; i++) {
            walkers[i] = new Walker(randGen.nextFloat() * Utility.width(),
                randGen.nextFloat() * Utility.height());
        }
        for (int i = 0; i < Walker.NUM_FRAMES; i++) {
            frames[i] = Utility.loadImage("images" + File.separator + "walk-" + i + ".png");
        }
    }

    /**
     * the draw method sets the background color of the GUI and then, through a for loop and
     * conditional statements, sets the walkers in the GUI in motion and updates their statuses
     */
    public static void draw() {
        Utility.background(bgColor);
        for (int i = 0; i < walkers.length; i++) {
            if (walkers[i] == null)
                break;
            Utility.image(frames[walkers[i].getCurrentFrame()], walkers[i].getPositionX(),
                walkers[i].getPositionY());
            if (walkers[i].isWalking()) {
                walkers[i].setPositionX(walkers[i].getPositionX() + 3);
                walkers[i].setPositionX(walkers[i].getPositionX() % Utility.width());
                walkers[i].update();
            }
        }
    }

    /**
     * the isMouseOver method checks if the user's mouse/cursor is over the "hitbox" of the walker
     * by setting bounds around the walker's position with conditional statements
     *
     * @param walker
     * @return
     */
    public static boolean isMouseOver(Walker walker) {
        int imageWidth = frames[0].width;
        int imageHeight = frames[0].height;
        return Utility.mouseX() > (walker.getPositionX() - imageWidth / 2.) && Utility.mouseX() < (
            walker.getPositionX() + imageWidth / 2.) && Utility.mouseY() > (walker.getPositionY()
            - imageHeight / 2.) && Utility.mouseY() < (walker.getPositionY() + imageHeight / 2.);
    }

    /**
     * the mousePressed method checks if the mouse/cursor of the user is pressed over a walker
     */
    public static void mousePressed() {
        for (int i = 0; i < walkers.length; i++) {
            if (walkers[i] != null) {
                if (isMouseOver(walkers[i])) {
                    //System.out.println("Mouse is over walker");
                    walkers[i].setWalking(true);
                    break;
                }
            }
        }
    }

    /**
     * the keyPressed method checks user entered characters/keys are equivalent to S or A, which
     * are special cases for stopping and adding walkers in the GUI
     *
     * @param c
     */
    public static void keyPressed(char c) {
        if (c == 'a' || c == 'A') {
            for (int i = 0; i < walkers.length; i++) {
                if (walkers[i] == (null)) {
                    walkers[i] = new Walker(randGen.nextFloat() * Utility.width(),
                        randGen.nextFloat() * Utility.height());
                    break;
                }
            }
        }
        if (c == 's' || c == 'S') {
            for (int i = 0; i < walkers.length; i++) {
                if (walkers[i] == null)
                    break;
                walkers[i].setWalking(false);
            }
        }
    }

    /**
     * main method runs the program
     *
     * @param args
     */
    public static void main(String[] args) {
        Utility.runApplication();
    }
}
