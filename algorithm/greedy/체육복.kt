class Solution {

    fun solution(n: Int, lost: IntArray, reserve: IntArray): Int {

        var answer = 0

        // 각 학생이 가진 체육복 개수
        val now = MutableList(n) { 1 }

        // 도난당한 학생
        for (i in lost) {
            now[i - 1]--
        }

        // 여벌 체육복 있는 학생
        for (i in reserve) {
            now[i - 1]++
        }

        // 체육복 빌려주기
        for (i in 0 until n) {

            // 첫 번째 학생
            if (i == 0) {

                if (now[i] == 0 && now[i + 1] > 1) {
                    now[i]++
                    now[i + 1]--
                }
            }

            // 마지막 학생
            else if (i == n - 1) {

                if (now[i] == 0 && now[i - 1] > 1) {
                    now[i]++
                    now[i - 1]--
                }
            }

            // 가운데 학생
            else {

                // 앞 학생에게 빌리기
                if (now[i] == 0 && now[i - 1] > 1) {

                    now[i]++
                    now[i - 1]--
                }

                // 뒤 학생에게 빌리기
                else if (now[i] == 0 && now[i + 1] > 1) {

                    now[i]++
                    now[i + 1]--
                }
            }
        }

        // 체육 수업 가능한 학생 수 계산
        for (i in now) {

            if (i > 0) {
                answer++
            }
        }

        return answer
    }
}
