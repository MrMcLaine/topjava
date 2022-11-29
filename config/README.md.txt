//Get all
$ curl http://localhost:8080/topjava/rest/admin/meals
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   764    0   764    0     0   1909      0 --:--:-- --:--:-- --:--:--  1910[{"id":100009,"dateTime":"2020-01-31T20:00:00","description":"Ужин","calories":510,"excess":true},{"id":100008,"dateTime":"2020-01-31T13:00:00","description":"Обед","calories":1000,"excess":true},{"id":100007,"dateTime":"2020-01-31T10:00:00","description":"Завтрак","calories":500,"excess":true},{"id":100006,"dateTime":"2020-01-31T00:00:00","description":"Еда на граничное значение","calories":100,"excess":true},{"id":100005,"dateTime":"2020-01-30T20:00:00","description":"Ужин","calories":500,"excess":false},{"id":100004,"dateTime":"2020-01-30T13:00:00","description":"Обед","calories":1000,"excess":false},{"id":100003,"dateTime":"2020-01-30T10:00:00","description":"Завтрак","calories":500,"excess":false}]

//Get
$ curl http://localhost:8080/topjava/rest/admin/meals/100003
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   104    0   104    0     0    247      0 --:--:-- --:--:-- --:--:--   247{"id":100003,"dateTime":"2020-01-30T10:00:00","description":"Завтрак","calories":500,"user":null}

//Delete
$ curl -v -X DELETE http://localhost:8080/topjava/rest/admin/meals/100003
*   Trying 127.0.0.1:8080...
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (127.0.0.1) port 8080 (#0)
> DELETE /topjava/rest/admin/meals/100003 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.84.0
> Accept: */*
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 204
< Date: Tue, 29 Nov 2022 15:47:13 GMT
<
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
* Connection #0 to host localhost left intact

//Update
$ curl -s -X PUT -d '{"dateTime":"2022-11-29T10:00","description":"Lunch","calories":850}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/profile/meals/100003
<!doctype html><html lang="en"><head><title>HTTP Status 404 – Not Found</title><style type="text/css">body {font-family:Tahoma,Arial,sans-serif;} h1, h2, h3, b {color:white;background-color:#525D76;} h1 {font-size:22px;} h2 {font-size:16px;} h3 {font-size:14px;} p {font-size:12px;} a {color:black;} .line {height:1px;background-color:#525D76;border:none;}</style></head><body><h1>HTTP Status 404 – Not Found</h1><hr class="line" /><p><b>Type</b> Status Report</p><p><b>Description</b> The origin server did not find a current representation for the target resource or is not willing to disclose that one exists.</p><hr class="line" /><h3>Apache Tomcat/9.0.65</h3></body></html>


//Create
$ curl -s -X POST -d '{"dateTime":"2022-11-29T10:00","description":"Lunch","calories":850}' -H 'Content-Type:application/json' http://localhost:8080/topjava/rest/profile/meals
<!doctype html><html lang="en"><head><title>HTTP Status 404 – Not Found</title><style type="text/css">body {font-family:Tahoma,Arial,sans-serif;} h1, h2, h3, b {color:white;background-color:#525D76;} h1 {font-size:22px;} h2 {font-size:16px;} h3 {font-size:14px;} p {font-size:12px;} a {color:black;} .line {height:1px;background-color:#525D76;border:none;}</style></head><body><h1>HTTP Status 404 – Not Found</h1><hr class="line" /><p><b>Type</b> Status Report</p><p><b>Description</b> The origin server did not find a current representation for the target resource or is not willing to disclose that one exists.</p><hr class="line" /><h3>Apache Tomcat/9.0.65</h3></body></html>
