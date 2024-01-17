import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TypingSpeedTestGUI extends JFrame {
    private List<String> wordList = new ArrayList<>();
    private JLabel[] wordLabels;
    private JButton startButton;

    public TypingSpeedTestGUI() {
        setTitle("Typing Speed Test");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create an array of labels to display random words
        wordLabels = new JLabel[10];
        for (int i = 0; i < 10; i++) {
            wordLabels[i] = new JLabel();
            add(wordLabels[i]);
        }

        startButton = new JButton("Start Test");
        add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runTest();
            }
        });

        // Initialize the wordList
        Collections.addAll(wordList, "apple", "banana", "cherry", "dog", "elephant", "frog", "grape", "house", "icecream", "jacket");
    }

    public void runTest() {
        // Shuffle the wordList to get random words
        Collections.shuffle(wordList);

        // Display random words as labels
        for (int i = 0; i < wordLabels.length; i++) {
            wordLabels[i].setText(wordList.get(i));
        }

        double start = LocalTime.now().toNanoOfDay();

        // Wait for user input
        String typedWords = JOptionPane.showInputDialog("Type the words you see, separated by spaces:");

        double end = LocalTime.now().toNanoOfDay();

        String[] typedArray = typedWords.split(" ");

        int wrong = 0;
        StringBuilder wrongWords = new StringBuilder();

        for (int i = 0; i < wordList.size() && i < typedArray.length; i++) {
            String typedWord = typedArray[i];
            if (!typedWord.equals(wordList.get(i))) {
                wrong++;
                wrongWords.append(typedWord).append(" ");
            }
        }

        double timeElapsed = end - start;
        double seconds = timeElapsed / 1000000000.0;

        int numChars = typedWords.length();
        int wpm = calculateWPM(numChars, seconds);

        // Display the results in a message dialog
        String resultMessage = "Time taken: " + seconds + " seconds\n";
        resultMessage += "Typing speed (WPM): " + wpm + " wpm\n";
        resultMessage += "Mistakes made: " + wrong + " words\n";
        resultMessage += "Wrong words: " + wrongWords.toString();

        JOptionPane.showMessageDialog(null, resultMessage, "Test Results", JOptionPane.INFORMATION_MESSAGE);
    }

    private int calculateWPM(int numChars, double seconds) {
        return (int) ((((double) numChars / 5) / seconds) * 60);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TypingSpeedTestGUI gui = new TypingSpeedTestGUI();
                gui.setVisible(true);
            }
        });
    }
}