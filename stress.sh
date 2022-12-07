#!/bin/sh

echo "Press [CTRL+C] to stop.."
while :
do
    curl 'http://localhost:8080/todos' -X POST --data-raw 'description=hello'
done
