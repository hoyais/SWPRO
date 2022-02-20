package exam.word;

import java.util.*;

class UserSolutionEx {
    class Word {
        String data;
        int len;
        public Word(String s, int l) {
            data = s; len = l;
        }
    }
    Word[] baseWord;
    HashMap<String, PriorityQueue<String>> dictionaryData = new HashMap<>();
    Set<String> wordInfo = new HashSet<>();
    int N;

    void init(int n, char str[]) {
        N = n;
        baseWord = new Word[40000];
        dictionaryData.clear();
        wordInfo.clear();
        String s = "";
        int i, size = 0;
        for(i = 0; i <= n; i++) {
            if(str[i] == '_' || i == n) {
                baseWord[i-size] = new Word(s, size);
                s = ""; size = 0;
            } else {
                s += str[i]; size++;
            }
        }
    }

    void addWord(char str[]) {
        String s = charToStr(str);
        for(int i = 0; i < s.length(); i++) {
            String k = s.substring(0, i) + "*" + s.substring(i+1);
            if(dictionaryData.containsKey(k)) {
                dictionaryData.get(k).add(s);
            } else {
                PriorityQueue<String> list = new PriorityQueue<>();
                list.add(s);
                dictionaryData.put(k, list);
            }
        }
        wordInfo.add(s);
    }

    void removeWord(char str[]) {
        wordInfo.remove(charToStr(str));
    }

    int correct(int start, int end) {
        int key = start;
        int count = 0;
        while(key <= end) {
            String s = baseWord[key].data;
            if(wordInfo.contains(s)) {
                key += baseWord[key].len+1;
                continue;
            }
            String data = null;
            for(int i = 0; i < baseWord[key].len; i++) {
                String k = s.substring(0, i) + "*" + s.substring(i+1);
                if(dictionaryData.containsKey(k)) {
                    PriorityQueue<String> pq = dictionaryData.get(k);
                    while(pq.size() > 0) {
                        String vl = pq.peek();
                        if(wordInfo.contains(vl)) {
                            data = (data == null || data.compareTo(vl) > 0) ? vl : data;
                            break;
                        } else pq.poll();
                    }
                }
            }
            if(data != null) {
                count++;
                baseWord[key].data = data;
            }
            key += baseWord[key].len+1;
        }
        return count;
    }

    void destroy(char result[]) {
        int r = 0, key = 0;
        while(key < N) {
            for(int i = 0; i < baseWord[key].len; i++)
                result[r++] = baseWord[key].data.charAt(i);
            result[r++] = '_';
            key = r;
        }
        result[r-1] = '\0';
    }

    String charToStr(char[] str) {
        String s = "";
        for(int i = 0; str[i] != '\0'; i++) s+= str[i];
        return s;
    }
}