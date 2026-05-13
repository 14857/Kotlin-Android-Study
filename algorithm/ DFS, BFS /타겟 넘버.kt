// 프로그래머스 > 너비 우선 탐색(DFS/BFS) > 타겟 넘버
// BFS 풀이

class Solution {
    fun solution(numbers: IntArray, target: Int): Int {
        var answer = 0

        var leaves = mutableListOf(0)

        for (num in numbers) {
            val temp = mutableListOf<Int>()

            for (parent in leaves) {
                temp.add(parent + num)
                temp.add(parent - num)
            }

            leaves = temp
        }

        for (leaf in leaves) {
            if (leaf == target) {
                answer++
            }
        }

        return answer
    }
}
