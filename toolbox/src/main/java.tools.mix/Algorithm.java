import java.util.List;

/**
 * @author Jianjun Lv
 * @date 7/6/2018 2:29 PM
 */
public class Algorithm {
    //return four number is the two biggest num and the index of them
    public static int[] FindTwoBiggest(List<Integer> input) {
        if (input == null || input.size() == 0) {
            return new int[]{0, 0, 0, 0};
        } else if (input.size() == 1) {
            return new int[]{input.get(0), input.get(0), 0, 0};
        } else {
            int firstNum, secondNum, firstIndex, secondIndex;
            if (input.get(0) < input.get(1)) {
                firstNum = input.get(1);
                secondNum = input.get(0);
                firstIndex = 1;
                secondIndex = 0;
            } else {
                firstNum = input.get(0);
                secondNum = input.get(1);
                firstIndex = 0;
                secondIndex = 1;
            }
            for (int i = 2; i < input.size(); i++) {
                int curNum = input.get(i);
                if (curNum > firstNum) {
                    secondNum = firstNum;
                    firstNum = curNum;
                    secondIndex = firstIndex;
                    firstIndex = i;
                } else if (curNum > secondNum) {
                    secondNum = curNum;
                    secondIndex = i;
                }
            }
            return new int[]{firstNum, secondNum, firstIndex, secondIndex};
        }
    }
}
