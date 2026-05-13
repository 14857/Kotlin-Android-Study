# 깊이/너비 우선 탐색(DFS/BFS) > 게임 맵 최단거리
# 상대 팀 진영에 도착할 수 없을 때는 -1을 return

from collections import deque         

def solution(maps):
    answer = 0
    
    n = len(maps)
    m = len(maps[0])
    
    # 이동할 방향 정의
    dx = [-1,1,0,0]
    dy = [0,0,-1,1]
    
    def bfs(x,y):
        queue = deque()
        queue.append((x,y))

        while queue:
            x,y = queue.popleft()

            # 현재위치에서 네 방향으로의 위치 확인 
            for i in range(4):
                nx = x + dx[i]
                ny = y + dy[i]

                # 공간 벗어난 경우 무시
                if(nx<0 or ny<0 or nx >= n or ny >= m):
                    continue
                if maps[nx][ny] == 0:
                    continue
                if maps[nx][ny] == 1:
                    maps[nx][ny] = maps[x][y] + 1
                    queue.append((nx,ny))

        return maps[n-1][m-1]

    answer = bfs(0,0)
    
    if(answer == 1):
        answer = -1
    
    return answer
