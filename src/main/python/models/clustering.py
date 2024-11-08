import sys
import json

def perform_test(input_data):
    response = {
        "message": "파이썬 연동 성공!",
        "receivedData": input_data,
        "status": "success",
        "error": None
    }
    print(json.dumps(response, ensure_ascii=False))

if __name__ == "__main__":
    try:
        # 테스트용 더미 데이터
        test_data = {
            "algorithm": "test",
            "dataPoints": [],
            "parameters": {}
        }
        perform_test(test_data)
    except Exception as e:
        error_response = {
            "message": "에러 발생",
            "receivedData": None,
            "status": "error",
            "error": str(e)
        }
        print(json.dumps(error_response, ensure_ascii=False))