package exam.ResultExam;

import java.util.*;

class UserSolutionEX {
    Map<Integer, Student> mapStudent = new HashMap<>();
    TreeSet<Student> grade[][] = new TreeSet[4][2];
 
    void init() {
        mapStudent.clear();
        for (int i = 1; i <= 3; i++)
            for (int j = 0; j < 2; j++)
                grade[i][j] = new TreeSet<>((a, b) -> a.score == b.score ? b.id - a.id : b.score - a.score);
    }
 
    int add(int mId, int mGrade, char mGender[], int mScore) {
        Student student = new Student(mId, mGrade, mGender, mScore);
        mapStudent.put(mId, student);
        grade[mGrade][student.gender].add(student);
        return grade[mGrade][student.gender].first().id;
    }
 
    int remove(int mId) {
        Student student = mapStudent.get(mId);
        if (student != null) {
            grade[student.grade][student.gender].remove(student);
            if (!grade[student.grade][student.gender].isEmpty())
                return grade[student.grade][student.gender].last().id;
        }
        return 0;
    }
 
    int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
        TreeSet<Student> treeSet = new TreeSet<>((a, b) -> a.score == b.score ? a.id - b.id : a.score - b.score);
        for (int i = 0; i < mGradeCnt; i++) {
            for (int j = 0; j < mGenderCnt; j++) {
                Student temp = new Student(0, mGrade[i], mGender[j], mScore);
                Student lowest = grade[mGrade[i]][temp.gender].floor(temp);
                if (lowest != null)
                    treeSet.add(lowest);
            }
        }
        return treeSet.isEmpty() ? 0 : treeSet.first().id;
    }
}
 
class Student {
    int id, grade, gender, score;
 
    public Student(int mId, int mGrade, char mGender[], int mScore) {
        id = mId;
        grade = mGrade;
        gender = mGender[0] == 'm' ? 0 : 1;
        score = mScore;
    }
}