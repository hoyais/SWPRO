package exam.ResultExam;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * [H2220][Pro] 성적조회
 * 
 * @author hoyamac16
 *
 */
class UserSolution {
	private static final int MAX_MID = 1000000000;
	private static final int MAX_SCORE = 300000;
	private static final int MAX_GRADE = 3;
	
	class Student {
		int mId;
		int mGrade;
		char mGender[];
		String sGender;
		int mScore;
		
		public Student(int mId, int mGrade, char mGender[], int mScore) {
			this.mId = mId;
			this.mGrade = mGrade;
			this.mGender = mGender;
			this.sGender = charToString(mGender);
			this.mScore = mScore;
		}
	}
	
	int midScore[];	// mid array for storing score
	HashMap<Integer, Student> midHash; 
	
	public void init() {
		midScore = new int[MAX_MID];
		
		
		return;
	}

	public int add(int mId, int mGrade, char mGender[], int mScore) {
		return 0;
	}

	public int remove(int mId) {
		return 0;
	}

	public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
		return 0;
	}
	
	public String charToString(char c[]) {
		String str = "";
		
		for (int i=0; c[i] != '\0'; i++) {
			str = str + c[i];
		}
		
		return str;
	}
}