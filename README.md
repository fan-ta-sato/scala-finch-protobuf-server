# scala-finch-protobuf-server

by finch

## run server

```sh
$ sbt run
```

## protobuf request

### request

```sh
$ sh ./sample/test.sh
```

or

```sh
cat $REQ | \
    protoc --encode=com.example.protos.HelloRequest -I$SRC_ROOT/src/main/protobuf hello.proto | \
    curl -s -X POST -H 'Content-Type: application/octet-stream' --data-binary @- http://$SERVER/pbhello | \
    protoc --decode=com.example.protos.HelloReply -I$SRC_ROOT/src/main/protobuf hello.proto | \
    xargs -0 echo -e
```
