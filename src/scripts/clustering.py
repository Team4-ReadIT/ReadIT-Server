import numpy as np
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import KMeans
from sklearn.metrics import silhouette_score
import pymysql
from sklearn.metrics.pairwise import cosine_similarity
import sys
import json

sys.stdout = open(sys.stdout.fileno(), mode='w', encoding='utf-8')

# 데이터베이스 접속 정보
db_config = {
    "host": "readit-mysql.ct026sge4qfq.ap-northeast-2.rds.amazonaws.com",
    "port": 3306,
    "user": "team4",
    "password": "readit2024",
    "database": "readit",
    "charset": "utf8mb4",
}

def process_articles():
    try:
        connection = pymysql.connect(**db_config)
        cursor = connection.cursor(pymysql.cursors.DictCursor)

        # 모든 기사 가져오기
        select_query = "SELECT id, title FROM article"
        cursor.execute(select_query)
        articles = cursor.fetchall()

        # 데이터프레임으로 변환
        data = pd.DataFrame(articles)

        # 제목 전처리: 결측값 제거
        data = data.dropna(subset=['title'])

        # 1. TF-IDF 임베딩 및 벡터화
        tfidf_vectorizer = TfidfVectorizer(min_df=3, ngram_range=(1, 5))
        tfidf_matrix = tfidf_vectorizer.fit_transform(data['title'])
        vector = tfidf_matrix.toarray()

        return data, vector, tfidf_vectorizer

    except Exception as e:
        return None, None, None
    finally:
        # 연결 종료
        if connection:
            connection.close()

def perform_kmeans_clustering(data, vector, k):
    # K-Means 클러스터링 수행
    kmeans = KMeans(n_clusters=k, random_state=0)
    result = kmeans.fit_predict(vector)
    data['cluster'] = result
    return data, kmeans

def find_similar_articles(input_title, data, vector, tfidf_vectorizer, kmeans):
    # 입력된 기사 제목을 벡터화
    input_vector = tfidf_vectorizer.transform([input_title]).toarray()

    # 입력된 기사가 속한 클러스터 찾기
    input_cluster = kmeans.predict(input_vector)[0]

    # 동일 클러스터에 속한 기사 필터링
    similar_articles = data[data['cluster'] == input_cluster]

    # input_title과 동일한 기사를 제외 (case-insensitive 비교)
    similar_articles = similar_articles[similar_articles['title'].str.lower() != input_title.lower()]

    # 코사인 유사도를 사용하여 유사도 계산
    similar_vectors = vector[similar_articles.index]
    similarities = cosine_similarity(input_vector, similar_vectors).flatten()

    # 유사도가 높은 상위 10개 기사 선택
    top_indices = np.argsort(similarities)[::-1][:10]
    top_articles = similar_articles.iloc[top_indices]

    result_json = top_articles[['id', 'title']].reset_index(drop=True).to_json(orient="records", force_ascii=False)
    return result_json

if __name__ == "__main__":
    # 명령줄 인자로 articleId 받기
    if len(sys.argv) < 2:
        sys.exit(1)

    article_id = sys.argv[1]

    # MySQL에서 articleId를 이용해 해당 Article의 Title을 조회
    try:
        connection = pymysql.connect(**db_config)
        cursor = connection.cursor(pymysql.cursors.DictCursor)

        # 주어진 articleId로 제목 가져오기
        select_query = "SELECT title FROM article WHERE id = %s"
        cursor.execute(select_query, (article_id,))
        article = cursor.fetchone()

        if article is None:
            sys.exit(1)

        input_title = article['title']

    except Exception as e:
        sys.exit(1)
    finally:
        if connection:
            connection.close()

    # 데이터 처리
    data, vector, tfidf_vectorizer = process_articles()
    if data is None or vector is None or tfidf_vectorizer is None:
        sys.exit(1)

    # K-Means 클러스터링 수행 (최적의 k 값을 설정하거나, 필요에 따라 조정)
    k = 20
    clustered_data, kmeans = perform_kmeans_clustering(data, vector, k)

    # 유사한 기사 찾기
    result_json = find_similar_articles(input_title, clustered_data, vector, tfidf_vectorizer, kmeans)
    print(result_json)