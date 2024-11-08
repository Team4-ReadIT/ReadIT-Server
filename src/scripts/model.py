import sys

# 데이터 수집 및 처리 함수 예시
def toUpper(data):
    # 데이터 처리 작업 수행
    upstr = data.upper()
    return upstr

# 데이터 입력 받기
data = sys.argv[1:]

print(data)

# 데이터 처리 함수 호출
result = [toUpper(upstr) for upstr in data]

# 결과 출력
print(result)