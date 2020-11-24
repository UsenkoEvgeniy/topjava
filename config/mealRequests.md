#RestMeals

##getAll
get all meals for user
###Request
curl --location --request GET 'http://localhost:8080/topjava/rest/meals'
###Response
Status: 200 OK
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 24 Nov 2020 17:19:41 GMT
Keep-Alive: timeout=20
Connection: keep-alive

[
  {
    "id": 100008,
    "dateTime": "2020-01-31T20:00:00",
    "description": "Ужин",
    "calories": 510,
    "excess": true
  },
  {
    "id": 100007,
    "dateTime": "2020-01-31T13:00:00",
    "description": "Обед",
    "calories": 1000,
    "excess": true
  },
  {
    "id": 100006,
    "dateTime": "2020-01-31T10:00:00",
    "description": "Завтрак",
    "calories": 500,
    "excess": true
  },
  {
    "id": 100005,
    "dateTime": "2020-01-31T00:00:00",
    "description": "Еда на граничное значение",
    "calories": 100,
    "excess": true
  },
  {
    "id": 100004,
    "dateTime": "2020-01-30T20:00:00",
    "description": "Ужин",
    "calories": 500,
    "excess": true
  },
  {
    "id": 100011,
    "dateTime": "2020-01-30T13:54:00",
    "description": "Создание Post",
    "calories": 1000,
    "excess": true
  },
  {
    "id": 100003,
    "dateTime": "2020-01-30T13:00:00",
    "description": "Обновление через PUT",
    "calories": 1000,
    "excess": true
  }
]

##delete
delete user with id
###Request
curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100002'
###Response
Status: 204 No Content
Date: Tue, 24 Nov 2020 17:24:54 GMT
Keep-Alive: timeout=20
Connection: keep-alive

##put
update meal
###Request
curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100003' \
--data-raw '{
    "dateTime": "2020-01-30T13:00",
    "description": "Обновление через PUT",
    "calories": 1000
}'
###Response
Status: 204 No Content
Date: Tue, 24 Nov 2020 17:24:48 GMT
Keep-Alive: timeout=20
Connection: keep-alive

##create
create meal
###Request
curl --location --request POST 'http://localhost:8080/topjava/rest/meals/' \
--data-raw '    {
        "dateTime": "2020-01-30T13:54:00",
        "description": "Создание Post",
        "calories": 1000
    }'
###Response
Status: 201 Created
Location: http://localhost:8080/topjava/rest/meals/100011
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 24 Nov 2020 17:24:29 GMT
Keep-Alive: timeout=20
Connection: keep-alive

{
    "id": 100011,
    "dateTime": "2020-01-30T13:54:00",
    "description": "Создание Post",
    "calories": 1000,
    "user": null
}

##getOne
get one meal
###Request
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/100008'
###Response
Status: 200 OK
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 24 Nov 2020 17:24:39 GMT
Keep-Alive: timeout=20
Connection: keep-alive

{
    "id": 100008,
    "dateTime": "2020-01-31T20:00:00",
    "description": "Ужин",
    "calories": 510,
    "user": null
}

##getFiltered
Get filtered by date and time
###Request
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2011-12-03&startTime=09:15:30&endDate=2020-01-30&endTime=11:15:30'
###Response
Status: 200 OK
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Tue, 24 Nov 2020 17:03:28 GMT
Keep-Alive: timeout=20
Connection: keep-alive

[
    {
        "id": 100002,
        "dateTime": "2020-01-30T10:00:00",
        "description": "Завтрак",
        "calories": 500,
        "excess": false
    }
]