package com.mushan.WordCut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class Reader {
    Random r = new Random();
    public static int index;
    File file = new File("Word.txt");
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line = null;
    ArrayList words = new ArrayList<Word>();
    ArrayList ws = new ArrayList<Word>();
    public Reader() throws IOException {
        while ((line = br.readLine()) != null) {
            String[] word = line.trim().split("\\s+");
            Word w = new Word();
            w.setWord(word[0]);
            w.setS1(word[1]);
            w.setS2(word[2]);
            w.setS3(word[3]);
            w.setS4(word[4]);
            w.setAnswer(word[5]);
            words.add(w);
        }
        br.close();
    }

    public Word selectWord() {
        index = r.nextInt(words.size());
        Word word = (Word) words.get(index);
        return word;
    }
}
