# scala-play-url-shortener
A minimal web service built using Play Framework for shortening URL.

It uses MySQL as database and Anorm to work easily with the queries. 

To use the service simply run the request:

    CURL localhost:9000/urls -X POST --data '{"fullurl" : "http://facebook.com/mateusgf"}' -H 'Content-type: application/json'
